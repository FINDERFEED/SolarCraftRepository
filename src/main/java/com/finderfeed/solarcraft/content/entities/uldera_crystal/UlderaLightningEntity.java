package com.finderfeed.solarcraft.content.entities.uldera_crystal;

import com.finderfeed.solarcraft.registries.damage_sources.SolarcraftDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class UlderaLightningEntity extends Entity {

    public static final EntityDataAccessor<Float> HEIGHT = SynchedEntityData.defineId(UlderaLightningEntity.class, EntityDataSerializers.FLOAT);
    public static final AABB DAMAGE_BOX = new AABB(-2,-2,-2,2,2,2);
    public float damage;

    public UlderaLightningEntity(EntityType<?> type, Level level) {
        super(type, level);
    }


    @Override
    public void tick() {
        if (!level.isClientSide && this.tickCount == 1){
            this.dealDamage();
            ((ServerLevel)level).playSound(this,this.getOnPos(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.HOSTILE,
                    5f,0.5f);
        }
        if (!level.isClientSide && tickCount >= 10){
            this.remove(RemovalReason.DISCARDED);
        }
        super.tick();
    }

    private void dealDamage(){
        AABB box = DAMAGE_BOX.move(this.position());
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class,box,entity->{
            return !(entity instanceof UlderaCrystalBoss );
        })){
            entity.hurt(SolarcraftDamageSources.SHADOW,damage);
        }
    }

    public void setHeight(float h){
        this.entityData.set(HEIGHT,h);
    }

    public float getHeight(){
        return this.entityData.get(HEIGHT);
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putFloat("damage",damage);
        tag.putFloat("height",this.getHeight());
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.damage = tag.getFloat("damage");
//        this.setHeight(tag.getFloat("height"));
        super.load(tag);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(HEIGHT,10f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {

    }
}
