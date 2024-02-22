package com.finderfeed.solarcraft.content.abilities.meteorite;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.AlphaInOutOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDDefaultOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDScalingOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.instances.SmokeParticleOptions;
import com.finderfeed.solarcraft.content.abilities.solar_strike.SolarStrikeEntity;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
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

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;



import net.minecraft.world.level.Level;


public class MeteoriteProjectile extends AbstractHurtingProjectile {
    private boolean removeNextTick = false;
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
        removeNextTick = true;
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
        int mod = 5;
        Vec3 v = this.getDeltaMovement().multiply(1,0,1).normalize().multiply(mod,0,mod);
        BlockPos iPos = this.getOnPos().offset(
                (int)v.x,
                -1,
                (int)v.z
        );
        v = Helpers.getBlockCenter(iPos);
        BallParticleOptions ball = new BallParticleOptions(7f,255,115,0,120,true,false);
        int count = 250;
        double angle = Math.PI * 2 / count;
        for (double i = 0; i <= Math.PI * 2;i += angle){
            double x = Math.sin(i);
            double z = Math.cos(i);

            ParticleOptions o;
            if (level.random.nextFloat() > 0.5){
                o = options1;
            }else{
                o = options;
            }
            level.addParticle(ball,
                    v.x + x * (random.nextFloat() * 1),
                    v.y + random.nextFloat() * 2 - 2,
                    v.z + z * (random.nextFloat() * 1),
                    x * (random.nextFloat()*0.15 + 0.05),
                    random.nextFloat() * 0.1,
                    z * (random.nextFloat()*0.15 + 0.05)
            );
            level.addParticle(o,
                    v.x + x * (random.nextFloat() * 6 + 2),
                    v.y,
                    v.z + z * (random.nextFloat() * 6 + 2),
                    x * 0.3 * (level.random.nextFloat() * 2 + 0.1),
                    level.random.nextFloat() * 0.3,
                    z * 0.3 * (level.random.nextFloat() * 2 + 0.1));
        }
    }

    private void onExplode(){
        if (Helpers.isSpellGriefingEnabled((ServerLevel) level())) {
            int i = this.createCrater();
            this.createMeteorite(i);
        }
    }


    private void createMeteorite(int depth){
        int mod = 5;
        Vec3 v = this.getDeltaMovement().multiply(1,0,1).normalize().multiply(mod,0,mod);
        int rad = random.nextInt(2) + 5;
        BlockPos iPos = this.getOnPos().offset(
                (int)v.x,
                (-depth - rad)/2,
                (int)v.z
        );

        for (int x = -rad;x <= rad;x++){
            for (int y = -rad;y <= rad;y++){
                for (int z = -rad;z <= rad;z++){
                    BlockPos pos = new BlockPos(x,y,z);
                    Vec3 c = Helpers.getBlockCenter(pos);
                    if (c.length() <= rad){
                        BlockState state = random.nextFloat() > 0.3 ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.MAGMA_BLOCK.defaultBlockState();
                        level.setBlock(pos.offset(iPos),state,3);
                    }
                }
            }
        }
    }

    private int createCrater(){
        int ellipseWidth = 10 + random.nextInt(2);
        int ellipseHeight = 8 + random.nextInt(2);
        int mod = 5;
        Vec3 v = this.getDeltaMovement().multiply(1,0,1).normalize().multiply(mod,0,mod);
        for (int x = -ellipseWidth; x <= ellipseWidth;x++){
            for (int y = -ellipseHeight; y <= ellipseHeight;y++){
                for (int z = -ellipseWidth; z <= ellipseWidth;z++){
                    BlockPos pos = new BlockPos(
                            x
                            ,y
                            ,z
                    );
                    Vec3 posf = Helpers.getBlockCenter(pos);
                    posf = posf.add(posf.normalize().multiply(
                       random.nextFloat() * 2,
                       random.nextFloat() * 2,
                       random.nextFloat() * 2
                    ));
                    if (FDMathHelper.isInEllipse((float)posf.x,(float)posf.y,(float)posf.z,ellipseWidth,ellipseHeight)){
                        this.tryDeleteBlock(pos.offset(this.getOnPos()).offset(
                                (int)v.x,0,(int)v.z
                        ));
                    }
                }
            }
        }
        return ellipseHeight;
    }

    private void tryDeleteBlock(BlockPos pos){
        BlockState state = level.getBlockState(pos);
        float destroySpeed = state.getDestroySpeed(level,pos);
        if (destroySpeed > 0){
            level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
        }
    }

    private void trySummonBlock(BlockPos pos){

    }

    @Override
    protected float getInertia() {
        return 1F;
    }

    @Override
    public void tick(){
        if (removeNextTick){
            this.remove(RemovalReason.KILLED);
        }
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
        int freq = 1;
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
        float r = 2.8f;
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
                            level.random.nextFloat() * 1 - 0.5,
                            level.random.nextFloat() * 1 - 0.5,
                            level.random.nextFloat() * 1 - 0.5
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
