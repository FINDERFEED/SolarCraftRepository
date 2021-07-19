package com.finderfeed.solarforge.magic_items.items.solar_disc_gun;

import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class SolarDiscProjectile extends DamagingProjectileEntity {
    public int count = 0;
    public float pitch = 0;
    public float yaw = 0;
    public SolarDiscProjectile(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);

    }

    public SolarDiscProjectile(double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, World p_i50174_14_) {
        super(Projectiles.SOLAR_DISC.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public SolarDiscProjectile(LivingEntity p_i50175_2_, World p_i50175_9_) {
        super(Projectiles.SOLAR_DISC.get(),  p_i50175_9_);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        this.remove();
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult ctx) {

        if (!this.level.isClientSide && ctx.getEntity() instanceof LivingEntity ){
            LivingEntity ent = (LivingEntity) ctx.getEntity();
            ent.hurt(DamageSource.MAGIC,5.0f);
            AxisAlignedBB box = new AxisAlignedBB(-10,-5,-10,10,5,10).move(this.position().x,this.position().y,this.position().z);
            List<MobEntity> entities = this.level.getEntitiesOfClass(MobEntity.class,box);
            entities.remove(ent);
            entities.removeIf(LivingEntity::isDeadOrDying);
            Vector3d vectorPos = this.position();

            if (entities.size() != 0 ) {
                int rand = (int)Math.floor(this.level.random.nextFloat()*(entities.size()-1));
                Vector3d vec = entities.get(rand).position();
                Vector3d minxVect = new Vector3d(vec.x-vectorPos.x,vec.y-vectorPos.y+this.level.random.nextDouble()+0.4,vec.z-vectorPos.z);
                count++;
                this.setDeltaMovement(minxVect.normalize());
                ctx.getEntity().invulnerableTime = 0;

            }
            if (count == 5 || entities.size() == 0){
                this.remove();
            }

        }
    }

    @Override
    protected float getInertia() {
        return 1F;
    }

    @Override
    public void tick(){
        super.tick();
        if (!this.level.isClientSide){
            tickCount++;
            if (this.tickCount > 500){
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


    @Override
    public void load(CompoundNBT cmp) {
        tickCount = cmp.getInt("tick");
        count = cmp.getInt("count");
        super.load(cmp);
    }

    @Override
    public boolean shouldRender(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
        return true;
    }

    @Override
    public boolean save(CompoundNBT cmp) {
        cmp.putInt("tick",tickCount);
        cmp.putInt("count",count);
        return super.save(cmp);
    }
}
