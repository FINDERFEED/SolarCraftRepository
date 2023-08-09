package com.finderfeed.solarcraft.content.entities.uldera_crystal;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.lightning_particle.LightningParticleOptions;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.SendShapeParticlesPacket;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.BurstAttackParticleShape;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.SphereParticleShape;
import com.finderfeed.solarcraft.content.entities.projectiles.HomingStarProjectile;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.AnimatedObject;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationManager;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.EntityServerAnimationManager;
import com.finderfeed.solarcraft.local_library.entities.BossAttackChain;
import com.finderfeed.solarcraft.misc_things.NoHealthLimitMob;
import com.finderfeed.solarcraft.packet_handler.PacketHelper;
import com.finderfeed.solarcraft.registries.animations.SCAnimations;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UlderaCrystalBoss extends NoHealthLimitMob implements AnimatedObject, UlderaCrystalBuddy {

    public static final String ATTACK_1_TICKER = "attack_1";
    public static final String TEMP1 = "temp1";
    private AnimationManager manager;

    private List<BlockPos> lightningPositions = new ArrayList<>();

    private BossAttackChain bossChain = new BossAttackChain.Builder()
            .setTimeBetweenAttacks(40)
            .addAttack("homingStars",this::homingStarsRelease, 50*5 - 1,1,0)
            .addAttack("lightnings",this::summonLightnings,200,20,1)
            .addAttack("effectCrystals",this::throwEffectCrystals,60,1,2)
            .addPostEffectToAttack("lightnings",()->{
                this.getAnimationManager().stopAnimation(ATTACK_1_TICKER);
                this.getAnimationManager().stopAnimation(TEMP1);
            })
            .addPostEffectToAttack("homingStars",()->{
                this.getAnimationManager().stopAnimation(ATTACK_1_TICKER);
            })
            .addPostEffectToAttack("effectCrystals",()->{
                this.getAnimationManager().stopAnimation(ATTACK_1_TICKER);
                this.getAnimationManager().stopAnimation(TEMP1);
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
            this.getAnimationManager().setAnimation("main",
                    new AnimationTicker.Builder(SCAnimations.ULDERA_CRYSTAL_IDLE.get())
                            .toNullTransitionTime(20)
                            .replaceable(false)
                    .build());
            if (this.getTarget() != null) {
                bossChain.tick();
            }

            if (level.getGameTime() % 200 == 0){
                Vec3 pos = this.getCenterPos().add(0,20,0);
                PacketHelper.sendToPlayersTrackingEntity(
                        this,
                        new SendShapeParticlesPacket(
                                new SphereParticleShape(1,0.05,2),
                                new LightningParticleOptions(0.2f,
                                        90,0,186,
                                        2,level.random.nextInt(),
                                        60
                                        ),
                                pos.x,pos.y,pos.z,
                                0,0,0

                        )
                );
                System.out.println("particles spawned");
            }
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
            projectile.setDamage(4f);
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
            lightning.damage = 5;
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
        sector1 = generateRandomPositions(sector1,(int)radius/6);
        sector2 = generateRandomPositions(sector2,(int)radius/6);
        sector3 = generateRandomPositions(sector3,(int)radius/6);
        sector4 = generateRandomPositions(sector4,(int)radius/6);
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
        }else if (this.bossChain.getTicker() == 0){
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

    private void summonCrystal(int xOffs,int zOffs){
        EffectCrystal crystal = new EffectCrystal(SCEntityTypes.EFFECT_CRYSTAL.get(),level);
        Vec3 pos = this.position().add(xOffs/2,this.getBbHeight()/2,zOffs/2);
        crystal.setPos(pos);
        level.addFreshEntity(crystal);
        PacketHelper.sendToPlayersTrackingEntity(this,
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




    protected void doPush(Entity entity) {
        entity.setDeltaMovement(entity.position().add(0,entity.getBbHeight()/2,0).subtract(this.position().add(0,this.getBbHeight()/2,0)).normalize());
    }


    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        ((EntityServerAnimationManager)this.getAnimationManager().getAsServerManager()).sendAllAnimations(player);
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
    public boolean canBeAffected(MobEffectInstance p_21197_) {
        return false;
    }

    @Override
    public boolean canCollideWith(Entity p_20303_) {
        return false;
    }

    @Override
    public boolean ignoreExplosion() {
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

    @Nullable
    @Override
    public LivingEntity getTarget() {
        LivingEntity target = super.getTarget();
        if (!checkTarget(target)){
            LivingEntity potentialTarget = this.searchTarget();
            this.setTarget(potentialTarget);
            return potentialTarget;
        }
        return target;
    }

    private boolean checkTarget(LivingEntity target){
        if (target == null) return false;
        if (target instanceof UlderaCrystalBuddy) return false;
        if (target.isDeadOrDying()) return false;
        if (target instanceof Player player && (player.isCreative() || player.isSpectator())) return false;
        Vec3 centerPos = this.getCenterPos();
        Vec3 targetCenter = target.position().add(0,target.getEyeHeight(target.getPose())/2f,0);
        if (centerPos.multiply(1,0,1).subtract(targetCenter.multiply(1,0,1)).length() > 30){
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
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,SEARCH_TARGET_BOX.move(this.getCenterPos()),living->{
            if (living instanceof Player player){
                return !player.isSpectator() && !player.isCreative();
            }
            return !(living instanceof UlderaCrystalBuddy) && this.isEntityReachable(living);
        });
        if (entities.isEmpty()){
            return null;
        }else{
            return entities.get(level.random.nextInt(entities.size()));
        }
    }


    public Vec3 getCenterPos(){
        return this.position().add(0,this.getBbHeight()/2f,0);
    }
    @Override
    public boolean save(CompoundTag tag) {
        this.bossChain.save(tag);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.bossChain.load(tag);
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



    public static AttributeSupplier.Builder createCrystalAttributes() {
        return NoHealthLimitMob.createEntityAttributes();
    }
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }
}
