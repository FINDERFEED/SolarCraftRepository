package com.finderfeed.solarcraft.content.entities;

import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ShadowZombie extends Monster implements PowerableMob {
    public ShadowZombie(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);

    }


    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            if (level.isDay()){

                this.kill();
            }
        }
    }


    @Override
    protected void tickDeath() {
        for (int i = 0; i <= 6;i++){
            Vec3 pos = this.position().add( level.random.nextFloat()*2-1,0.5 + level.random.nextFloat()*2-1,level.random.nextFloat()*2-1);
            level.addParticle(ParticleTypes.SMOKE,pos.x,pos.y,pos.z,0,0.04,0);
        }
        ++this.deathTime;
        if (this.deathTime == 10 && !this.level.isClientSide()) {
            this.level.broadcastEntityEvent(this, (byte)60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

//    @Override
//    public boolean isInvulnerableTo(DamageSource src) {
//        System.out.println(src);
//        return !src.isMagic() && src != DamageSource.OUT_OF_WORLD;
//    }

    @Override
    public boolean hurt(DamageSource src, float amount) {
        if (!src.isMagic() && src != DamageSource.OUT_OF_WORLD){
            return super.hurt(src,0);
        }
        return super.hurt(src, amount);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.23F)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                .add(Attributes.MAX_HEALTH,30);
    }



    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SolarcraftSounds.SHADOW_ZOMBIE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SolarcraftSounds.SHADOW_ZOMBIE_HURT.get();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }
    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean isPowered() {
        return true;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    public void playAmbientSound() {

    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_STEP;
    }


    protected void playStepSound(BlockPos p_34316_, BlockState p_34317_) {
//        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }
}
