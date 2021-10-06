package com.finderfeed.solarforge.entities;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.for_future_library.entities.BossAttackChain;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.items.projectiles.CrystalBossAttackHoldingMissile;
import com.finderfeed.solarforge.magic_items.items.projectiles.FallingStarCrystalBoss;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.registries.entities.Entities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.w3c.dom.Attr;

import javax.annotation.Nullable;
import java.util.List;

public class CrystalBossEntity extends Mob implements CrystalBossBuddy {
    private static EntityDataAccessor<Boolean> ATTACK_IMMUNE = SynchedEntityData.defineId(CrystalBossEntity.class, EntityDataSerializers.BOOLEAN);

    private ServerBossEvent CRYSTAL_BOSS_EVENT = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_20);
    private final BossAttackChain ATTACK_CHAIN = new BossAttackChain.Builder()
            .addAttack("missiles",this::holdingMissilesAttack,40,10)
            .addAttack("mines",this::spawnMines,200,40)
            .addAttack("air_strike",this::airStrike,160,10)
            .addAttack("shielding_crystals",this::spawnShieldingCrystals,80,null)
            .setTimeBetweenAttacks(80)
            .build();
    private int ticker = 0;
    public List<ShieldingCrystalCrystalBoss> entitiesAroundClient = null;

    public CrystalBossEntity(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            ticker++;
            if (this.hasEnemiesNearby()) {
                ATTACK_CHAIN.tick();
            }
        }
        if (level.isClientSide){
            this.entitiesAroundClient = level.getEntitiesOfClass(ShieldingCrystalCrystalBoss.class,
                    new AABB(-16,-16,-16,16,16,16).move(position()),(cr)->{
                        return (cr.distanceTo(this) <= 16 ) ;
                    });

        }

    }

    public boolean isBlockingDamage(){
        return !(level.getEntitiesOfClass(ShieldingCrystalCrystalBoss.class,
                new AABB(-16,-16,-16,16,16,16).move(position()),(cr)->{
                    return (cr.distanceTo(this) <= 16 ) && !cr.isDeploying();
                }).isEmpty());
    }



    @Override
    protected void doPush(Entity entity) {
        entity.setDeltaMovement(entity.position().add(0,entity.getBbHeight()/2,0).subtract(this.position().add(0,this.getBbHeight()/2,0)).normalize());
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance p_21197_) {
        return false;
    }



    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public boolean canCollideWith(Entity p_20303_) {
        return false;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }


    public void airStrike(){

        for (int i = 0;i < 5;i++){
            double x = (level.random.nextDouble()*0.2+0.01)*FinderfeedMathHelper.randomPlusMinus();
            double z = (level.random.nextDouble()*0.2+0.01)*FinderfeedMathHelper.randomPlusMinus();
            FallingStarCrystalBoss star = new FallingStarCrystalBoss(level,x,1,z);
            star.setPos(this.position().add(0,this.getBbHeight()*0.7,0));
            level.addFreshEntity(star);
        }
    }


    public void spawnMines(){
        List<Vec3> positions = Helpers.findRandomGroundPositionsAround(level,position(),20,15);
        for (Vec3 pos : positions){
            MineEntityCrystalBoss mine = new MineEntityCrystalBoss(Entities.CRYSTAL_BOSS_MINE.get(),level);
            mine.setPos(pos);
            level.addFreshEntity(mine);
        }
    }

    public void spawnShieldingCrystals(){
        for (int i = 0; i < 3;i++){
            if (this.level.getEntitiesOfClass(ShieldingCrystalCrystalBoss.class,new AABB(-10,-10,-10,10,10,10).move(position())).size() <= 10) {
                ShieldingCrystalCrystalBoss crystal = new ShieldingCrystalCrystalBoss(Entities.CRYSTAL_BOSS_SHIELDING_CRYSTAL.get(), level);
                Vec3 positon = this.position().add((level.random.nextDouble() * 5 +3)* FinderfeedMathHelper.randomPlusMinus(), 0, (level.random.nextDouble() * 5 +3)* FinderfeedMathHelper.randomPlusMinus());
                crystal.setPos(positon);
                level.addFreshEntity(crystal);
            }
        }
    }


    public void holdingMissilesAttack(){
        List<Player> possibleTargets = level.getEntitiesOfClass(Player.class,new AABB(this.position().add(-20,-8,-20),this.position().add(20,8,20)),
                (pl)->{
                    return !pl.isCreative() && !pl.isSpectator();
                });
        if (!possibleTargets.isEmpty()){
            float timeOffset = 0;
            for (int i = 0; i < 7*possibleTargets.size();i++){
                Vec3 vec= Helpers.randomVector();

                CrystalBossAttackHoldingMissile missile = new CrystalBossAttackHoldingMissile.Builder(level)
                        .setHoldingTime(1+timeOffset)
                        .setTarget(possibleTargets.get(level.random.nextInt(possibleTargets.size())).getUUID())
                        .setInitialSpeed(vec.multiply(1,0.2,1).normalize())
                        .setPosition(this.position().add(0,this.getBbHeight()/2,0))
                        .build();
                level.addFreshEntity(missile);
                timeOffset+=0.1;
            }
        }
    }

    public boolean hasEnemiesNearby(){
        return !level.getEntitiesOfClass(Player.class,new AABB(this.position().add(-20,-8,-20),this.position().add(20,8,20)),
                (pl)->{
                    return !pl.isCreative() && !pl.isSpectator();
                }).isEmpty();
    }

    public static AttributeSupplier.Builder createAttributes(){
        return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH,5000).add(Attributes.ARMOR,10);
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean save(CompoundTag cmp) {
        ATTACK_CHAIN.save(cmp);
        cmp.putInt("ticker",ticker);
        return super.save(cmp);
    }

    @Override
    public void load(CompoundTag cmp) {
        ATTACK_CHAIN.load(cmp);
        this.ticker = cmp.getInt("ticker");
        super.load(cmp);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer p_20119_) {
        super.startSeenByPlayer(p_20119_);
        CRYSTAL_BOSS_EVENT.addPlayer(p_20119_);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer p_20174_) {
        super.stopSeenByPlayer(p_20174_);
        CRYSTAL_BOSS_EVENT.removePlayer(p_20174_);
    }
    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }



    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        return super.hurt(p_21016_, p_21017_);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        CRYSTAL_BOSS_EVENT.setProgress(this.getHealth()/this.getMaxHealth());
    }

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {}


    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEvents.BLAZE_HURT;
    }
}

@Mod.EventBusSubscriber(modid = SolarForge.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
class CancelAttack{

    @SubscribeEvent
    public static void cancelCrystalBossAttack(LivingHurtEvent event){
        if (event.getEntityLiving() instanceof CrystalBossEntity boss){
            if (boss.isBlockingDamage()){
                event.setCanceled(true);
            }
        }else if (event.getEntityLiving() instanceof ShieldingCrystalCrystalBoss shield){
            if (shield.isDeploying()){
                event.setCanceled(true);
            }
        }

    }
}
