package com.finderfeed.solarforge.SolarAbilities;

import com.finderfeed.solarforge.SolarForge;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

public class MeteoriteProjectile extends DamagingProjectileEntity {
    public MeteoriteProjectile(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);

    }

    public MeteoriteProjectile(double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, World p_i50174_14_) {
        super(SolarForge.METEORITE.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public MeteoriteProjectile(LivingEntity p_i50175_2_, World p_i50175_9_) {
        super(SolarForge.METEORITE.get(),  p_i50175_9_);
    }

    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        if (!this.level.isClientSide) {
            Vector3d velocityVector = this.getDeltaMovement().multiply(8,8,8);

            this.level.explode(null, DamageSource.DRAGON_BREATH, null, this.position().x, this.position().y, this.position().z, 10, true, Explosion.Mode.DESTROY);
            this.level.explode(null, DamageSource.DRAGON_BREATH, null, this.position().x + velocityVector.x/5, this.position().y+velocityVector.y/15, this.position().z+velocityVector.z/10, 10, true, Explosion.Mode.DESTROY);
            double radius = this.level.random.nextFloat()*1 +4;
            for (int i = (int) -Math.ceil(radius);i < Math.ceil(radius);i++){
                for (int g = (int) -Math.ceil(radius);g < Math.ceil(radius);g++){
                    for (int h = (int) -Math.ceil(radius);h < Math.ceil(radius);h++){
                        if (SolarStrikeEntity.checkTochkaVEllipse(i,g,h,radius,radius,radius)){
                            BlockPos pos = this.getOnPos().offset((int)Math.floor(i),(int)Math.floor(g),(int)Math.floor(h)).offset((int)Math.ceil(velocityVector.x),(int)Math.ceil(velocityVector.y),(int)Math.ceil(velocityVector.z));

                            if (this.level.random.nextFloat() < 0.8){

                                    if (this.level.getBlockState(pos).getDestroySpeed(this.level,pos) >= 0 && this.level.getBlockState(pos).getDestroySpeed(this.level,pos) <= 100){
                                        this.level.setBlock(pos,Blocks.OBSIDIAN.defaultBlockState(), Constants.BlockFlags.DEFAULT);
                                    }
                                }else{
                                if (this.level.getBlockState(pos).getDestroySpeed(this.level,pos) >= 0 && this.level.getBlockState(pos).getDestroySpeed(this.level,pos) <= 100){
                                    this.level.setBlock(pos,Blocks.MAGMA_BLOCK.defaultBlockState(), Constants.BlockFlags.DEFAULT);
                                }
                            }
                        }
                    }
                }
            }
        }
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
            tickCount++;
            if (this.tickCount > 500){
                this.remove();
            }
        }
        for (int i = 0;i<24;i++){
            int radius = 3;
            double offsety = radius*Math.cos(Math.toRadians(i*15));
            double offsetz = radius*Math.sin(Math.toRadians(i*15));
            this.level.addParticle(ParticleTypes.FLAME,this.position().x,this.position().y+offsety,this.position().z+offsetz,0,0,0);
        }
        for (int i = 0;i<24;i++){
            int radius = 3;

            double offsetx = radius*Math.sin(Math.toRadians(i*15));
            this.level.addParticle(ParticleTypes.FLAME,this.position().x+offsetx,this.position().y,this.position().z,0,0,0);
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
    public void load(CompoundNBT cmp) {
        tickCount = cmp.getInt("tick");

        super.load(cmp);
    }

    @Override
    public boolean save(CompoundNBT cmp) {
        cmp.putInt("tick",tickCount);

        return super.save(cmp);
    }
}
