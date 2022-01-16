package com.finderfeed.solarforge.magic_items.items.solar_disc_gun;

import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.registries.entities.Entities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;


import java.util.List;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;


public class SolarDiscProjectile extends AbstractHurtingProjectile {
    public int count = 0;
    public float pitch = 0;
    public float yaw = 0;
    public SolarDiscProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50173_1_, Level p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);

    }

    public SolarDiscProjectile(double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, Level p_i50174_14_) {
        super(Entities.SOLAR_DISC.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public SolarDiscProjectile(LivingEntity p_i50175_2_, Level p_i50175_9_) {
        super(Entities.SOLAR_DISC.get(),  p_i50175_9_);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_230299_1_) {
        if (!level.isClientSide) {
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult ctx) {

        if (!this.level.isClientSide && ctx.getEntity() instanceof LivingEntity ){
            LivingEntity ent = (LivingEntity) ctx.getEntity();
            ent.hurt(DamageSource.MAGIC,5.0f);
            AABB box = new AABB(-10,-5,-10,10,5,10).move(this.position().x,this.position().y,this.position().z);
            List<Mob> entities = this.level.getEntitiesOfClass(Mob.class,box);
            entities.remove(ent);
            entities.removeIf(LivingEntity::isDeadOrDying);
            Vec3 vectorPos = this.position();

            if (entities.size() != 0 ) {
                int rand = (int)Math.floor(this.level.random.nextFloat()*(entities.size()-1));
                Vec3 vec = entities.get(rand).position();
                Vec3 minxVect = new Vec3(vec.x-vectorPos.x,vec.y-vectorPos.y+this.level.random.nextDouble()+0.4,vec.z-vectorPos.z);
                count++;
                this.setDeltaMovement(minxVect.normalize());
                ctx.getEntity().invulnerableTime = 0;

            }
            if (count == 5 || entities.size() == 0){
                this.remove(RemovalReason.KILLED);
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
        return ParticlesList.INVISIBLE_PARTICLE.get();
    }


    @Override
    public void load(CompoundTag cmp) {
        tickCount = cmp.getInt("tick");
        count = cmp.getInt("count");
        super.load(cmp);
    }

    @Override
    public boolean shouldRender(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
        return true;
    }

    @Override
    public boolean save(CompoundTag cmp) {
        cmp.putInt("tick",tickCount);
        cmp.putInt("count",count);
        return super.save(cmp);
    }
}
