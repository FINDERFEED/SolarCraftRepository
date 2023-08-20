package com.finderfeed.solarcraft.content.entities.projectiles;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.content.entities.uldera_crystal.UlderaCrystalBuddy;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;


public class HomingStarProjectile extends NormalProjectile {

    private UUID shooter;
    private UUID target;
    private Entity targetEntity;
    private float damage;
    private float rotationSpeed = 0.1f;

    private float speed = 1f;

    public HomingStarProjectile(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
        this.setDeltaMovement(0,-0.5,0);
    }

    public HomingStarProjectile(Level level){
        this(SCEntityTypes.Projectiles.HOMING_STAR.get(),level);
    }


    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            if (this.tickCount >= 3000){
                this.kill();
            }
            this.serverTick();
        }else{
            this.clientTick();
        }
    }

    private void serverTick(){
        ServerLevel slevel = (ServerLevel) level;
        if (target != null){
            if (targetEntity == null){
                targetEntity = slevel.getEntity(target);
            }
            if (targetEntity instanceof LivingEntity living){
                if (living.isDeadOrDying()){
                    this.explode();
                }
                Vec3 newMoveVector = this.calculateNewMovementVector(living.position().add(0,living.getBbHeight()/2f,0));
                this.setDeltaMovement(newMoveVector);
            }
        }
    }

    private Vec3 calculateNewMovementVector(Vec3 targetPosition){
        Vec3 pos = this.position().add(0,this.getBbHeight()/2,0);
        Vec3 between = targetPosition.subtract(pos);
        if (between.length() <= 3){
            return this.getDeltaMovement();
        }
        Vec3 add = between.normalize().multiply(rotationSpeed,rotationSpeed,rotationSpeed);
        Vec3 newSpeed = this.getDeltaMovement().add(add).normalize().multiply(speed,speed,speed);
        return newSpeed;
    }

    private void clientTick(){
        float h = getBbHeight();
        Vec3 ppos = this.position().add(0,h/2,0);
        level.addParticle(BallParticleOptions.Builder.begin()
                .setShouldShrink(true)
                .setRGB(120,40,186)
                .setLifetime(60)
                .setSize(0.25f)
                .build(),ppos.x,ppos.y,ppos.z,0,0,0);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        if (level instanceof ServerLevel serverLevel){
            this.explode();
        }
        super.onHitBlock(p_37258_);
    }

    private void explode(){
        if (level instanceof ServerLevel serverLevel){
            float h = getBbHeight();
            Vec3 ppos = this.position().add(0,h/2,0);
            serverLevel.sendParticles(BallParticleOptions.Builder.begin()
                    .setShouldShrink(true)
                    .setRGB(90,0,186)
                    .setLifetime(60)
                    .setSize(0.25f)
                    .build(),ppos.x,ppos.y,ppos.z,20,0.25f,0.25f,0.25f,0.05f);
            this.kill();
        }
    }

    @Override
    public void setDeltaMovement(Vec3 movement) {
        super.setDeltaMovement(movement);
        this.speed = (float)movement.length();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (level instanceof ServerLevel serverLevel && result.getEntity() instanceof LivingEntity living){
            Entity shootEntity = shooter != null ? serverLevel.getEntity(shooter) : null;
            if (shootEntity != living && !(living instanceof UlderaCrystalBuddy)){
                if (shootEntity instanceof LivingEntity  shooterEntity) {
                    living.hurt(SCDamageSources.livingArmorPierceProjectile(shooterEntity),damage);
                }else{
                    living.hurt(SCDamageSources.SHADOW,damage);
                }
                this.explode();
            }

        }
        super.onHitEntity(result);
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putFloat("damage",damage);
        tag.putFloat("rotspeed",rotationSpeed);
        CompoundNBTHelper.writeVec3("deltaMovement",this.getDeltaMovement(),tag);
        if (shooter != null){
            tag.putUUID("shooter",this.shooter);
        }
        if (target != null){
            tag.putUUID("target",target);
        }
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.damage = tag.getFloat("damage");
        this.rotationSpeed = tag.getFloat("rotspeed");
        this.setDeltaMovement(CompoundNBTHelper.getVec3("deltaMovement",tag));
        this.setShooter(CompoundNBTHelper.getUUID(tag,"shooter"));
        this.setTarget(CompoundNBTHelper.getUUID(tag,"target"));
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setTarget(UUID target) {
        this.target = target;
    }
    public void setShooter(UUID shooter) {
        this.shooter = shooter;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
}
