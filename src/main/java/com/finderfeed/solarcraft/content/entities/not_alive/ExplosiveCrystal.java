package com.finderfeed.solarcraft.content.entities.not_alive;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.misc_things.CrystalBossBuddy;
import com.finderfeed.solarcraft.registries.damage_sources.SolarcraftDamageSources;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class ExplosiveCrystal extends Mob implements CrystalBossBuddy {
    public static final int DEPLOYING_TIME = 20;
    public static final EntityDataAccessor<Byte> ACTIVATION_SECONDS_REMAINING = SynchedEntityData.defineId(ExplosiveCrystal.class, EntityDataSerializers.BYTE);


    public ExplosiveCrystal(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH,200).add(Attributes.ARMOR,10).add(Attributes.FOLLOW_RANGE);
    }


    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide){
            if (isDeploying()){
                for (int i = 0;i < 6;i++){
                    double[] xz = FDMathHelper.rotatePointDegrees(1.5*(1-(float)tickCount / DEPLOYING_TIME),0,i*60 + tickCount*5);
                    ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                            position().x + xz[0],position().y + 1.5,position().z + xz[1],0,0,0,()->200 + level.random.nextInt(55),
                            ()->0,()->0,0.3f);
                }
            }
        }else{
            int seconds = getRemainingActivationSeconds();
            if (!this.isDeadOrDying()) {
                if (seconds <= 0) {
                    for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, new AABB(-32, -32, -32, 32, 32, 32).move(position()),
                            (l)->!(l instanceof CrystalBossBuddy))) {
                        living.invulnerableTime = 0;
                        living.hurt(SolarcraftDamageSources.livingArmorPierce(this), 60);

                    }
                    level.playSound(null,position().x,position().y + this.getBbHeight()/2,position().z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE,1,1);
                    level.addParticle(ParticleTypes.EXPLOSION_EMITTER,position().x,position().y + this.getBbHeight()/2,position().z,0,0,0);
                    ((ServerLevel)level).sendParticles(ParticleTypes.EXPLOSION_EMITTER,position().x,position().y + this.getBbHeight()/2,position().z,1,0,0,0,0);
                    this.kill();
                } else {
                    if (tickCount % 20 == 0 && !isDeploying()) {
                        if (!level.getEntitiesOfClass(Player.class,
                                new AABB(-32, -32, -32, 32, 32, 32).move(position())).isEmpty()) {
                            setActivationSecondsRemaining(getRemainingActivationSeconds() - 1);
                        }
                    }
                }
            }
        }
    }

    public int getRemainingActivationSeconds(){
        return this.entityData.get(ACTIVATION_SECONDS_REMAINING);
    }

    public void setActivationSecondsRemaining(int seconds){
        this.entityData.set(ACTIVATION_SECONDS_REMAINING,(byte)seconds);
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.setDeltaMovement(entity.position().add(0,entity.getBbHeight()/2,0).subtract(this.position().add(0,this.getBbHeight()/2,0)).normalize());
    }

    public boolean isDeploying(){
        return tickCount <= DEPLOYING_TIME;
    }

    @Override
    public boolean attackable() {
        return !isDeploying();
    }

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {}

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
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
    public boolean fireImmune() {
        return true;
    }
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SolarcraftSounds.CRYSTAL_HIT.get();
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SolarcraftSounds.CRYSTAL_HIT.get();
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ACTIVATION_SECONDS_REMAINING,(byte)30);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("remainingSeconds")){
            setActivationSecondsRemaining(tag.getByte("remainingSeconds"));
        }
        super.load(tag);
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putByte("remainingSeconds",(byte)getRemainingActivationSeconds());
        return super.save(tag);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }
}