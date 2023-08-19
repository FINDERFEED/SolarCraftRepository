package com.finderfeed.solarcraft.content.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class AttackEffectEntity extends Entity {


    private int livingTicks;
    private int lifetime;

    public AttackEffectEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public void tick() {
        if (!level.isClientSide && livingTicks++ > lifetime){
            this.remove(RemovalReason.DISCARDED);
        }
        super.tick();
    }

    public int getLivingTicks() {
        return livingTicks;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public void setLivingTicks(int livingTicks) {
        this.livingTicks = livingTicks;
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putInt("lifetime",lifetime);
        tag.putInt("livingTicks",livingTicks);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.lifetime = tag.getInt("lifetime");
        this.livingTicks = tag.getInt("livingTicks");
        super.load(tag);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }
}
