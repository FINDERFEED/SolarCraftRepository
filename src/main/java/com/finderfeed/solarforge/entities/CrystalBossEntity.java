package com.finderfeed.solarforge.entities;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.for_future_library.entities.BossAttackChain;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.items.projectiles.CrystalBossAttackHoldingMissile;
import com.finderfeed.solarforge.magic_items.items.projectiles.FallingStarCrystalBoss;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.registries.entities.Entities;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
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

import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;


public class CrystalBossEntity extends Mob implements CrystalBossBuddy {
    private static EntityDataAccessor<Boolean> GET_OFF_ME = SynchedEntityData.defineId(CrystalBossEntity.class, EntityDataSerializers.BOOLEAN);
    public static EntityDataAccessor<Float> RAY_STATE_FLOAT_OR_ANGLE = SynchedEntityData.defineId(CrystalBossEntity.class,EntityDataSerializers.FLOAT);

    public int clientGetOffMeTicker = 0;
    public static final int RAY_STOPPED = -3;
    public static final int RAY_NOT_ACTIVE = -1;
    public static final int RAY_PREPARING = -2;

    private ServerBossEvent CRYSTAL_BOSS_EVENT = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_20);
    private final BossAttackChain ATTACK_CHAIN = new BossAttackChain.Builder()
            .addAttack("missiles",this::holdingMissilesAttack,40,10)
            .addAttack("mines",this::spawnMines,200,40)
            .addAttack("air_strike",this::airStrike,160,10)
            .addAttack("shielding_crystals",this::spawnShieldingCrystals,40,null)
            .addAttack("ray_attack",this::rayAttack,650,1)
            .addPostEffectToAttack("ray_attack",this::rayAttackPost)
            .addAftermathAttack(this::getOffMEEE)
            .setTimeBetweenAttacks(40)
            .build();
    private int ticker = 0;
    public List<ShieldingCrystalCrystalBoss> entitiesAroundClient = null;
    private int rayPreparingTicks = -1;

    public CrystalBossEntity(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
        AttributeInstance attribute = this.getAttribute(Attributes.MAX_HEALTH);
        if (attribute != null){
            attribute.addPermanentModifier(new AttributeModifier(UUID.fromString("24ee266e-f591-4c1a-92a6-e2e31b1c716c"),"max_health_mod",5, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            if (this.entityData.get(GET_OFF_ME)){
                this.entityData.set(GET_OFF_ME,false);
            }
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
            rayAttackParticles();
            setGetOffMeClient();
        }

    }

    private void setGetOffMeClient(){
        boolean flagData = this.entityData.get(GET_OFF_ME);
        boolean flagTicker = clientGetOffMeTicker > 0;
        if (flagData && !flagTicker){
            this.clientGetOffMeTicker = 1;
        }else if (!flagData && flagTicker){
            this.clientGetOffMeTicker+=4;
            if (clientGetOffMeTicker > 33){
                this.clientGetOffMeTicker = 0;
            }
        }
    }

    public void getOffMEEE(){
        level.getEntitiesOfClass(LivingEntity.class,
                new AABB(-5,-5,-5,5,5,5).move(position().add(0,this.getBbHeight()/2,0)),
                (ent)-> !(ent instanceof CrystalBossBuddy)).forEach((entity)->{
                    Vec3 speed = entity.position().add(0,entity.getBbHeight()/2,0).subtract(this.position()).normalize().add(0,0.3,0);
                    if (entity instanceof ServerPlayer player){
                        Helpers.setServerPlayerSpeed(player,speed);
                    }else{
                        entity.setDeltaMovement(speed);
                    }
        });
        this.entityData.set(GET_OFF_ME,true);
    }

    public boolean isBlockingDamage(){
        return !(level.getEntitiesOfClass(ShieldingCrystalCrystalBoss.class,
                new AABB(-16,-16,-16,16,16,16).move(position()),(cr)->{
                    return (cr.distanceTo(this) <= 16 ) && !cr.isDeploying();
                }).isEmpty());
    }


    public void rayAttackPost(){
        this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE,(float)RAY_NOT_ACTIVE);
    }

    public void rayAttack(){
        float state = this.entityData.get(RAY_STATE_FLOAT_OR_ANGLE);
        int rounded = Math.round(state);
        if (rounded != RAY_STOPPED) {
            if (rounded == RAY_NOT_ACTIVE) {
                this.rayPreparingTicks = 60;
                this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE, (float) RAY_PREPARING);
            } else if (rounded == RAY_PREPARING) {
                if (this.rayPreparingTicks-- <= 0) {
                    this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE, 0f);
                }
            } else {
                Vec3 firstPos = this.position().add(0, 0.5, 0);
                Vec3 secondPos = firstPos.add(new Vec3(10, 0, 0).yRot((float) Math.toRadians(state)));
                EntityHitResult res = Helpers.getHitResult(level, firstPos, secondPos, (entity -> {
                    return Helpers.isVulnerable(entity) && !(entity instanceof CrystalBossBuddy);
                }));
                if (res != null) {
                    Entity ent = res.getEntity();
                    ent.hurt(DamageSource.MAGIC, 7);
                }

                secondPos = firstPos.add(new Vec3(10, 0, 0).yRot((float) Math.toRadians(state+180)));
                res = Helpers.getHitResult(level, firstPos, secondPos,  (entity -> {
                    return Helpers.isVulnerable(entity) && !(entity instanceof CrystalBossBuddy);
                }));
                if (res != null) {
                    Entity ent = res.getEntity();
                    ent.hurt(DamageSource.MAGIC, 10);
                }
                if (state <= 2160*1.5f) {
                    this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE, state + 3.6f*1.5f);
                } else {
                    this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE, (float) RAY_STOPPED);
                }
            }
        }
    }

    private void rayAttackParticles(){
        float state = this.entityData.get(RAY_STATE_FLOAT_OR_ANGLE);
        int rounded = Math.round(state);
        if (rounded != RAY_STOPPED){
            if (rounded != RAY_NOT_ACTIVE){
                if (rounded == RAY_PREPARING){
                    for (int i = -10;i < 11;i++){
                        double rndx = level.random.nextDouble()*0.5-0.25;
                        double rndy = level.random.nextDouble()*0.5-0.25;
                        double rndz = level.random.nextDouble()*0.5-0.25;
                        level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                                this.position().x+i+rndx,
                                this.position().y+1.6+rndy,
                                this.position().z+rndz,
                                rndx*0.1,rndy*0.1,rndz*0.1);
                    }

                }else{
                    Vec3 vec = new Vec3(10,0,0).yRot((float)Math.toRadians(state));
                    Vec3 vec2 = new Vec3(10,0,0).yRot((float)Math.toRadians(state+180));
                    for (int i = 0;i < 11;i++){
                        double rndx = level.random.nextDouble()*0.5-0.25;
                        double rndy = level.random.nextDouble()*0.5-0.25;
                        double rndz = level.random.nextDouble()*0.5-0.25;
                        level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                                this.position().x+(vec.x *((float)i/10)) +rndx,
                                this.position().y+1.6+rndy,
                                this.position().z+(vec.z *((float)i/10))+rndz,
                                rndx*0.1,rndy*0.1,rndz*0.1);
                    }
                    for (int i = 0;i < 11;i++){
                        double rndx = level.random.nextDouble()*0.5-0.25;
                        double rndy = level.random.nextDouble()*0.5-0.25;
                        double rndz = level.random.nextDouble()*0.5-0.25;
                        level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                                this.position().x+(vec2.x *((float)i/10)) +rndx,
                                this.position().y+1.6+rndy,
                                this.position().z+(vec2.z *((float)i/10))+rndz,
                                rndx*0.1,rndy*0.1,rndz*0.1);
                    }
                }
            }
        }
    }



    public void airStrike(){
        for (int i = 0;i < 6;i++){
            double x = (level.random.nextDouble()*0.25+0.01)*FinderfeedMathHelper.randomPlusMinus();
            double z = (level.random.nextDouble()*0.25+0.01)*FinderfeedMathHelper.randomPlusMinus();
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
            if (this.level.getEntitiesOfClass(ShieldingCrystalCrystalBoss.class,new AABB(-10,-10,-10,10,10,10).move(position())).size() < 9) {
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
        return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH,10000).add(Attributes.ARMOR,20);
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean save(CompoundTag cmp) {
        ATTACK_CHAIN.save(cmp);
        float state = this.entityData.get(RAY_STATE_FLOAT_OR_ANGLE);
        cmp.putFloat("ray_state",state);
        cmp.putInt("ticker",ticker);
        cmp.putInt("ray_preparing",rayPreparingTicks);
        return super.save(cmp);
    }

    @Override
    public void load(CompoundTag cmp) {
        ATTACK_CHAIN.load(cmp);
        this.ticker = cmp.getInt("ticker");
        this.rayPreparingTicks = cmp.getInt("ray_preparing");
        float c = cmp.getFloat("ray_state");
        if (((c < 1) && (c > -0.1) && (this.tickCount < 5))) {
            this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE, (float)RAY_NOT_ACTIVE);
        }else{
            this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE,c);
        }
        super.load(cmp);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RAY_STATE_FLOAT_OR_ANGLE,(float)RAY_NOT_ACTIVE);
        this.entityData.define(GET_OFF_ME,false);
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
        System.out.println("current: "+this.getHealth());
        System.out.println("max: "+this.getMaxHealth());
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
        return Sounds.CRYSTAL_HIT.get();
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
