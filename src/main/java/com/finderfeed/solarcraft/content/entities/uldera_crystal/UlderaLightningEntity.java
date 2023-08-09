package com.finderfeed.solarcraft.content.entities.uldera_crystal;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.CameraShakePacket;
import com.finderfeed.solarcraft.registries.damage_sources.SolarcraftDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import java.util.Random;
import java.util.UUID;

public class UlderaLightningEntity extends Entity {

    public static final EntityDataAccessor<Float> HEIGHT = SynchedEntityData.defineId(UlderaLightningEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> LIGHTNING_DELAY = SynchedEntityData.defineId(UlderaLightningEntity.class, EntityDataSerializers.INT);
    public static final AABB DAMAGE_BOX = new AABB(-2.5,-2.5,-2.5,2.5,0,2.5);
    public float damage;
    private UUID owner;

    public UlderaLightningEntity(EntityType<?> type, Level level) {
        super(type, level);
    }


    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            this.serverTick();
        }else{
            this.clientTick();
        }
    }

    private void serverTick(){
        if (this.tickCount == this.getLightningDelay()){
            this.dealDamage();
            ((ServerLevel)level).playSound(this,this.getOnPos(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.HOSTILE,
                    5f,0.5f);
        }else if (this.tickCount >= this.getLightningDelay() + 10){
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private void clientTick(){
        if (this.tickCount < this.getLightningDelay()){
            float h = this.getHeight() < 10 ? this.getHeight() : 10;
            for (int i = 0; i <= h;i++){
                this.spawnParticleCollumn(i);
            }
            RandomSource r = level.random;
            for (int i = 0; i < 2;i++){
                Vec3 ppos = this.position().add(0,0.1,0);
                level.addParticle(BallParticleOptions.Builder.begin()
                        .setSize(0.2f)
                        .setRGB(90,0,186)
                        .setShouldShrink(true)
                                .setPhysics(false)
                                .setLifetime(60)
                        .build(),ppos.x,ppos.y,ppos.z,
                        (r.nextDouble()*2-1)*0.3f,
                        0,
                        (r.nextDouble()*2-1)*0.3f
                );
            }
        } else if (this.tickCount == this.getLightningDelay()){
            RandomSource r = level.random;
            for (int i = 0; i < 50;i++){
                Vec3 ppos = this.position().add(0,0.1,0);
                level.addParticle(BallParticleOptions.Builder.begin()
                                .setSize(0.2f)
                                .setRGB(90,0,186)
                                .setShouldShrink(true)
                                .setLifetime(60)
                                .setPhysics(false)
                                .build(),ppos.x,ppos.y,ppos.z,
                        (r.nextDouble()*2-1)*0.1f,
                        r.nextDouble()*0.1f,
                        (r.nextDouble()*2-1)*0.1f);
            }
        }
    }

    private void spawnParticleCollumn(int h){
        RandomSource r = level.random;
        for (float i = 0; i < 1;i += 0.25f){
            Vec3 ppos = this.position().add(0,h + i,0);
            level.addParticle(BallParticleOptions.Builder.begin()
                    .setSize(0.2f)
                    .setRGB(90,0,186)
                    .setPhysics(false)
                    .setShouldShrink(true)
                            .setLifetime(20)
                    .build(),ppos.x,ppos.y,ppos.z,(r.nextDouble()*2-1)*0.2f,0,(r.nextDouble()*2-1)*0.2f);
        }
    }

    private void dealDamage(){
        ServerLevel l = (ServerLevel)level;
        AABB box = DAMAGE_BOX.setMaxY(this.getHeight() + 2).move(this.position());
        LivingEntity owner = this.getOwner() != null ? (LivingEntity) l.getEntity(this.getOwner()) : null;
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class,box,entity->{
            return !entity.getUUID().equals(this.getOwner()) && !(entity instanceof UlderaCrystalBuddy);
        })){
            if (owner != null) {
                entity.hurt(SolarcraftDamageSources.livingArmorPierce(owner),damage);
            }else{
                entity.hurt(SolarcraftDamageSources.SHADOW, damage);
            }
        }
        Vec3 c = this.position();
        SCPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(()->new PacketDistributor.TargetPoint(
                c.x,c.y,c.z,10,this.level().dimension()
        )),new CameraShakePacket(0,10,5,0.25f));
    }

    public void setHeight(float h){
        this.entityData.set(HEIGHT,h);
    }

    public float getHeight(){
        return this.entityData.get(HEIGHT);
    }

    public void setLightningDelay(int delay){
        this.entityData.set(LIGHTNING_DELAY,delay);
    }

    public int getLightningDelay(){
        return this.entityData.get(LIGHTNING_DELAY);
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public UUID getOwner() {
        return owner;
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putFloat("damage",damage);
        tag.putFloat("height",this.getHeight());
        tag.putInt("delay",this.getLightningDelay());
        CompoundNBTHelper.saveUUID(tag,owner,"owner");
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.damage = tag.getFloat("damage");
        this.setHeight(tag.getFloat("height"));
        this.setLightningDelay(tag.getInt("delay"));
        this.setOwner(CompoundNBTHelper.getUUID(tag,"owner"));
        super.load(tag);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(HEIGHT, 10f);
        this.entityData.define(LIGHTNING_DELAY, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double p_19883_) {
        return true;
    }
}
