package com.finderfeed.solarcraft.content.abilities.meteorite;

import com.finderfeed.solarcraft.client.particles.fd_particle.AlphaInOutOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDDefaultOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDScalingOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.instances.SmokeParticleOptions;
import com.finderfeed.solarcraft.content.abilities.solar_strike.SolarStrikeEntity;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.CameraShakePacket;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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



import net.minecraft.world.level.Level;


public class MeteoriteProjectile extends AbstractHurtingProjectile {
    public MeteoriteProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50173_1_, Level p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);

    }

    public MeteoriteProjectile(double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, Level p_i50174_14_) {
        super(SCEntityTypes.METEORITE.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public MeteoriteProjectile(LivingEntity p_i50175_2_, Level p_i50175_9_) {
        super(SCEntityTypes.METEORITE.get(),  p_i50175_9_);
    }

    @Override
    protected void onHit(HitResult p_70227_1_) {
        if (!this.level().isClientSide) {
            this.onExplode();
            FDPacketUtil.sendToTrackingEntity(this,new CameraShakePacket(0,10,40,1.5f));
        }else{
            this.explodeParticles();
        }
        this.remove(RemovalReason.KILLED);
    }

    private void explodeParticles(){
        float c = 0.1f;
        FDDefaultOptions doptions = new FDDefaultOptions(4f,120,c,c,c,1f,false,false);
        SmokeParticleOptions options = new SmokeParticleOptions(
                doptions,
                new FDScalingOptions(0,30),
                new AlphaInOutOptions(0,0)
        );
        FDDefaultOptions doptions1 = new FDDefaultOptions(4f,120,1f,0.5f,0.3f,1f,false,true);
        SmokeParticleOptions options1 = new SmokeParticleOptions(
                doptions1,
                new FDScalingOptions(0,60),
                new AlphaInOutOptions(0,0)
        );
        int count = 200;
        double angle = Math.PI * 2 / count;
        for (double i = 0; i <= Math.PI * 2;i += angle){
            double x = Math.sin(i);
            double z = Math.cos(i);
            Vec3 v = this.position().add(x * 4,level.random.nextFloat(),z * 4).add(this.getDeltaMovement()
                    .multiply(8,8,8));
            ParticleOptions o;
            if (level.random.nextFloat() > 0.5){
                o = options1;
            }else{
                o = options;
            }
            level.addParticle(o,v.x,v.y,v.z,
                    x * 0.3 * (level.random.nextFloat() * 0.9 + 0.1),
                    level.random.nextFloat() * 0.1,
                    z * 0.3 * (level.random.nextFloat() * 0.9 + 0.1));
        }
    }

    private void onExplode(){
        Vec3 velocityVector = this.getDeltaMovement().multiply(8,8,8);
        if (Helpers.isSpellGriefingEnabled((ServerLevel) level())) {
            this.level().explode(null, level().damageSources().magic(), null, this.position().x, this.position().y, this.position().z, 10, true, Level.ExplosionInteraction.BLOCK);
            this.level().explode(null, level().damageSources().magic(), null, this.position().x + velocityVector.x / 5, this.position().y + velocityVector.y / 15, this.position().z + velocityVector.z / 10, 10, true, Level.ExplosionInteraction.BLOCK);
            double radius = this.level().random.nextFloat() * 1 + 4;
            for (int i = (int) -Math.ceil(radius); i < Math.ceil(radius); i++) {
                for (int g = (int) -Math.ceil(radius); g < Math.ceil(radius); g++) {
                    for (int h = (int) -Math.ceil(radius); h < Math.ceil(radius); h++) {
                        if (SolarStrikeEntity.checkTochkaVEllipse(i, g, h, radius, radius, radius)) {
                            BlockPos pos = this.getOnPos().offset((int) Math.floor(i), (int) Math.floor(g), (int) Math.floor(h)).offset((int) Math.ceil(velocityVector.x), (int) Math.ceil(velocityVector.y), (int) Math.ceil(velocityVector.z));

                            if (this.level().random.nextFloat() < 0.8) {

                                if (this.level().getBlockState(pos).getDestroySpeed(this.level(), pos) >= 0 && this.level().getBlockState(pos).getDestroySpeed(this.level(), pos) <= 100) {
                                    this.level().setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 3);
                                }
                            } else {
                                if (this.level().getBlockState(pos).getDestroySpeed(this.level(), pos) >= 0 && this.level().getBlockState(pos).getDestroySpeed(this.level(), pos) <= 100) {
                                    this.level().setBlock(pos, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
                                }
                            }
                        }
                    }
                }
            }
        }else{
            this.level().explode(null, level().damageSources().magic(), null, this.position().x, this.position().y, this.position().z, 10, true, Level.ExplosionInteraction.NONE);
            this.level().explode(null, level().damageSources().magic(), null, this.position().x + velocityVector.x / 5, this.position().y + velocityVector.y / 15, this.position().z + velocityVector.z / 10, 10, true, Level.ExplosionInteraction.NONE);
        }
    }

    @Override
    protected float getInertia() {
        return 1F;
    }

    @Override
    public void tick(){
        super.tick();
        if (!this.level().isClientSide){
            if (this.tickCount > 500){
                this.remove(RemovalReason.KILLED);
            }
        }else{
            this.spawnParticles();
        }
    }

    public void spawnParticles(){
        int freq = 2;
        float c = 0.1f;
        FDDefaultOptions doptions = new FDDefaultOptions(1f,60,c,c,c,1f,false,false);
        SmokeParticleOptions options = new SmokeParticleOptions(
                doptions,
                new FDScalingOptions(0,60),
                new AlphaInOutOptions(0,0)
        );
        FDDefaultOptions doptionsOrange = new FDDefaultOptions(1f,60,1f,0.5f,0.1f,1f,false,true);
        SmokeParticleOptions optionsOrange = new SmokeParticleOptions(
                doptionsOrange,
                new FDScalingOptions(0,60),
                new AlphaInOutOptions(0,0)
        );
        float r = 2.5f;
        for (int x = -freq;x <= freq;x++){
            for (int y = -freq;y <= freq;y++){
                for (int z = -freq;z <= freq;z++){
                    Vec3 v = new Vec3(x,y,z).normalize();
                    SmokeParticleOptions o;
                    if (v.dot(this.getDeltaMovement().reverse()) < 0){
                        o = optionsOrange;
                    }else{
                        o = options;
                    }

                    v = v.multiply(r,r,r).add(
                            level.random.nextFloat() * 0.5 - 0.25,
                            level.random.nextFloat() * 0.5 - 0.25,
                            level.random.nextFloat() * 0.5 - 0.25
                    ).add(this.position());

                    level.addParticle(o,true,v.x,v.y,v.z,
                            level.random.nextFloat() * 0.1- 0.05,
                            level.random.nextFloat() * 0.1- 0.05,
                            level.random.nextFloat() * 0.1- 0.05
                    );
                }
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
