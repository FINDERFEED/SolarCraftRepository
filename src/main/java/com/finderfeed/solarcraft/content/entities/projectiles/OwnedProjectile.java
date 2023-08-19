package com.finderfeed.solarcraft.content.entities.projectiles;

import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class OwnedProjectile extends NormalProjectile{

    private UUID owner;

    protected OwnedProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public OwnedProjectile(EntityType<? extends AbstractHurtingProjectile> x, double y, double z, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(x, y, z, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
    }

    public OwnedProjectile(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity owner, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(p_36826_, owner, p_36828_, p_36829_, p_36830_, p_36831_);
    }


    @Override
    public boolean save(CompoundTag tag) {
        CompoundNBTHelper.saveUUID(tag,owner,"owner");
        return super.save(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        this.owner = CompoundNBTHelper.getUUID(tag,"owner");
        super.load(tag);
    }

    @Nullable
    public UUID getOwnerUUID(){
        return this.owner;
    }

    public void setOwner(LivingEntity living){
        this.owner = living.getUUID();
    }

    public void setOwner(UUID uuid){
        this.owner = uuid;
    }

    @Nullable
    public Entity getOwner(){
        if (level.isClientSide) return null;
        if (this.getOwnerUUID() == null) return null;
        return ((ServerLevel)level).getEntity(this.getOwnerUUID());
    }

    @Nullable
    public LivingEntity getLivingEntityOwner(){
        if (this.getOwner() instanceof LivingEntity entity){
            return entity;
        }else{
            return null;
        }
    }
}
