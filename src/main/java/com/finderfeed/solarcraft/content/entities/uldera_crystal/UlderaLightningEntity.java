package com.finderfeed.solarcraft.content.entities.uldera_crystal;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.lightning_particle.LightningParticleOptions;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.CameraShakePacket;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import java.util.UUID;

public class UlderaLightningEntity extends Entity {

    public static final EntityDataAccessor<Float> HEIGHT = SynchedEntityData.defineId(UlderaLightningEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> LIGHTNING_DELAY = SynchedEntityData.defineId(UlderaLightningEntity.class, EntityDataSerializers.INT);

    private static final float DAMAGE_BOX_RAD = 3.5f;

    public static final AABB DAMAGE_BOX = new AABB(-DAMAGE_BOX_RAD,-DAMAGE_BOX_RAD,-DAMAGE_BOX_RAD,DAMAGE_BOX_RAD,0,DAMAGE_BOX_RAD);
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
            this.kill();
        }
    }

    private void clientTick(){
        if (this.tickCount < this.getLightningDelay()){
            int c = 12;
            Vec3 p = this.position();
            double radius = 1;
            double angle = Math.PI * 2 / c;
            BallParticleOptions options = BallParticleOptions.Builder.begin()
                    .setSize(0.2f)
                    .setRGB(90,0,186)
                    .setShouldShrink(true)
                    .setLifetime(20)
                    .setPhysics(false)
                    .build();
            for (int i = 0; i < c; i++){
                double x = Math.sin(angle * i + random.nextFloat() * angle);
                double z = Math.cos(angle * i + random.nextFloat() * angle);
                Vec3 ppos = p.add(x * radius,0,z * radius);
                Vec3 speed = new Vec3(0.05 * -x,0.1,0.05 * -z);
                level.addParticle(options,ppos.x,ppos.y,ppos.z,
                        speed.x + random.nextFloat() * 0.02 - 0.01,speed.y,speed.z + random.nextFloat() * 0.02 - 0.01);

                level.addParticle(options,
                        ppos.x + random.nextFloat() * 0.5 - 0.25,
                        ppos.y,
                        ppos.z + random.nextFloat() * 0.5 - 0.25,
                        x * 0.05 + random.nextFloat() * 0.05 - 0.025,
                        0,
                        z * 0.05 + random.nextFloat() * 0.05 - 0.025);
            }
        } else if (this.tickCount == this.getLightningDelay()){
            RandomSource r = level.random;
            BallParticleOptions options = BallParticleOptions.Builder.begin()
                    .setSize(0.2f)
                    .setRGB(90,0,186)
                    .setShouldShrink(true)
                    .setLifetime(60)
                    .setPhysics(false)
                    .build();
            LightningParticleOptions optionsl = new LightningParticleOptions(1f, 90, 0, 186, -1, 20, false);
            for (int i = 0; i < 50;i++){
                Vec3 ppos = this.position().add(0,0.1,0);
                level.addParticle(options,ppos.x,ppos.y,ppos.z,
                        (r.nextDouble()*2-1)*0.1f,
                        r.nextDouble()*0.1f,
                        (r.nextDouble()*2-1)*0.1f);

                if (i % 2 == 0) {
                    level.addParticle(optionsl,
                            ppos.x, ppos.y, ppos.z,
                            (r.nextDouble()*2-1)*0.1f,
                            r.nextDouble()*0.1f,
                            (r.nextDouble()*2-1)*0.1f);
                }
            }
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
                entity.hurt(level.damageSources().mobAttack(owner),damage);
            }else{
                entity.hurt(SCDamageSources.SHADOW, damage);
            }
        }
        Vec3 c = this.position();
        PacketDistributor.NEAR.with(new PacketDistributor.TargetPoint(
                c.x,c.y,c.z,10,this.level().dimension()
        )).send(new CameraShakePacket(0,10,10,0.35f));
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
        return super.shouldRender(p_20296_,p_20297_,p_20298_);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double smth) {
        double d0 = 64.0D * getViewScale();
        return smth < d0 * d0;
    }
}
