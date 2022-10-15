package com.finderfeed.solarcraft.content.abilities.meteorite;

import com.finderfeed.solarcraft.content.abilities.solar_strike.SolarStrikeEntity;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.Helpers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;

import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import net.minecraft.world.level.Explosion;

import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;


public class MeteoriteProjectile extends AbstractHurtingProjectile {
    public MeteoriteProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50173_1_, Level p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);

    }

    public MeteoriteProjectile(double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, Level p_i50174_14_) {
        super(SolarCraft.METEORITE.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public MeteoriteProjectile(LivingEntity p_i50175_2_, Level p_i50175_9_) {
        super(SolarCraft.METEORITE.get(),  p_i50175_9_);
    }

    @Override
    protected void onHit(HitResult p_70227_1_) {
        if (!this.level.isClientSide) {
            Vec3 velocityVector = this.getDeltaMovement().multiply(8,8,8);
            if (Helpers.isSpellGriefingEnabled((ServerLevel) level)) {
                this.level.explode(null, DamageSource.DRAGON_BREATH, null, this.position().x, this.position().y, this.position().z, 10, true, Explosion.BlockInteraction.DESTROY);
                this.level.explode(null, DamageSource.DRAGON_BREATH, null, this.position().x + velocityVector.x / 5, this.position().y + velocityVector.y / 15, this.position().z + velocityVector.z / 10, 10, true, Explosion.BlockInteraction.DESTROY);
                double radius = this.level.random.nextFloat() * 1 + 4;
                for (int i = (int) -Math.ceil(radius); i < Math.ceil(radius); i++) {
                    for (int g = (int) -Math.ceil(radius); g < Math.ceil(radius); g++) {
                        for (int h = (int) -Math.ceil(radius); h < Math.ceil(radius); h++) {
                            if (SolarStrikeEntity.checkTochkaVEllipse(i, g, h, radius, radius, radius)) {
                                BlockPos pos = this.getOnPos().offset((int) Math.floor(i), (int) Math.floor(g), (int) Math.floor(h)).offset((int) Math.ceil(velocityVector.x), (int) Math.ceil(velocityVector.y), (int) Math.ceil(velocityVector.z));

                                if (this.level.random.nextFloat() < 0.8) {

                                    if (this.level.getBlockState(pos).getDestroySpeed(this.level, pos) >= 0 && this.level.getBlockState(pos).getDestroySpeed(this.level, pos) <= 100) {
                                        this.level.setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 3);
                                    }
                                } else {
                                    if (this.level.getBlockState(pos).getDestroySpeed(this.level, pos) >= 0 && this.level.getBlockState(pos).getDestroySpeed(this.level, pos) <= 100) {
                                        this.level.setBlock(pos, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
                                    }
                                }
                            }
                        }
                    }
                }
            }else{
                this.level.explode(null, DamageSource.DRAGON_BREATH, null, this.position().x, this.position().y, this.position().z, 10, true, Explosion.BlockInteraction.NONE);
                this.level.explode(null, DamageSource.DRAGON_BREATH, null, this.position().x + velocityVector.x / 5, this.position().y + velocityVector.y / 15, this.position().z + velocityVector.z / 10, 10, true, Explosion.BlockInteraction.NONE);
            }
        }
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
            tickCount++;
            if (this.tickCount > 500){
                this.remove(RemovalReason.KILLED);
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
    public Packet<?> getAddEntityPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
    }
    @Override
    public void load(CompoundTag cmp) {
        tickCount = cmp.getInt("tick");

        super.load(cmp);
    }

    @Override
    public boolean save(CompoundTag cmp) {
        cmp.putInt("tick",tickCount);

        return super.save(cmp);
    }
}
