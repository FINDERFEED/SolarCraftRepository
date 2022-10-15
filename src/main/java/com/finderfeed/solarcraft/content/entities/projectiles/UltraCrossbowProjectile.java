package com.finderfeed.solarcraft.content.entities.projectiles;

import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkHooks;


public class UltraCrossbowProjectile extends AbstractHurtingProjectile {
    public static EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(UltraCrossbowProjectile.class, EntityDataSerializers.FLOAT);
    public static EntityDataAccessor<Float> YAW = SynchedEntityData.defineId(UltraCrossbowProjectile.class, EntityDataSerializers.FLOAT);
    public double damage = 0;
    public int livingTicks = 0;

    public UltraCrossbowProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50173_1_, Level p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);

    }

    public UltraCrossbowProjectile(double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, Level p_i50174_14_) {
        super(SolarcraftEntityTypes.ULTRA_CROSSBOW_SHOT.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);

    }

    public UltraCrossbowProjectile(LivingEntity p_i50175_2_, Level p_i50175_9_) {
        super(SolarcraftEntityTypes.ULTRA_CROSSBOW_SHOT.get(),  p_i50175_9_);

    }



    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Override
    protected void onHitEntity(EntityHitResult ctx) {
        level.playSound(null,this.getX(),this.getY(),this.getZ(), SolarcraftSounds.CROSSBOW_SHOT_IMPACT.get(),SoundSource.AMBIENT,5,1);
        if (!level.isClientSide) {

            Entity ent = ctx.getEntity();

            ent.hurt(DamageSource.MAGIC.setProjectile(),(float)damage);

            ((ServerLevel)level).sendParticles(SolarcraftParticleTypes.SOLAR_STRIKE_PARTICLE.get(),ent.getX(),ent.getY()+1.2,ent.getZ(),2,0,0.02,0,0.02);
            if (damage >= 30 && (damage < 120) ){
                level.explode(null,this.getX(),this.getY(),this.getZ(),5,true, Explosion.BlockInteraction.BREAK);
            }else if (damage >= 120){
                level.explode(null,this.getX(),this.getY(),this.getZ(),8,true, Explosion.BlockInteraction.BREAK);
            }
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {

        if (!level.isClientSide){
            if (damage >= 30 && (damage < 120)){
                level.explode(null,this.getX(),this.getY(),this.getZ(),5,true, Explosion.BlockInteraction.BREAK);
            }else if (damage >= 120){
                level.explode(null,this.getX(),this.getY(),this.getZ(),8,true, Explosion.BlockInteraction.BREAK);
            }
            if ((level.getBlockState(result.getBlockPos()).getDestroySpeed(level,result.getBlockPos()) >= 0) &&
                    level.getBlockState(result.getBlockPos()).getDestroySpeed(level,result.getBlockPos()) <= 100) {
                level.destroyBlock(result.getBlockPos(), true);
            }

        }
        level.playSound(null,this.getX(),this.getY(),this.getZ(), SolarcraftSounds.CROSSBOW_SHOT_IMPACT.get(),SoundSource.AMBIENT,5,1);
        this.remove(RemovalReason.KILLED);
    }



    @Override
    protected float getInertia() {
        return 1F;
    }


    @Override
    public void tick(){

        super.tick();
        if (!this.level.isClientSide){

            livingTicks++;
            if (livingTicks >= 600){
                this.remove(RemovalReason.KILLED);
            }
        }

    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }
    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return SolarcraftParticleTypes.INVISIBLE_PARTICLE.get();
    }

    public void setPITCH(float a ) {
        this.entityData.set(PITCH,a);
    }

    public void setYAW(float a ) {
        this.entityData.set(YAW,a);
    }

    @Override
    public void load(CompoundTag cmp) {
        damage = cmp.getDouble("damage_ultra");
        tickCount = cmp.getInt("get_tick_count");
        this.entityData.set(PITCH,cmp.getFloat("curpitch"));
        this.entityData.set(YAW,cmp.getFloat("curyaw"));
        super.load(cmp);
    }

    @Override
    public boolean shouldRender(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
        return true;
    }

    @Override
    public boolean save(CompoundTag cmp) {
        cmp.putDouble("damage_ultra",damage);
        cmp.putInt("get_tick_count",tickCount);
        cmp.putFloat("curyaw",this.entityData.get(YAW));
        cmp.putFloat("curpitch",this.entityData.get(PITCH));

        return super.save(cmp);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PITCH,0f);
        this.entityData.define(YAW,0f);
    }
}
