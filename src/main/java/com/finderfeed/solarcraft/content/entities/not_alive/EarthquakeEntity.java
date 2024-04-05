package com.finderfeed.solarcraft.content.entities.not_alive;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.misc_things.CrystalBossBuddy;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.data_serializers.FDEntityDataSerializers;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


public class EarthquakeEntity extends Entity {

    public static final int ACTIVATION_TIME = 30;
    public static final int DEATH_TIME = 60;
    public static final float MAX_LENGTH = 20;
    private float damage;

    public static final EntityDataAccessor<Vec3> DIRECTION_VECTOR = SynchedEntityData.defineId(EarthquakeEntity.class, FDEntityDataSerializers.VEC3.get());

    public static final EntityDataAccessor<Float> LENGTH = SynchedEntityData.defineId(EarthquakeEntity.class, EntityDataSerializers.FLOAT);

    public EarthquakeEntity(EntityType<?> type, Level world) {
        super(type, world);
    }


    public EarthquakeEntity(Level world,Vec3 dir,float length){
        this(SCEntityTypes.EARTHQUAKE.get(),world);
        this.setDirection(dir);
        setLength(length);
    }


    @Override
    public void tick() {
        if (!level.isClientSide){
            this.setDirection(this.getDir());
            this.setLength(this.getLength());
            if (tickCount == ACTIVATION_TIME){

                Vec3 dir = this.entityData.get(DIRECTION_VECTOR);
                Vec3 initPos = position();
                Vec3 endPos = initPos.add(dir.normalize().multiply(getLength() + 1,0,getLength() + 1));
                Vec3 beetween = initPos.subtract(endPos).normalize();
                AABB box = new AABB(initPos.x + beetween.x,initPos.y,initPos.z + beetween.z,
                        endPos.x + beetween.reverse().x,endPos.y + 6,endPos.z+ beetween.reverse().z);
                for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class,box,(l)-> !(l instanceof CrystalBossBuddy))){
                    Vec3 vec = endPos.subtract(initPos).multiply(1,0,1);
                    Vec3 ePos = e.position().subtract(initPos).multiply(1,0,1);
                    double main = vec.length();
                    double a = ePos.length();
                    double b = vec.subtract(ePos).length();
                    double p = (main + a + b)/2;
                    double S = Math.sqrt(p*(p - a)*(p-b)*(p-main));
                    double H = 2*S / main;
                    if (H <= 2){
                        e.hurt(SCDamageSources.RUNIC_MAGIC,damage);
                    }
                }
                level.playSound(null,position().x,position().y,position().z, SCSounds.EARTHQUAKE.get(), SoundSource.HOSTILE,5f,1f);
            }
            if (tickCount >= DEATH_TIME-20){
                this.kill();
            }
        }else{
            if (tickCount == ACTIVATION_TIME-2){
                Vec3 dir = this.entityData.get(DIRECTION_VECTOR);
                Vec3 initPos = position();
                Vec3 endPos = initPos.add(dir.normalize().multiply(getLength(),0,getLength()));
                Vec3 between = endPos.subtract(initPos);
                for (int i = 0;i < 5;i++){
                    for (double g = 0;g <= between.length();g+=0.75){
                        double percentile = (g + level.random.nextDouble()*0.3) /between.length();
                        Vec3 pos = initPos.add(between.multiply(percentile,0,percentile));
                        ClientHelpers.Particles.createParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                                pos.x,pos.y,pos.z,0,(0.1 + level.random.nextDouble()*0.05) *(1-i/4f),0,()->255,()->255,()->0,0.5f*(i/4f));
                    }
                }
            }
        }

        super.tick();
    }



    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public float getLength() {
        return this.entityData.get(LENGTH);
    }

    public void setLength(float length) {
        this.entityData.set(LENGTH,length);
    }

    public void setDirection(Vec3 vec3){
        this.entityData.set(DIRECTION_VECTOR,vec3);
    }

    public Vec3 getDir(){
        return this.entityData.get(DIRECTION_VECTOR);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DIRECTION_VECTOR,new Vec3(1,0,1));
        this.entityData.define(LENGTH,MAX_LENGTH);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("damage")) {
            this.damage = tag.getFloat("damage");
        }
        if (tag.contains("dir1")) {
            this.entityData.set(DIRECTION_VECTOR, CompoundNBTHelper.getVec3("dir", tag));
        }
        if (tag.contains("len")){
            setLength(tag.getFloat("len"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("damage",damage);
        tag.putFloat("len",getLength());
        CompoundNBTHelper.writeVec3("dir",entityData.get(DIRECTION_VECTOR),tag);
    }

//    @Override
//    public Packet<ClientGamePacketListener> getAddEntityPacket() {
//        return NetworkHooks.getEntitySpawningPacket(this);
//    }



}
