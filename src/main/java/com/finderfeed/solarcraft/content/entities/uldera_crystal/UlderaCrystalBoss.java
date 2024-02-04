package com.finderfeed.solarcraft.content.entities.uldera_crystal;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.lightning_particle.LightningParticleOptions;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.SendShapeParticlesPacket;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.BurstAttackParticleShape;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.SphereParticleShape;
import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.finderfeed.solarcraft.content.entities.ElectricRainEntity;
import com.finderfeed.solarcraft.content.entities.not_alive.LegendaryItem;
import com.finderfeed.solarcraft.content.entities.projectiles.HomingStarProjectile;
import com.finderfeed.solarcraft.content.items.RuneEnergyPylonBlockItem;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.AnimatedObject;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationManager;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.EntityServerAnimationManager;
import com.finderfeed.solarcraft.local_library.entities.BossAttackChain;
import com.finderfeed.solarcraft.local_library.entities.bossbar.server.CustomServerBossEvent;
import com.finderfeed.solarcraft.misc_things.NoHealthLimitMob;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.CameraShakePacket;
import com.finderfeed.solarcraft.packet_handler.packets.DisablePlayerFlightPacket;
import com.finderfeed.solarcraft.registries.animations.SCAnimations;
import com.finderfeed.solarcraft.registries.attributes.AttributesRegistry;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.effects.SCEffects;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UlderaCrystalBoss extends NoHealthLimitMob implements AnimatedObject, UlderaCrystalBuddy {


    public static final BallParticleOptions PURPLE_PARTICLE_OPTIONS = BallParticleOptions.Builder.begin()
            .setLifetime(60)
            .setSize(0.15f)
            .setRGB(90,0,186)
            .setShouldShrink(true)
            .setPhysics(false)
            .build();
    public final CustomServerBossEvent BOSS_EVENT = new CustomServerBossEvent(this.getDisplayName(),"uldera_crystal");


    private static final BlockPos[] ELECTRIC_RAIN_OFFSETS = {
      new BlockPos(15,0,15),
      new BlockPos(15,0,-15),
      new BlockPos(-15,0,-15),
      new BlockPos(-15,0,15)
    };


    public static final String ATTACK_1_TICKER = "attack_1";
    public static final String TEMP1 = "temp1";
    public static final String TEMP2 = "temp2";

    private RunicEnergy.Type type = RunicEnergy.Type.ARDO;
    private AnimationManager manager;

    private int spawnTicks = 0;

    private int dontPush = 0;

    private int playerSearchCooldown = 0;

    private List<BlockPos> lightningPositions = new ArrayList<>();

    private static final EntityDataAccessor<Integer> MISC_TICKER = SynchedEntityData.defineId(UlderaCrystalBoss.class, EntityDataSerializers.INT);


    private BossAttackChain bossChain = new BossAttackChain.Builder()
            .setTimeBetweenAttacks(80)
            .addAttack("homingStars",this::homingStarsRelease, 50*5 - 1,1,0)
            .addAttack("lightnings",this::summonLightnings,200,20,1)
            .addAttack("effectCrystals",this::throwEffectCrystals,60,1,2)
            .addAttack("pullEntities",this::pullEntities,200,1,3)
            .addPostEffectToAttack("lightnings",()->{
                this.getAnimationManager().stopAnimation(ATTACK_1_TICKER);
                this.getAnimationManager().stopAnimation(TEMP1);
                this.passiveElectricRainSpawn();
            })
            .addPostEffectToAttack("homingStars",()->{
                this.getAnimationManager().stopAnimation(ATTACK_1_TICKER);
                this.passiveElectricRainSpawn();
            })
            .addPostEffectToAttack("effectCrystals",()->{
                this.getAnimationManager().stopAnimation(ATTACK_1_TICKER);
                this.getAnimationManager().stopAnimation(TEMP1);
            })
            .addPostEffectToAttack("pullEntities",()->{
                this.getAnimationManager().stopAnimation(TEMP2);
                this.getAnimationManager().stopAnimation(TEMP1);
                this.passiveElectricRainSpawn();
            })
            .build();

    public UlderaCrystalBoss(EntityType<? extends NoHealthLimitMob> p_21368_, Level level) {
        super(p_21368_, level);
        this.manager = AnimationManager.createEntityAnimationManager(this,level.isClientSide);
    }

    @Override
    public void tick() {
        super.tick();
        this.manager.tickAnimations();
        if (!level.isClientSide){

            playerSearchCooldown = Mth.clamp(playerSearchCooldown - 1,0,Integer.MAX_VALUE);

            BOSS_EVENT.setProgress(this.getHealth() / this.getMaxHealth());

            this.getAnimationManager().setAnimation("main",
                    new AnimationTicker.Builder(SCAnimations.ULDERA_CRYSTAL_IDLE.get())
                            .toNullTransitionTime(20)
                            .replaceable(false)
                            .build());

            if (spawnTicks <= 80){
                if (spawnTicks == 0) {
                    this.getAnimationManager().setAnimation("spawn",
                            new AnimationTicker.Builder(SCAnimations.ULDERA_CRYSTAL_SPAWN.get())
                                    .toNullTransitionTime(5)
                                    .replaceable(false)
                                    .build());
                }else if (spawnTicks == 15){
                    this.destroyBlocksAround();
                }
                spawnTicks++;
                return;
            }




            this.dontPush--;

            if (!this.isDeadOrDying() && this.getTarget() != null) {
                bossChain.tick();
            }

            if (level.getGameTime() % 20 == 0) {
                List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, SEARCH_TARGET_BOX.move(this.getCenterPos()), player -> {
                    return !player.isSpectator() && !player.isCreative();
                });
                if (players.isEmpty()){
                    if (SolarcraftConfig.SHOULD_ULDERA_CRYSTAL_REGENERATE.get()) {
                        this.heal(10);
                    }
                }else {
                    for (ServerPlayer player : players) {
                        this.disableFlight(player);
                        if (!player.hasEffect(SCEffects.ULDERA_CRYSTAL_PRESENCE.get()) && !this.isEntityReachable(player)) {
                            player.addEffect(new MobEffectInstance(SCEffects.ULDERA_CRYSTAL_PRESENCE.get(), 40, 0, true, false));
                        }
                    }
                }
            }
        }
    }

    private void destroyBlocksAround(){
        for (int x = -8;x <= 8;x++){
            for (int y = -8;y <= 8;y++){
                for (int z = -8;z <= 8;z++){
                    float xd = x + 0.5f;
                    float yd = y + 0.5f;
                    float zd = z + 0.5f;
                    if (xd*xd + yd*yd + zd*zd < 100) {

                        BlockPos pos = Helpers.vecToPos(this.getCenterPos()).offset(x, y, z);
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private void disableFlight(ServerPlayer player){
        if (player.getAbilities().flying){
            DisablePlayerFlightPacket.send(player,true);
             FDPacketUtil.sendToTrackingEntity(this,
                    new SendShapeParticlesPacket(
                            new BurstAttackParticleShape(this.getCenterPos(),
                                    player.position().add(0,player.getBbHeight()/2,0)
                                    ,0.5f,3,
                                    0.025),
                            BallParticleOptions.Builder.begin()
                                    .setLifetime(60)
                                    .setSize(0.15f)
                                    .setRGB(90,0,186)
                                    .setShouldShrink(true)
                                    .setPhysics(false)
                                    .build()
                    ));
             FDPacketUtil.sendToTrackingEntity(this,
                    new SendShapeParticlesPacket(
                            new BurstAttackParticleShape(this.getCenterPos(),
                                    player.position().add(0,player.getBbHeight()/2,0)
                                    ,0.5f,2,
                                    0.025),
                            LightningParticleOptions.Builder.start()
                                    .setHasPhysics(false)
                                    .setLifetime(60)
                                    .setQuadSize(1f)
                                    .setSeed(-1)
                                    .setRGB(90,0,186)
                                    .build()
                    ));
        }
    }

    private void homingStarsRelease(){
        if (this.getTarget() == null) return;
        if (this.getTarget().isDeadOrDying()) return;
        Vec3 center = this.getCenterPos();
        int t = bossChain.getTicker() % 50;
        if (t == 0){
            this.getAnimationManager().setAnimation(ATTACK_1_TICKER,
                    AnimationTicker.Builder.begin(SCAnimations.ULDERA_CRYSTAL_ATTACK_1.get())
                            .startFrom(0)
                            .replaceable(true)
                            .build());
        }

        if (t > 20 && t <= 40){
            HomingStarProjectile projectile = new HomingStarProjectile(level);
            projectile.setPos(this.getCenterPos());
            projectile.setDeltaMovement(Helpers.randomVector().normalize().multiply(0.5f,0.5f,0.5f));
            projectile.setTarget(this.getTarget().getUUID());
            projectile.setRotationSpeed(0.075f);
            projectile.setShooter(this.getUUID());
            projectile.setDamage(5f);
            level.addFreshEntity(projectile);
        }
        ((ServerLevel)level).sendParticles(
                BallParticleOptions.Builder.begin()
                        .setLifetime(60)
                        .setSize(0.5f)
                        .setRGB(90,0,186)
                        .setShouldShrink(true)
                        .build(),
                center.x,center.y,center.z,10,1,1,1,0.05f
        );
    }

    //lightning start
    private void summonLightnings(){
        this.getAnimationManager().setAnimation(ATTACK_1_TICKER,AnimationTicker.Builder.begin(SCAnimations.ULDERA_CRYSTAL_INFLATE_POSE.get())
                        .toNullTransitionTime(20)
                .build());
        this.getAnimationManager().setAnimation(TEMP1,AnimationTicker.Builder.begin(SCAnimations.ULDERA_CRYSTAL_SHAKE.get())
                        .toNullTransitionTime(5)
                .build());
        if (this.lightningPositions.isEmpty()){
            lightningPositions.addAll(this.generateRandomLightningPositions());
        }
        for (BlockPos pos : this.lightningPositions){
            UlderaLightningEntity lightning = new UlderaLightningEntity(SCEntityTypes.ULDERA_LIGHTNING.get(),level);
            lightning.setPos(pos.getX() + 0.5,pos.getY(),pos.getZ() + 0.5);
            lightning.setHeight(100);
            lightning.setLightningDelay(40);
            lightning.setOwner(this.getUUID());
            lightning.damage = 20;
            level.addFreshEntity(lightning);
        }
        lightningPositions.clear();
    }

    private List<BlockPos> generateRandomLightningPositions(){
        double radius = 30;
        List<BlockPos> sector1 = new ArrayList<>();
        List<BlockPos> sector2 = new ArrayList<>();
        List<BlockPos> sector3 = new ArrayList<>();
        List<BlockPos> sector4 = new ArrayList<>();
        this.tryAddPositions(0,0,radius,sector1);
        this.tryAddPositions((int)radius,0,radius,sector2);
        this.tryAddPositions(0,(int)radius,radius,sector3);
        this.tryAddPositions((int) radius,(int) radius,radius,sector4);
        int count = (int)radius / 5;
        sector1 = generateRandomPositions(sector1,count);
        sector2 = generateRandomPositions(sector2,count);
        sector3 = generateRandomPositions(sector3,count);
        sector4 = generateRandomPositions(sector4,count);
        return translateAndMergeLists(sector1,sector2,sector3,sector4);
    }

    @SafeVarargs
    private List<BlockPos> translateAndMergeLists(List<BlockPos>... positions){
        List<BlockPos> first = new ArrayList<>(positions[0].stream().map(this::translatePos).toList());
        for (int i = 1; i < positions.length;i++){
            first.addAll(positions[i].stream().map(this::translatePos).toList());
        }
        return first;
    }

    private BlockPos translatePos(BlockPos pos){
        pos = new BlockPos(this.getOnPos().getX() + pos.getX(),0,this.getOnPos().getZ() + pos.getZ());
        return pos.offset(0,level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,pos.getX(),pos.getZ()),0);
    }

    private void tryAddPositions(int sectorOffsetX, int sectorOffsetZ, double radius, List<BlockPos> positions){
        int r = (int)radius;
        for (int x = -r + sectorOffsetX; x < sectorOffsetX; x++){
            for (int z = -r + sectorOffsetZ; z < sectorOffsetZ; z++){
                float xs = x + 0.5f;
                float zs = z + 0.5f;
                float lensq = xs*xs + zs*zs;
                if (xs*xs + zs*zs <= radius*radius && lensq >= 2.5){
                    positions.add(new BlockPos(x,0,z));
                }
            }
        }
    }

    private List<BlockPos> generateRandomPositions(List<BlockPos> randomsrc,int count){
        List<BlockPos> positions = new ArrayList<>();
        for (int i = 0; i < count;i++){
            positions.add(randomsrc.remove(level.random.nextInt(randomsrc.size())));
        }
        return positions;
    }
    //lightning end


    //effect crystals
    private void throwEffectCrystals(){
        if (this.bossChain.getTicker() == this.bossChain.getCurrentAttack().getTime()) {
            int rad = 20;
            this.summonCrystalsIfSectorClear(rad);
        }else if (this.bossChain.getTicker() == 0){
            if (!isAnySectorClear(20)){
                this.bossChain.setCurrentWaitTime(-1);
                return;
            }
            this.getAnimationManager().setAnimation(ATTACK_1_TICKER,
                    AnimationTicker.Builder.begin(SCAnimations.ULDERA_CRYSTAL_ATTACK_1_POSE.get())
                            .startFrom(0)
                            .replaceable(true)
                            .toNullTransitionTime(15)
                            .build());
            this.getAnimationManager().setAnimation(TEMP1,
                    AnimationTicker.Builder.begin(SCAnimations.ULDERA_CRYSTAL_INFLATE_POSE.get())
                            .startFrom(0)
                            .replaceable(true)
                            .toNullTransitionTime(15)
                            .build());
        }else if (this.bossChain.getTicker() == this.bossChain.getCurrentAttack().getTime() - 5){
            this.getAnimationManager().stopAnimation(ATTACK_1_TICKER);
            this.getAnimationManager().stopAnimation(TEMP1);
        }
    }

    private boolean isAnySectorClear(int rad){
        return this.isSectorClearForEffectCrystal(-rad, -rad) ||
                this.isSectorClearForEffectCrystal(rad, -rad) ||
                this.isSectorClearForEffectCrystal(-rad, rad) ||
                this.isSectorClearForEffectCrystal(rad, rad);
    }

    private void summonCrystalsIfSectorClear(int rad){
        if (this.isSectorClearForEffectCrystal(-rad, -rad)) {
            this.summonCrystal(-rad, -rad);
        }
        if (this.isSectorClearForEffectCrystal(rad, -rad)) {
            this.summonCrystal(rad, -rad);
        }
        if (this.isSectorClearForEffectCrystal(-rad, rad)) {
            this.summonCrystal(-rad, rad);
        }
        if (this.isSectorClearForEffectCrystal(rad, rad)) {
            this.summonCrystal(rad, rad);
        }
    }

    private void summonCrystal(int xOffs,int zOffs){
        EffectCrystal crystal = new EffectCrystal(SCEntityTypes.EFFECT_CRYSTAL.get(),level);
        Vec3 pos = this.position().add(xOffs/2,this.getBbHeight()/2,zOffs/2);
        crystal.setPos(pos);
        level.addFreshEntity(crystal);
         FDPacketUtil.sendToTrackingEntity(this,
                new SendShapeParticlesPacket(
                        new BurstAttackParticleShape(this.getCenterPos(),pos,0.5f,3,
                                0.025),
                        BallParticleOptions.Builder.begin()
                                .setLifetime(60)
                                .setSize(0.15f)
                                .setRGB(90,0,186)
                                .setShouldShrink(true)
                                .setPhysics(false)
                                .build()
                ));
    }

    private boolean isSectorClearForEffectCrystal(int offsX,int offsZ){
        AABB box = new AABB(
                this.position(),
                this.position().add(offsX,this.getBbHeight(),offsZ)
        );
        return level.getEntitiesOfClass(EffectCrystal.class,box).isEmpty();
    }

    //effect crystals end


    //pull attack

    private void pullEntities(){
        int tick = bossChain.getTicker();
        if (tick == 0) {
            this.getAnimationManager().setAnimation(TEMP1, new AnimationTicker.Builder(SCAnimations.ULDERA_CRYSTAL_INFLATE_POSE.get())
                    .toNullTransitionTime(10)
                    .replaceable(false)
                    .build());
            this.getAnimationManager().setAnimation(TEMP2, new AnimationTicker.Builder(SCAnimations.ULDERA_CRYSTAL_SHAKE.get())
                    .toNullTransitionTime(10)
                    .replaceable(false)
                    .build());
            this.performEntityPull();
        }else if (tick == bossChain.getCurrentAttack().getTime() - 5){
            this.getAnimationManager().stopAnimation(TEMP1);
            this.getAnimationManager().stopAnimation(TEMP2);
            this.performEntityPull();
        }else if (tick == bossChain.getCurrentAttack().getTime()){
            this.sendExplosionParticles();
            this.dealExplosionDamage();
        }else{
            if (tick % 2 == 0) {
                Vec3 v = this.getCenterPos();
                 FDPacketUtil.sendToTrackingEntity(this, new SendShapeParticlesPacket(
                        new SphereParticleShape(PULL_DISTANCE, 0.6f, 3, true, false, 0.25f),
                        new BallParticleOptions.Builder().setRGB(180,60,255).setPhysics(false)
                                .setShouldShrink(true).setSize(0.4f).build(),
                        v.x, v.y, v.z, 0, 0, 0
                ));
            }
            this.performEntityPull();
        }
    }


    public static final float PULL_DISTANCE = 30;

    private void performEntityPull(){
        for (LivingEntity entity : this.getPullAffectedEntities()){
            Vec3 dist = this.getCenterPos().subtract(entity.position().add(0,entity.getBbHeight()/2,0));
            Vec3 vdist = this.position().multiply(1,0,1).subtract(entity.position().multiply(1,0,1));

            double vlen = vdist.length();
            float str = this.getPullStrength();
            Vec3 movement = entity.getDeltaMovement();
            double entitySpeed = movement.length();
            entitySpeed = Mth.clamp(entitySpeed,0.1,Double.MAX_VALUE);
            double mult = entitySpeed * str * 0.6;
            double yMod = 2 / vlen;
            double ySpeed = 0.1;
            if (yMod < 1 || str < 1){
                yMod = 0.05;
            }else{
                yMod = Mth.clamp(yMod,0,4);
            }
            double finalYSpeed = yMod * ySpeed * str;

            Vec3 pullSpeed = dist.normalize().multiply(mult,finalYSpeed,mult);
            Vec3 finalSpeed = movement.add(pullSpeed);
            finalSpeed = new Vec3(finalSpeed.x,Mth.clamp(finalSpeed.y,-0.1,0.1),finalSpeed.z);
            if (entity instanceof ServerPlayer player){
                Helpers.setServerPlayerSpeed(player,finalSpeed);
            }else{
                entity.setDeltaMovement(finalSpeed);
            }
        }
    }

    private float getPullStrength(){
        return Mth.clamp(this.bossChain.getTicker()/80f,0,1);
    }
    private void dealExplosionDamage(){
        float baseDamage = 10;
        for (LivingEntity entity : this.getPullAffectedEntities()){
            Vec3 dist = entity.position().add(0,entity.getBbHeight()/2,0).subtract(this.getCenterPos());
            float damage = calculateDistanceDamage(baseDamage,(float)dist.length());
//            if (entity instanceof Player player){
//                player.displayClientMessage(Component.literal("Damage: " + damage + "Distance: " + dist.length()),false);
//            }
            entity.invulnerableTime = 0;
            entity.hurt(SCDamageSources.livingAllResistanceIgnore(this),damage);
        }
    }

    private static final AABB PULL_ENTITY_BOX = new AABB(-PULL_DISTANCE,-PULL_DISTANCE,-PULL_DISTANCE,PULL_DISTANCE,PULL_DISTANCE,PULL_DISTANCE);
    private List<LivingEntity> getPullAffectedEntities(){
        return level.getEntitiesOfClass(LivingEntity.class,PULL_ENTITY_BOX.move(this.getCenterPos()),entity->{
           if (entity instanceof Player player){
               return !player.isSpectator() && !player.isCreative();
           }
           return !(entity instanceof UlderaCrystalBuddy);
        });
    }

    private float calculateDistanceDamage(float base,float distance){
        float distanceMod = distance / (PULL_DISTANCE / 2f);
        return 4 * (float)Math.pow(4,-distanceMod) * base;
    }

    private void sendExplosionParticles(){
        Vec3 v = this.getCenterPos();
         FDPacketUtil.sendToTrackingEntity(this,new SendShapeParticlesPacket(
                new SphereParticleShape(0.5,0.6f,3,true,true,1f),
                new BallParticleOptions.Builder().setRGB(90,0,186).setPhysics(false)
                        .setShouldShrink(true).setSize(0.25f).build(),
                v.x,v.y,v.z,0,0,0
        ));
         FDPacketUtil.sendToTrackingEntity(this,new SendShapeParticlesPacket(
                new SphereParticleShape(0,0.6f,2,true,true,1f),
                new LightningParticleOptions(2f,90,0,186,-1,60,false),
                v.x,v.y,v.z,0,0,0
        ));
         FDPacketUtil.sendToTrackingEntity(this,new SendShapeParticlesPacket(
                new SphereParticleShape(1.5,0.8f,3,true,true,1f),
                new BallParticleOptions.Builder().setRGB(90,0,186).setPhysics(false)
                        .setShouldShrink(true).setSize(0.25f).build(),
                v.x,v.y,v.z,0,0,0
        ));
         FDPacketUtil.sendToTrackingEntity(this,new SendShapeParticlesPacket(
                new SphereParticleShape(1,0.8f,2,true,true,1f),
                new LightningParticleOptions(2f,90,0,186,-1,60,false),
                v.x,v.y,v.z,0,0,0
        ));
    }

    //end pull attack


    private void passiveElectricRainSpawn(){
        BlockPos pos = ELECTRIC_RAIN_OFFSETS[level.random.nextInt(ELECTRIC_RAIN_OFFSETS.length)].offset(this.getOnPos());
        ElectricRainEntity rain = new ElectricRainEntity(SCEntityTypes.ELECTRIC_RAIN.get(),level);
        int height = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,pos.getX(),pos.getZ());
        Vec3 v = new Vec3(pos.getX() + 0.5,height,pos.getZ() + 0.5);
         FDPacketUtil.sendToTrackingEntity(this,
                new SendShapeParticlesPacket(
                        new BurstAttackParticleShape(this.getCenterPos(),
                                v
                                ,0.5f,3,
                                0.025),
                        BallParticleOptions.Builder.begin()
                                .setLifetime(60)
                                .setSize(0.35f)
                                .setRGB(90,0,186)
                                .setShouldShrink(true)
                                .setPhysics(false)
                                .build()
                ));
         FDPacketUtil.sendToTrackingEntity(this,
                new SendShapeParticlesPacket(
                        new BurstAttackParticleShape(this.getCenterPos(),
                                v
                                ,0.5f,2,
                                0.025),
                        LightningParticleOptions.Builder.start()
                                .setHasPhysics(false)
                                .setLifetime(60)
                                .setQuadSize(1f)
                                .setSeed(-1)
                                .setRGB(90,0,186)
                                .build()
                ));
        rain.setPos(v);
        rain.setLifetime(300);
        rain.setOwner(this.getUUID());
        rain.setFrequency(2);
        rain.setRadius(21);
        rain.setDamage(6);
        level.addFreshEntity(rain);
    }



    public int getMiscTicker(){
        return this.entityData.get(MISC_TICKER);
    }

    public void setMiscTicker(int tick){
        this.entityData.set(MISC_TICKER,tick);
    }

    protected void doPush(Entity entity) {
        if (this.bossChain.getCurrentAttack() != null && this.bossChain.getCurrentAttack().getSerializationId().equals("pullEntities")){
            dontPush = 20;
            return;
        }else if (this.dontPush > 0){
            return;
        }
        entity.setDeltaMovement(entity.position().add(0,entity.getBbHeight()/2,0).subtract(this.position().add(0,this.getBbHeight()/2,0)).normalize());
    }


    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        ((EntityServerAnimationManager)this.getAnimationManager().getAsServerManager()).sendAllAnimations(player);
        BOSS_EVENT.addPlayer(player);
    }


    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        BOSS_EVENT.removePlayer(player);
    }

    @Override
    public boolean hurt(DamageSource src, float amount) {
        if (src.is(DamageTypeTags.IS_PROJECTILE) || src.is(DamageTypeTags.IS_EXPLOSION) ||
                (src.getEntity() == null && src != damageSources().genericKill() && src != damageSources().fellOutOfWorld())) return false;

        if (src.getEntity() instanceof Player player){
            if (player.hasEffect(SCEffects.ULDERA_CRYSTAL_PRESENCE.get())) {
                amount = 0;
            }else if (!this.isEntityReachable(player)){
                player.addEffect(new MobEffectInstance(SCEffects.ULDERA_CRYSTAL_PRESENCE.get(),200,0));
                amount = 0;
            }
        }else if (src.getEntity() instanceof LivingEntity living){
            amount = amount / 4f;
        }
        return super.hurt(src, amount);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance inst) {
        return inst.getEffect() == SCEffects.IMMORTALITY_EFFECT.get();
    }

    @Override
    public boolean canCollideWith(Entity p_20303_) {
        return false;
    }

    @Override
    public boolean ignoreExplosion(Explosion p_312868_) {
        return true;
    }

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {

    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }


    @Override
    protected void tickDeath() {
        if (!level.isClientSide && this.deathTime == 0){
            this.getAnimationManager().setAnimation(ATTACK_1_TICKER,AnimationTicker.Builder.begin(SCAnimations.ULDERA_CRYSTAL_SHAKE.get())
                            .toNullTransitionTime(0)
                            .replaceable(false)
                    .build());
            this.getAnimationManager().setAnimation(TEMP1,AnimationTicker.Builder.begin(SCAnimations.ULDERA_CRYSTAL_DIE.get())
                            .toNullTransitionTime(0)
                            .replaceable(false)
                    .build());
            this.getAnimationManager().stopAnimation(TEMP2);
            FDPacketUtil.sendToPlayersCloseToSpot(level,this.getCenterPos(),30,new CameraShakePacket(5,
                    SCAnimations.ULDERA_CRYSTAL_DIE.get().tickLength() - 20,
                    20,0.5f));
        }
        ++this.deathTime;
        if (!level.isClientSide && deathTime % 2 == 0){
            Vec3 c = this.getCenterPos();
            ((ServerLevel)level).sendParticles(new BallParticleOptions.Builder().setRGB(90,0,186).setPhysics(false)
                    .setShouldShrink(true).setSize(0.5f).build(),c.x,c.y,c.z,10,1,1,1,0.025);
            ((ServerLevel)level).sendParticles(new LightningParticleOptions(2f,90,0,186,-1,60,false)
                    ,c.x,c.y,c.z,1,1,1,1,0.025);
        }
        if (this.deathTime >= SCAnimations.ULDERA_CRYSTAL_DIE.get().tickLength() && !this.level().isClientSide() && !this.isRemoved()) {
            Vec3 c = this.getCenterPos();
             FDPacketUtil.sendToTrackingEntity(this,new SendShapeParticlesPacket(
                    new SphereParticleShape(0.5,0.6f,5,true,true,1f),
                    new BallParticleOptions.Builder().setRGB(90,0,186).setPhysics(false)
                            .setShouldShrink(true).setSize(0.5f).build(),
                    c.x,c.y,c.z,0,0,0
            ));
             FDPacketUtil.sendToTrackingEntity(this,new SendShapeParticlesPacket(
                    new SphereParticleShape(0.5,0.6f,3,true,true,1f),
                    new LightningParticleOptions(2f,90,0,186,-1,60,false),
                    c.x,c.y,c.z,0,0,0
            ));


            LegendaryItem item = new LegendaryItem(SCEntityTypes.LEGENDARY_ITEM.get(), level);
            item.setPos(this.getCenterPos());
            item.setItem(RuneEnergyPylonBlockItem.createWithType(this.type));
            level.addFreshEntity(item);

            this.level().broadcastEntityEvent(this, (byte)60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public void die(DamageSource p_21014_) {
        super.die(p_21014_);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        LivingEntity target = super.getTarget();
        if (!checkTarget(target)){
            LivingEntity potentialTarget = this.searchTarget();
            this.setTarget(potentialTarget);
            return potentialTarget;
        } else if (!(target instanceof Player) && playerSearchCooldown <= 0){
            playerSearchCooldown = 40;
            List<Player> players = this.getTargetableEntities(Player.class);
            if (!players.isEmpty()){
                Player player;
                this.setTarget(player = players.get(level.random.nextInt(players.size())));
                return player;
            }
        }
        return target;
    }

    private boolean checkTarget(LivingEntity target){
        if (target == null) return false;
        if (target instanceof UlderaCrystalBuddy) return false;
        if (target.isDeadOrDying() || target.isRemoved()) return false;
        if (target instanceof Player player && (player.isCreative() || player.isSpectator())) return false;
        Vec3 centerPos = this.getCenterPos();
        Vec3 targetCenter = target.position().add(0,target.getEyeHeight(target.getPose())/2f,0);
        double vlen = centerPos.multiply(1,0,1).subtract(targetCenter.multiply(1,0,1)).length();
        if (vlen <= 0.1){
            return true;
        }
        if (vlen > 30){
            return false;
        }
        if (!this.isEntityReachable(target)){
            return false;
        }
        return true;
    }

    private boolean isEntityReachable(LivingEntity target){
        Vec3 centerPos = this.getCenterPos();
        Vec3 targetCenter = target.position().add(0,target.getEyeHeight(target.getPose())/2f,0);
        HitResult result = Helpers.getEntityHitResult(this,level,centerPos,targetCenter,(entity)->true);
        if (result == null){
            return false;
        }
        return true;
    }


    private static final AABB SEARCH_TARGET_BOX = new AABB(-30,-30,-30,30,30,30);
    private LivingEntity searchTarget(){
        List<Player> players = new ArrayList<>();
        List<LivingEntity> entities = this.getTargetableEntities(LivingEntity.class);
        if (entities.isEmpty()){
            return null;
        }else{
            if (!players.isEmpty()){
                return players.get(level.random.nextInt(players.size()));
            }
            return entities.get(level.random.nextInt(entities.size()));
        }
    }

    private <T extends LivingEntity> List<T> getTargetableEntities(Class<T> search){
        return level.getEntitiesOfClass(search,SEARCH_TARGET_BOX.move(this.getCenterPos()),living->{
            return this.checkTarget(living);
        });
    }


    public void setREType(RunicEnergy.Type type) {
        this.type = type;
    }

    public RunicEnergy.Type getRETypeType() {
        return type;
    }

    public Vec3 getCenterPos(){
        return this.position().add(0,this.getBbHeight()/2f,0);
    }
    @Override
    public boolean save(CompoundTag tag) {
        this.bossChain.save(tag);
        tag.putInt("spawnTicks",this.spawnTicks);
        tag.putString("energyType",type.id);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.bossChain.load(tag);
        this.type = RunicEnergy.Type.byId(tag.getString("energyType"));
        if (this.type == null){
            type = RunicEnergy.Type.ARDO;
        }
        this.spawnTicks = tag.getInt("spawnTicks");
        super.load(tag);
    }

    @Override
    public AnimationManager getAnimationManager() {
        return manager;
    }

    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return true;
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MISC_TICKER,0);
    }

    public static AttributeSupplier.Builder createCrystalAttributes() {
        return NoHealthLimitMob.createEntityAttributes()
                .add(Attributes.ARMOR,15)
                .add(Attributes.ARMOR_TOUGHNESS,7)
                .add(AttributesRegistry.MAXIMUM_HEALTH_NO_LIMIT.get(),1000);
    }
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SCSounds.CRYSTAL_HIT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SCSounds.CRYSTAL_HIT.get();
    }

}
