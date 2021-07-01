package com.finderfeed.solarforge.magic_items.items.projectiles;

import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.misc_things.SolarStrikeParticle;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.UUID;

public class UltraCrossbowProjectile extends DamagingProjectileEntity {
    public static DataParameter<Float> PITCH = EntityDataManager.defineId(UltraCrossbowProjectile.class, DataSerializers.FLOAT);
    public static DataParameter<Float> YAW = EntityDataManager.defineId(UltraCrossbowProjectile.class, DataSerializers.FLOAT);
    public double damage = 0;
    public int livingTicks = 0;

    public UltraCrossbowProjectile(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);

    }

    public UltraCrossbowProjectile(double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, World p_i50174_14_) {
        super(Projectiles.ULTRA_CROSSBOW_SHOT.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);

    }

    public UltraCrossbowProjectile(LivingEntity p_i50175_2_, World p_i50175_9_) {
        super(Projectiles.ULTRA_CROSSBOW_SHOT.get(),  p_i50175_9_);

    }



    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult ctx) {
        level.playSound(null,this.getX(),this.getY(),this.getZ(), Sounds.CROSSBOW_SHOT_IMPACT.get(),SoundCategory.AMBIENT,5,1);
        if (!level.isClientSide) {

            Entity ent = ctx.getEntity();

            ent.hurt(DamageSource.MAGIC,(float)damage);

            ((ServerWorld)level).sendParticles(ParticlesList.SOLAR_STRIKE_PARTICLE.get(),ent.getX(),ent.getY()+1.2,ent.getZ(),2,0,0.02,0,0.02);
            if (damage >= 30 && (damage < 120) ){
                level.explode(null,this.getX(),this.getY(),this.getZ(),5,true, Explosion.Mode.BREAK);
            }else if (damage >= 120){
                level.explode(null,this.getX(),this.getY(),this.getZ(),8,true, Explosion.Mode.BREAK);
            }
            this.remove();
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult result) {

        if (!level.isClientSide){
            if ((level.getBlockState(result.getBlockPos()).getDestroySpeed(level,result.getBlockPos()) >= 0) &&
                    level.getBlockState(result.getBlockPos()).getDestroySpeed(level,result.getBlockPos()) <= 100) {

                if (damage >= 30 && (damage < 120)){
                    level.explode(null,this.getX(),this.getY(),this.getZ(),5,true, Explosion.Mode.BREAK);
                }else if (damage >= 120){
                    level.explode(null,this.getX(),this.getY(),this.getZ(),8,true, Explosion.Mode.BREAK);
                }
                level.destroyBlock(result.getBlockPos(), true);
            }

        }
        level.playSound(null,this.getX(),this.getY(),this.getZ(), Sounds.CROSSBOW_SHOT_IMPACT.get(),SoundCategory.AMBIENT,5,1);
        this.remove();
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
                this.remove();
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
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected IParticleData getTrailParticle() {
        return ParticlesList.INVISIBLE_PARTICLE.get();
    }

    public void setPITCH(float a ) {
        this.entityData.set(PITCH,a);
    }

    public void setYAW(float a ) {
        this.entityData.set(YAW,a);
    }

    @Override
    public void load(CompoundNBT cmp) {
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
    public boolean save(CompoundNBT cmp) {
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
