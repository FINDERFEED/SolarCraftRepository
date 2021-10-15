package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.items.projectiles.CrystalBossAttackHoldingMissile;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.registries.entities.Entities;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class ShieldingCrystalCrystalBoss extends Mob implements CrystalBossBuddy {


    private static EntityDataAccessor<Boolean> DEPLOYING = SynchedEntityData.defineId(ShieldingCrystalCrystalBoss.class,EntityDataSerializers.BOOLEAN);
    private int ticker = 0;

    public ShieldingCrystalCrystalBoss(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }


    @Override
    public void tick() {
        if (!level.isClientSide){
            if ((ticker++ >= 40) && this.entityData.get(DEPLOYING)){
                this.entityData.set(DEPLOYING,false);
            }
        }
        if (level.isClientSide && this.entityData.get(DEPLOYING)){
            for (int i = 0; i < 12;i++){
                double[] xz = FinderfeedMathHelper.polarToCartesian(0.5,i*30);
                level.addParticle(ParticleTypes.ENCHANT,this.position().x+xz[0],this.position().y+this.getBbHeight(),this.position().z+xz[1],0,-0.3,0);
            }
        }

        super.tick();
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity pEntity) {
    }

    public boolean isDeploying(){
        return this.entityData.get(DEPLOYING);
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


    public static AttributeSupplier.Builder createAttributes(){
        return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH,50).add(Attributes.ARMOR,20);
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
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {}


    @Override
    protected void defineSynchedData() {

        this.entityData.define(DEPLOYING,true);
        super.defineSynchedData();
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putInt("ticker",ticker);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.ticker = tag.getInt("ticker");
        super.load(tag);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return Sounds.CRYSTAL_HIT.get();
    }
}
