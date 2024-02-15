package com.finderfeed.solarcraft.content.abilities.solar_strike;


import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticle;
import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.AlphaInOutOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDDefaultOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDScalingOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.instances.SmokeParticleOptions;
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
import com.finderfeed.solarcraft.packet_handler.packets.misc_packets.SolarStrikeEntityDoExplosion;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
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

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import net.minecraft.world.level.Level;
import java.util.List;
import java.util.UUID;


public class SolarStrikeEntity extends Entity {

    public static final int PARTIAL_EXPLOSION_RANGE = 3;
    public static final int RANDOM_RADIUS = 5;
    public static final int BASE_RADIUS = 10;
    public static final int BASE_MAX_DEPTH = 25;
    public static final int RANDOM_DEPTH = 5;
    public static int TIME_UNTIL_EXPLOSION = 20;
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


        if (!this.level().isClientSide){
//            if (this.entityData.get(LIFE) == 1){
//                this.level().playSound(null,this.getOnPos().offset(0,5,0), SCSounds.SOLAR_STRIKE_BUILD_SOUND.get(),SoundSource.AMBIENT,10,1F);
//            }
//            life++;
//            this.entityData.set(LIFE, life);
//
//            if (life >= 60) {
//                doSolarStrikeExplosion(this.getOnPos().offset(0,1,0));
//                //this.level(.explode(null,DamageSource.DRAGON_BREATH,null, this.position().x, this.position().y, this.position().z, 10, false, Explosion.Mode.BREAK);
//                List<Entity> list = this.level().getEntities(this,new AABB(-30,-30,-30,30,30,30)
//                        .move(this.getOnPos()),x -> x != getOwner());
//                for (Entity entity : list) {
//                    if (getOwner() instanceof LivingEntity entity1){
//                        entity.hurt(SCDamageSources.livingArmorPierce(entity1), SolarcraftConfig.SOLAR_STRIKE_DAMAGE.get().floatValue());
//                        continue;
//                    }
//                    entity.hurt(level().damageSources().magic(), SolarcraftConfig.SOLAR_STRIKE_DAMAGE.get().floatValue());
//                }
//                this.level().playSound(null,this.getOnPos().offset(0,5,0), SCSounds.SOLAR_STRIKE_SOUND.get(),SoundSource.AMBIENT,10,0.4F);
//
//                FDPacketUtil.sendToTrackingEntity(this,new SolarStrikeEntityDoExplosion(this.position()));
//
////                SCPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(
////                        this.position().x,this.position().y,this.position().z,100,level().dimension()
////                )),new SolarStrikeEntityDoExplosion(this.position()));
//
//                this.remove(RemovalReason.KILLED);
//            }
        }

        TIME_UNTIL_EXPLOSION = 20;

        if (!this.isRemoved()){
            if (!level.isClientSide) {
                if (tickCount >= TIME_UNTIL_EXPLOSION) {
                    this.explode();
                    this.remove(RemovalReason.DISCARDED);
                }
            }else{
                this.particlesBeforeExplosion(tickCount);
            }
        }
    }


    private void particlesBeforeExplosion(int tick){
        float p = (tick / (float) TIME_UNTIL_EXPLOSION);
        float radius = 20 * (1 - p);
        double t = (1-p) * Math.PI;
        float pcount = 30;
        double angle = Math.PI * 2 / pcount;
        float psize = 2;
        for (double i = 0; i <= Math.PI * 2;i += angle){
            double x = Math.sin(i + t) * radius;
            double z = Math.cos(i + t) * radius;
            Vec3 pos = Helpers.getBlockCenter(this.getOnPos()).add(x,0.5,z);
            int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING,(int)pos.x,(int)pos.z);
            int r = 230 + level.random.nextInt(25);
            int g = 230 + level.random.nextInt(25);
            int b = 10 + level.random.nextInt(10);
            BallParticleOptions options = new BallParticleOptions(psize,r,g,b,TIME_UNTIL_EXPLOSION,true,false);
            level.addParticle(options,pos.x,y + psize,pos.z,0,0,0);
        }
    }


    private void explode(){
        if (SCEventHandler.isExplosionBlockerAround(level(), Helpers.getBlockCenter(this.getOnPos())) || !Helpers.isSpellGriefingEnabled((ServerLevel) level)) return;
        int radius = level.random.nextInt(RANDOM_RADIUS) + BASE_RADIUS;
        int depth = level.random.nextInt(RANDOM_DEPTH) + BASE_MAX_DEPTH;
        for (int x = -radius;x <= radius;x++){
            for (int z = -radius;z <= radius;z++){
                for (int y = -depth; y <= depth;y++){
                    float mod = level.random.nextFloat() * PARTIAL_EXPLOSION_RANGE;
                    Vec3 v = new Vec3(x,0,z).normalize().multiply(mod,mod,mod);
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
            this.trySummonBlock(y,pos);
            level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
        }
    }

    private void trySummonBlock(int y,BlockPos pos){
        if (Math.abs(y) < 3 && level.random.nextFloat() < 0.2){
            Vec3 center = Helpers.getBlockCenter(pos);
            BlockState state = level.getBlockState(pos);
            MyFallingBlockEntity fallingBlock = new MyFallingBlockEntity(level,center.x,center.y,center.z,state);
            Vec3 between = center.multiply(1,0,1).subtract(this.position().multiply(1,0,1)).normalize();
            fallingBlock.setDeltaMovement(
                    between.x + random.nextFloat() * 2 - 1,
                    0.5 + random.nextFloat() * 2-1,
                    between.z + random.nextFloat() * 2 - 1
            );
            level.addFreshEntity(fallingBlock);
            float col = random.nextFloat() * 0.1f + 0.3f;
            FDDefaultOptions defaultOptions = new FDDefaultOptions(3f,30,col,col,col,1f,false,false);
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