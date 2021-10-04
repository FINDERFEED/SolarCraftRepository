package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.Helpers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class MineEntityCrystalBoss extends Entity {

    public static EntityDataAccessor<Integer> TICKER = SynchedEntityData.defineId(MineEntityCrystalBoss.class, EntityDataSerializers.INT);
    private int ticker = 0;

    public MineEntityCrystalBoss(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }


    @Override
    public void tick() {
        super.tick();
        if (this.entityData.get(TICKER) >= 61){
            this.kill();
        }
        if (this.entityData.get(TICKER) >= 60){
            blowUp();
        }

        if (!this.level.isClientSide){
            ticker++;
            this.entityData.set(TICKER,ticker);

        }


    }


    public void blowUp(){
        if (!this.level.isClientSide){
            level.getEntitiesOfClass(LivingEntity.class,new AABB(-1.5,-1.0,-1.5,1.5,2,1.5).move(position())).forEach((living)->{
                living.hurt(DamageSource.MAGIC,5);
            });
        }
        level.getEntitiesOfClass(LivingEntity.class,new AABB(-1.5,-1.0,-1.5,1.5,2,1.5).move(position())).forEach((living)->{
            living.setDeltaMovement(living.position().subtract(this.position()).normalize().add(0,1,0).multiply(1.5,1.5,1.5));
        });
        if (this.level.isClientSide){

            createExplosionParticles();
        }
    }

    private void createExplosionParticles(){
        Helpers.createSmallSolarStrikeParticleExplosion(level, this.position(),2,0.1f,0.5f);
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

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TICKER,0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
