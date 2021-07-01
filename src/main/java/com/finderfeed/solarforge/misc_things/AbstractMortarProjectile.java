package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.List;


public abstract class AbstractMortarProjectile extends DamagingProjectileEntity {

    public double gravity = Helpers.GRAVITY_VELOCITY;       //1 block per second
    public double startingVel = 2;

    protected AbstractMortarProjectile(EntityType<? extends AbstractMortarProjectile> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

    public AbstractMortarProjectile(EntityType<? extends AbstractMortarProjectile> p_i50174_1_, double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, World p_i50174_14_) {
        super(p_i50174_1_, p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public AbstractMortarProjectile(EntityType<? extends AbstractMortarProjectile> p_i50175_1_, LivingEntity p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World p_i50175_9_) {
        super(p_i50175_1_, p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult ctx) {



    }

    @Override
    protected void onHitBlock(BlockRayTraceResult result) {
        causeExplosion(result.getLocation());
        causeExplosionParticles(result.getLocation());
        level.playSound(null,result.getBlockPos().getX()+0.5,result.getBlockPos().getY()+0.5,result.getBlockPos().getZ()+0.5, Sounds.SOLAR_MORTAR_PROJECTILE.get(), SoundCategory.AMBIENT,
                5,1);
        this.remove();
    }




    @Override
    protected float getInertia() {
        return 1F;
    }


    @Override
    public void tick(){
        if (!level.isClientSide){
            Vector3d velocity = getDeltaMovement();


            //setDeltaMovement(velocity.x,0,velocity.y);
            setDeltaMovement(velocity.x,velocity.y- gravity,velocity.z); //decreasing 20 block per second



        }
        this.level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x,this.position().y,this.position().z,0,0,0);
        this.level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x,this.position().y-0.25,this.position().z,0,0,0);
        this.level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x,this.position().y+0.25,this.position().z,0,0,0);
        this.level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x+0.25,this.position().y,this.position().z,0,0,0);
        this.level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x-0.25,this.position().y,this.position().z,0,0,0);
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
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public abstract double getMDamage();
    public abstract double getExplosionRadius();

    public void causeExplosion(Vector3d pos){
        if (!level.isClientSide){
            AxisAlignedBB box = new AxisAlignedBB(-getExplosionRadius(),-getExplosionRadius(),-getExplosionRadius(),getExplosionRadius(),getExplosionRadius(),getExplosionRadius());
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,box.move(pos));
            for (LivingEntity a : entities){
                a.hurt(DamageSource.MAGIC,(float)getMDamage());
            }
        }
    }
    public void causeExplosionParticles(Vector3d pos){

        for (int i = 0;i <16;i++){

            float length =(float) getExplosionRadius();
            double offsetx = length * Math.cos(Math.toRadians(i*22.5));
            double offsetz = length * Math.sin(Math.toRadians(i*22.5));
            this.level.addParticle(ParticlesList.SOLAR_STRIKE_PARTICLE.get(),pos.x +offsetx,pos.y,pos.z +offsetz,0,0.05,0);



        }
        for (int i = 0;i <16;i++){
            for (int g = 0; g < 4;g++){
                float length = (float) getExplosionRadius();
                double offsetx = this.level.random.nextFloat()*length * Math.cos(Math.toRadians(i*22.5));
                double offsetz = this.level.random.nextFloat()*length * Math.sin(Math.toRadians(i*22.5));
                this.level.addParticle(ParticlesList.SOLAR_STRIKE_PARTICLE.get(),pos.x +offsetx,pos.y,pos.z +offsetz,0,0.05,0);

            }

        }
    }
}
