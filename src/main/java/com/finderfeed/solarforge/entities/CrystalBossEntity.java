package com.finderfeed.solarforge.entities;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.entities.BossAttackChain;
import com.finderfeed.solarforge.magic_items.items.projectiles.CrystalBossAttackHoldingMissile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
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
import org.w3c.dom.Attr;

import java.util.List;

public class CrystalBossEntity extends Mob {
    private static EntityDataAccessor<Boolean> ATTACK_IMMUNE = SynchedEntityData.defineId(CrystalBossAttackHoldingMissile.class, EntityDataSerializers.BOOLEAN);

    private ServerBossEvent CRYSTAL_BOSS_EVENT = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_20);
    private final BossAttackChain ATTACK_CHAIN = new BossAttackChain.Builder()
            .addAttack("missiles",this::holdingMissilesAttack,20,false)
            .setTimeBetweenAttacks(80)
            .build();
    private int ticker = 0;

    public CrystalBossEntity(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }


    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            ticker++;
            ATTACK_CHAIN.tick();
        }

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

    private void holdingMissilesAttack(){
        List<Player> possibleTargets = level.getEntitiesOfClass(Player.class,new AABB(this.position().add(-20,-5,-20),this.position().add(20,5,20)));
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

    public static AttributeSupplier.Builder createAttributes(){
        return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH,5000).add(Attributes.ARMOR,10);
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



}
