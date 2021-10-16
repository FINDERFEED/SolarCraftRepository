package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.registries.attributes.AttributesRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public abstract class NoHealthLimitMob extends Mob {

    private static final EntityDataAccessor<Float> DATA_UNLIMITED_HEALTH_ID = SynchedEntityData.defineId(NoHealthLimitMob.class, EntityDataSerializers.FLOAT);

    protected NoHealthLimitMob(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    public static AttributeSupplier.Builder createEntityAttributes(){
        return PathfinderMob.createMobAttributes().add(AttributesRegistry.MAXIMUM_HEALTH_NO_LIMIT.get());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_UNLIMITED_HEALTH_ID,1.0f);
    }

    @Override
    public float getHealth() {
        return this.entityData.get(DATA_UNLIMITED_HEALTH_ID);
    }

    @Override
    public void setHealth(float p_21154_) {
        this.entityData.set(DATA_UNLIMITED_HEALTH_ID, Mth.clamp(p_21154_, 0.0F, this.getMaxHealth()));
    }

    @Override
    public double getAttributeValue(Attribute attr) {
        if (attr.equals(Attributes.MAX_HEALTH)){
            return super.getAttributeValue(AttributesRegistry.MAXIMUM_HEALTH_NO_LIMIT.get());
        }
        return super.getAttributeValue(attr);
    }

}
