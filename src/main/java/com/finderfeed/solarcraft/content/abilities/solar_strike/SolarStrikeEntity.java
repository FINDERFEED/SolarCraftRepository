package com.finderfeed.solarcraft.content.abilities.solar_strike;


import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticle;
import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.AlphaInOutOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDDefaultOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDScalingOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.instances.SmokeParticleOptions;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.SendShapeParticlesPacket;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.SphereParticleShape;
import com.finderfeed.solarcraft.content.entities.not_alive.MyFallingBlockEntity;
import com.finderfeed.solarcraft.events.other_events.event_handler.SCEventHandler;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmitterData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmmitterPacket;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.instances.ebpe_processor.EBPEmitterProcessorData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.instances.random_speed.RandomSpeedProcessorData;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.CameraShakePacket;
import com.finderfeed.solarcraft.packet_handler.packets.misc_packets.SolarStrikeEntityDoExplosion;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SolarStrikeEntity extends Entity {

    public static int AFTER_EXPLOSION_TIME = 10;
    public static int RAYS_COUNT = 3;
    public static final int PARTIAL_EXPLOSION_RANGE = 3;
    public static final int RANDOM_RADIUS = 5;
    public static final int BASE_RADIUS = 10;
    public static final int BASE_MAX_DEPTH = 25;
    public static final int RANDOM_DEPTH = 5;
    public static int TIME_UNTIL_EXPLOSION = 60;
    private UUID owner;

    public static EntityDataAccessor<Integer> LIFE = SynchedEntityData.defineId(SolarStrikeEntity.class, EntityDataSerializers.INT);
    public int life = 0;
    public SolarStrikeEntity(EntityType<? extends Entity> p_i48581_1_, Level p_i48581_2_) {
        super(SCEntityTypes.SOLAR_STRIKE_ENTITY_REG.get(), p_i48581_2_);
    }
//
//    public static AttributeSupplier.Builder createAttributes() {
//        return createLivingAttributes().add(Attributes.FOLLOW_RANGE);
//    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        //super.addAdditionalSaveData(compound);
        if (owner != null) {
            compound.putUUID("owner", owner);
        }
        compound.putInt("life", life);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        //super.readAdditionalSaveData(compound);
        if (compound.contains("owner")){
            this.owner = compound.getUUID("owner");
        }
        life = compound.getInt("life");
    }
    public int getLifeTicks(){
        return this.entityData.get(LIFE);
    }


    @Override
    public void tick(){


        if (!this.isRemoved()){
            if (!level.isClientSide) {
                if (tickCount == TIME_UNTIL_EXPLOSION) {
                    this.explode();
                }else if (tickCount >= TIME_UNTIL_EXPLOSION + AFTER_EXPLOSION_TIME){
                    this.remove(RemovalReason.DISCARDED);
                }
            }else{
                if (tickCount < TIME_UNTIL_EXPLOSION) {
                    this.particlesBeforeExplosion();
                }else if (tickCount == TIME_UNTIL_EXPLOSION){
                    this.particlesOnExplosion();
                }
            }
        }
    }

    private void particlesOnExplosion(){

        for (float y = 0; y < 100;y+=1f){
            int r = level.random.nextInt(25) + 230;
            int g = level.random.nextInt(25) + 230;
            int b = level.random.nextInt(30);
            Vec3 pos = this.position().add(
                    level.random.nextFloat() * 2 - 1,
                    y + random.nextFloat(),
                    level.random.nextFloat() * 2 - 1);
            BallParticleOptions options = new BallParticleOptions(2.5f + random.nextFloat() * 3,
                    r,g,b,80,true,false);
            level.addParticle(options,pos.x,pos.y,pos.z,
                    (random.nextFloat() * 0.1 - 0.05),
                    random.nextFloat() * 0.1-0.05,
                    (random.nextFloat() * 0.1 - 0.05));
        }

        int count = 200;
        double angle = Math.PI * 2 / count;

        for (double i = 0; i <= Math.PI * 2;i += angle){
            float radius = random.nextFloat() + 1;
            double x = Math.sin(i) * radius;
            double z = Math.cos(i) * radius;
            Vec3 pos = Helpers.getBlockCenter(this.getOnPos()).add(x,0.5,z);
            int r = level.random.nextInt(25) + 230;
            int g = level.random.nextInt(25) + 230;
            int b = level.random.nextInt(30);

            BallParticleOptions options = new BallParticleOptions(5f + random.nextFloat() * 3,
                    r,g,b,80,true,false);
            level.addParticle(options,pos.x,pos.y,pos.z,
                    x * (0.05 + random.nextFloat() * 0.2),
                    random.nextFloat() * 0.1,
                    z * (0.05 + random.nextFloat() * 0.2));
        }
    }

    public float getExplosionCompletionPercent(float pticks){
        return Mth.clamp((tickCount + pticks) / TIME_UNTIL_EXPLOSION,0,1);
    }

    public List<Vec3> getRayPositions(int rayCount,float pticks){
        float p = this.getExplosionCompletionPercent(pticks);
        float radius = 20 * (1 - p);
        double t = (1-p) * Math.PI;
        double angle = Math.PI * 2 / rayCount;
        List<Vec3> vs = new ArrayList<>();
        for (double i = 0; i < Math.PI * 2;i += angle){
            double x = Math.sin(i + t) * radius;
            double z = Math.cos(i + t) * radius;
            Vec3 pos = Helpers.getBlockCenter(this.getOnPos()).add(x,0.5,z);
            vs.add(new Vec3(pos.x,pos.y,pos.z));
        }
        return vs;
    }

    private void particlesBeforeExplosion(){
        float psize = 1.5f;
        for (Vec3 pos : this.getRayPositions(RAYS_COUNT,0.5f)){
            int r = 230 + level.random.nextInt(25);
            int g = 230 + level.random.nextInt(25);
            int b = 10 + level.random.nextInt(10);
            BallParticleOptions options = new BallParticleOptions(psize,r,g,b,TIME_UNTIL_EXPLOSION,true,false);
            level.addParticle(options,pos.x,pos.y,pos.z,
                    level.random.nextFloat()*0.015,
                    level.random.nextFloat()*0.015,
                    level.random.nextFloat()*0.015
            );
        }
    }


    private void explode(){
        FDPacketUtil.sendToTrackingEntity(this,new CameraShakePacket(0,10,10,2f));
        level.playSound(null,this.getX(),this.getY(),this.getZ(),SCSounds.SOLAR_STRIKE_ATTACK.get(),SoundSource.MASTER,40f,1f);
        if (SCEventHandler.isExplosionBlockerAround(level(), Helpers.getBlockCenter(this.getOnPos())) || !Helpers.isSpellGriefingEnabled((ServerLevel) level)) return;
        int radius = level.random.nextInt(RANDOM_RADIUS) + BASE_RADIUS;
        int depth = level.random.nextInt(RANDOM_DEPTH) + BASE_MAX_DEPTH;
        for (int x = -radius;x < radius;x++){
            for (int z = -radius;z < radius;z++){
                for (int y = -depth; y <= depth;y++){
                    float mod = level.random.nextFloat() * PARTIAL_EXPLOSION_RANGE;
                    Vec3 v = new Vec3(x + 0.5f,0,z + 0.5f).normalize().multiply(mod,mod,mod);
                    float xf = x + (float) v.x;
                    float yf = y;
                    float zf = z + (float) v.z;
                    if (FDMathHelper.isInEllipse(xf,yf,zf,radius,depth)){
                        this.processBlockExplosion(
                                x,
                                y,
                                z
                        );
                    }
                }
            }
        }
        AABB damageBox = new AABB(-radius,-depth,-radius,radius,depth,radius).inflate(3).move(this.position());
        Entity owner = this.getOwner();
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class,damageBox)){
            if (entity != owner){
                if (owner instanceof Player player) {
                    entity.hurt(SCDamageSources.playerArmorPierce(player),SolarcraftConfig.SOLAR_STRIKE_DAMAGE.get().floatValue());
                }else{
                    entity.hurt(level.damageSources().magic(),SolarcraftConfig.SOLAR_STRIKE_DAMAGE.get().floatValue());
                }
            }
        }
    }

    private void processBlockExplosion(int x,int y,int z){
        BlockPos pos = new BlockPos(
                (int)(x + this.getX()),
                (int)(y + this.getY()),
                (int)(z + this.getZ())
        );
        BlockState state = level.getBlockState(pos);
        float destroySpeed = state.getDestroySpeed(level,pos);
        if (destroySpeed > 0){
            if (!state.hasBlockEntity() && !(state.getBlock() instanceof LiquidBlock)) {
                this.trySummonBlock(state,y, pos);
            }
            level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
        }
    }

    private void trySummonBlock(BlockState state,int y,BlockPos pos){
        if (Math.abs(y) < 3 && level.random.nextFloat() < 0.2){
            Vec3 center = Helpers.getBlockCenter(pos);
            MyFallingBlockEntity fallingBlock = new MyFallingBlockEntity(level,center.x,center.y,center.z,state);
            fallingBlock.setNoPhysicsTime(3);
            Vec3 between = center.multiply(1,0,1).subtract(this.position().multiply(1,0,1)).normalize();
            Vec3 speed = new Vec3(
                    between.x  * (random.nextFloat() + 0.5),
                    0.5 + random.nextFloat() * 2-0.5,
                    between.z * (random.nextFloat() + 0.5)
            );
            if (speed.x < 0.05 && speed.z < 0.05){
                speed = speed.add(random.nextFloat() * 0.2 - 0.1,0,random.nextFloat() * 0.2 - 0.1);
            }
            fallingBlock.setDeltaMovement(
                    speed
            );
            level.addFreshEntity(fallingBlock);
            float r;
            float g;
            float b;
            if (random.nextFloat() > 0.2){
                r = 0.2f + random.nextFloat() * 0.05f;
                g = 0.2f + random.nextFloat() * 0.05f;
                b = 0.2f + random.nextFloat() * 0.05f;
            }else{
                r = 0.8f - random.nextFloat() * 0.1f;
                g = 0.8f - random.nextFloat() * 0.1f;
                b = 0.8f - random.nextFloat() * 0.1f;
            }
            FDDefaultOptions defaultOptions = new FDDefaultOptions(3f,30,r,g,b,1f,false,false);
            ParticleEmitterData data = new ParticleEmitterData()
                    .setPos(center.x,center.y,center.z)
                    .setParticle(new SmokeParticleOptions(
                            defaultOptions,
                            new FDScalingOptions(0,30),
                            new AlphaInOutOptions(0,0)
                    ))
                    .addParticleProcessor(new RandomSpeedProcessorData(0.025,0.025,0.025))
                    .addParticleEmitterProcessor(new EBPEmitterProcessorData(fallingBlock.getId()));
            FDPacketUtil.sendToTrackingEntity(this,new ParticleEmmitterPacket(data));
        }
    }


    public void setOwner(LivingEntity owner) {
        this.owner = owner.getUUID();
    }

    public Entity getOwner() {
        if (level() instanceof ServerLevel s && owner != null) {
            return s.getEntity(owner);
        }
        return null;
    }

    @Override
    protected void defineSynchedData() {
        //super.defineSynchedData();
        this.entityData.define(LIFE,0);
    }


    public void doSolarStrikeExplosion(BlockPos pos) {
        if (!SCEventHandler.isExplosionBlockerAround(level(), Helpers.getBlockCenter(pos)) && Helpers.isSpellGriefingEnabled((ServerLevel) level())) {
            //8
            //3 + Math.ceil(randomRadius) * 3
            double randomRadius = this.level().random.nextFloat() * 2 + 10;
            double randomHeight = this.level().random.nextFloat() * 3 + Math.ceil(randomRadius) * 3;

            for (int i = (int) -Math.ceil(randomRadius); i <= (int) Math.ceil(randomRadius); i++) {
                for (int g = (int) -Math.ceil(randomRadius); g <= (int) Math.ceil(randomRadius); g++) {
                    for (int k = (int) -Math.ceil(randomHeight); k <= (int) Math.ceil(randomHeight); k++) {
                        if (checkTochkaVEllipse(i, k, g, randomRadius, randomHeight, randomRadius)) {
                            Vec3 vec = new Vec3(i, 0, g);

                            if ((this.level().random.nextDouble() * 0.5 + 1) * vec.length() < randomRadius) {

                                if (this.level().getBlockState(pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g))).getDestroySpeed(this.level(), pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g))) >= 0
                                        && this.level().getBlockState(pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g))).getDestroySpeed(this.level(), pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g))) <= 100) {
                                    this.level().setBlock(pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g)), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                                }


                            }

                        }
                    }
                }

            }//pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g))
        }
    }

    public static boolean checkTochkaVEllipse(double xtochka,double ytochka,double ztochka,double xrad,double yrad,double zrad){
        double first;
        double second;
        double third;

        if (xrad != 0) {
            first = (xtochka * xtochka) / (xrad * xrad);
        }else{
            first = 0;
        }
        if (yrad != 0) {
            second = (ytochka*ytochka)/(yrad*yrad);
        }else{
            second = 0;
        }
        if (zrad != 0) {
            third = (ztochka*ztochka)/(zrad*zrad);
        }else{
            third = 0;
        }


        return first + second + third <= 1;
    }

}
/*
int radius = 21-h;

            BlockPos startposverh = pos.offset(radius,-h,0);
            BlockPos startposniz = pos.offset(radius,-h,0);
             for(int u = radius;u > 0;u--){
                 for (int b =radius - u;b >= 0;b-- ){
                     this.level(.removeBlock(startposverh.offset(-u,0,b),false);
                     this.level(.removeBlock(startposverh.offset(-u,0,-b),false);

                     this.level(.removeBlock(startposverh.offset(-u,0,b),false);
                     this.level(.removeBlock(startposverh.offset(-u,0,-b),false);
                 }

             }
 */