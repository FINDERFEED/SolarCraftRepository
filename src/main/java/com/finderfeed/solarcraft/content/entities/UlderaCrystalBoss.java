package com.finderfeed.solarcraft.content.entities;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.AnimatedObject;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationManager;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.local_library.entities.BossAttackChain;
import com.finderfeed.solarcraft.registries.animations.SCAnimations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UlderaCrystalBoss extends Monster implements AnimatedObject {

    public static final String ATTACK_1_TICKER = "attack_1";
    private AnimationManager manager;

    private BossAttackChain bossChain = new BossAttackChain.Builder()
            .setTimeBetweenAttacks(20)
            .addAttack("test",this::testAttack, 40,1,0)
            .build();

    public UlderaCrystalBoss(EntityType<? extends Monster> p_21368_, Level level) {
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
        }
    }

    private void testAttack(){
        Vec3 center = this.getCenterPos();
        if (bossChain.getTicker() == 1){
            this.getAnimationManager().setAnimation(ATTACK_1_TICKER,
                    AnimationTicker.Builder.begin(SCAnimations.ULDERA_CRYSTAL_ATTACK_1.get())
                            .startFrom(0)
                            .build());
        }
        ((ServerLevel)level).sendParticles(
                BallParticleOptions.Builder.begin()
                        .setLifetime(60)
                        .setSize(0.5f)
                        .setRGB(90,0,186)
                        .setShouldShrink(true)
                        .build(),
                center.x,center.y,center.z,10,1,1,1,1
        );
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
        if (target.isDeadOrDying()) return false;
        Vec3 centerPos = this.getCenterPos();
        Vec3 targetCenter = target.position().add(0,target.getEyeHeight(target.getPose())/2f,0);
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
            return this.isEntityReachable(living);
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
        return Monster.createMonsterAttributes();
    }
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }
}
