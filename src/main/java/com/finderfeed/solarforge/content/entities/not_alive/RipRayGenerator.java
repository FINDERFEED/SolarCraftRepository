package com.finderfeed.solarforge.content.entities.not_alive;

import com.finderfeed.solarforge.content.entities.CrystalBossEntity;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.local_library.other.CyclingInterpolatedValue;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.registries.sounds.SolarcraftSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class RipRayGenerator extends PathfinderMob implements CrystalBossBuddy{

    public int maxAttackLengthForClient = 0;
    private CyclingInterpolatedValue particlesValue = new CyclingInterpolatedValue(0,21,5*20);
    public static EntityDataAccessor<Boolean> DEPLOYING = SynchedEntityData.defineId(RipRayGenerator.class, EntityDataSerializers.BOOLEAN);

    public boolean alreadyDeployed = false;
    public int ticksalive = 0;

    public RipRayGenerator(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH,100).add(Attributes.ARMOR,5);
    }
    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide){
            ticksalive++;
            if ((ticksalive > this.getDeployingTime()) && !alreadyDeployed){
                this.entityData.set(DEPLOYING,false);
                alreadyDeployed = true;
            }
            if (ticksalive % 5 == 0){
                attackWithRay();
            }

        }
        if (this.level.isClientSide){
            if (!this.isDeploying()) {
                particlesValue.setEnd(maxAttackLengthForClient);
                particlesValue.setDuration(maxAttackLengthForClient*20/2);
                particlesValue.tick();
                if (this.level.getGameTime() % 5 == 0) {
                    maxAttackLengthForClient = this.getMaxAttackHeight();
                }


                doParticles();

            }else{
                doDeployingParticles();
            }
        }
    }

    public void doDeployingParticles(){
        if (level.getGameTime() % 2 == 0) {
            for (int i = 0; i < 6; i++) {
                double[] coords = FDMathHelper.polarToCartesian(0.5, Math.toRadians(60 * i));
                this.level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        this.position().x + coords[0],
                        this.position().y,
                        this.position().z + coords[1],
                        0, -0.05, 0);
            }
        }
    }

    public void attackWithRay(){
        List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class,new AABB(-0.5,-getMaxAttackHeight(),-0.5,0.5,0.5,0.5)
        .move(this.position()));
        for (LivingEntity ent : entities){
            if (!(ent instanceof CrystalBossBuddy)) {
                ent.hurt(DamageSource.MAGIC, CrystalBossEntity.RIP_RAY_DAMAGE);
            }
        }
    }


    public void doParticles(){
        double[] coords = FDMathHelper.polarToCartesian(0.4,Math.toRadians(level.getGameTime()*30));
        this.level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                this.position().x+coords[0],
                this.position().y-particlesValue.getValue(),
                this.position().z + coords[1],
                0,0,0);

    }

    public int getMaxAttackHeight(){
        for (int i = 0;i <= 20;i++){
           if (!level.getBlockState(blockPosition().below(i)).isAir()){
               return i;
           }
        }
        return 20;
    }

    public boolean isDeploying(){
        return this.entityData.get(DEPLOYING);
    }

    public int getDeployingTime(){
        return 60;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DEPLOYING,true);
    }

    @Override
    public boolean save(CompoundTag p_20224_) {
        p_20224_.putInt("ticksalive",ticksalive);
        return super.save(p_20224_);
    }

    @Override
    public void load(CompoundTag p_20259_) {
        this.ticksalive = p_20259_.getInt("ticksalive");
        super.load(p_20259_);
    }

    @Override
    protected void doPush(Entity pEntity) {
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

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {

    }


    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SolarcraftSounds.CRYSTAL_HIT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SolarcraftSounds.CRYSTAL_HIT.get();
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
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }
}
