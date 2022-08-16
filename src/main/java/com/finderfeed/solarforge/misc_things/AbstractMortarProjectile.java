package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.registries.sounds.SolarcraftSounds;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.network.protocol.Packet;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.AABB;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;


import java.util.List;


public abstract class AbstractMortarProjectile extends AbstractHurtingProjectile {

    public double gravity = Helpers.GRAVITY_VELOCITY;       //1 block per second
    public double startingVel = 2;
    private boolean removeit = false;

    protected AbstractMortarProjectile(EntityType<? extends AbstractMortarProjectile> p_i50173_1_, Level p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

    public AbstractMortarProjectile(EntityType<? extends AbstractMortarProjectile> p_i50174_1_, double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, Level p_i50174_14_) {
        super(p_i50174_1_, p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public AbstractMortarProjectile(EntityType<? extends AbstractMortarProjectile> p_i50175_1_, LivingEntity p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level p_i50175_9_) {
        super(p_i50175_1_, p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
    }

    @Override
    protected void onHitEntity(EntityHitResult ctx) {



    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!removeit){
            causeExplosion(result.getLocation());
            causeExplosionParticles(result.getLocation());
        }
        level.playSound(null,result.getBlockPos().getX()+0.5,result.getBlockPos().getY()+0.5,result.getBlockPos().getZ()+0.5, SolarcraftSounds.SOLAR_MORTAR_PROJECTILE.get(), SoundSource.AMBIENT,
                5,1);
        this.removeit = true;
    }




    @Override
    protected float getInertia() {
        return 1F;
    }


    @Override
    public void tick(){
        if (!level.isClientSide){
            Vec3 velocity = getDeltaMovement();


            //setDeltaMovement(velocity.x,0,velocity.y);
            setDeltaMovement(velocity.x,velocity.y- gravity,velocity.z); //decreasing 20 block per second



        }
        this.level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x,this.position().y,this.position().z,0,0,0);
        this.level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x,this.position().y-0.25,this.position().z,0,0,0);
        this.level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x,this.position().y+0.25,this.position().z,0,0,0);
        this.level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x+0.25,this.position().y,this.position().z,0,0,0);
        this.level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x-0.25,this.position().y,this.position().z,0,0,0);
//        else{
//            for (int i = 0;i<16;i++){
//                int radius = 1;
//                double offsety = radius*Math.cos(Math.toRadians(i*22.5));
//                double offsetz = radius*Math.sin(Math.toRadians(i*22.5));
//                this.level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x,this.position().y+offsety,this.position().z+offsetz,0,0,0);
//            }
//            for (int i = 0;i<16;i++){
//                int radius = 1;
//
//                double offsetx = radius*Math.sin(Math.toRadians(i*22.5));
//                this.level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x+offsetx,this.position().y,this.position().z,0,0,0);
//            }
//        }
        if (!level.isClientSide && removeit){
            this.kill();
        }
        super.tick();


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

    public abstract double getMDamage();
    public abstract double getExplosionRadius();

    public void causeExplosion(Vec3 pos){
        if (!level.isClientSide){
            AABB box = new AABB(-getExplosionRadius(),-getExplosionRadius(),-getExplosionRadius(),getExplosionRadius(),getExplosionRadius(),getExplosionRadius());
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,box.move(pos));
            for (LivingEntity a : entities){
                a.hurt(DamageSource.MAGIC,(float)getMDamage());
                a.invulnerableTime=0;
            }
        }
    }
    public void causeExplosionParticles(Vec3 pos){

        for (int i = 0;i <16;i++){

            float length =(float) getExplosionRadius();
            double offsetx = length * Math.cos(Math.toRadians(i*22.5));
            double offsetz = length * Math.sin(Math.toRadians(i*22.5));
            this.level.addParticle(SolarcraftParticleTypes.SOLAR_STRIKE_PARTICLE.get(),pos.x +offsetx,pos.y,pos.z +offsetz,0,0.05,0);



        }
        for (int i = 0;i <16;i++){
            for (int g = 0; g < 4;g++){
                float length = (float) getExplosionRadius();
                double offsetx = this.level.random.nextFloat()*length * Math.cos(Math.toRadians(i*22.5));
                double offsetz = this.level.random.nextFloat()*length * Math.sin(Math.toRadians(i*22.5));
                this.level.addParticle(SolarcraftParticleTypes.SOLAR_STRIKE_PARTICLE.get(),pos.x +offsetx,pos.y,pos.z +offsetz,0,0.05,0);

            }

        }
    }

    @Override
    public boolean save(CompoundTag p_20224_) {
        p_20224_.putBoolean("removeit",removeit);
        return super.save(p_20224_);
    }

    @Override
    public void load(CompoundTag p_20259_) {
        this.removeit = p_20259_.getBoolean("removeit");
        super.load(p_20259_);
    }
}
