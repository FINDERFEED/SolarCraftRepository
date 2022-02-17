package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.packet_handler.packets.misc_packets.ExplosionParticlesPacket;
import com.finderfeed.solarforge.registries.entities.EntityTypes;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

public class SunstrikeEntity extends Entity {

    public static final int FALLING_TIME = 25;
    private float damage = 0;

    public SunstrikeEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public SunstrikeEntity(Level world,double x,double y,double z){
        this(EntityTypes.SUNSTRIKE.get(),world);
        setPos(x,y,z);
    }


    @Override
    public void tick() {
        if (!level.isClientSide && tickCount == 1){
            level.playSound(null,this.getX(),this.getY(),this.getZ(), Sounds.SUNSTRIKE.get(), SoundSource.AMBIENT,1,1f);
        }
        if (!level.isClientSide && tickCount > FALLING_TIME){
            explode();
        }
        super.tick();
    }

    public void explode(){
        for (LivingEntity entity : this.level.getEntitiesOfClass(LivingEntity.class,new AABB(-2,-2,-2,2,2,2)
                .move(position()),(e)-> !(e instanceof CrystalBossBuddy)) ){
            if (Helpers.isVulnerable(entity)){
                entity.hurt(DamageSource.MAGIC,damage);
                entity.invulnerableTime = 0;
            }
        }
        level.playSound(null,this.getX(),this.getY(),this.getZ(), Sounds.SOLAR_EXPLOSION.get(), SoundSource.AMBIENT,level.random.nextFloat()*0.5f+0.5f,1f);
        ExplosionParticlesPacket.send(level,this.position());
        this.discard();
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.damage = tag.getFloat("damage");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("damage",damage);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
