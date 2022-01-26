package com.finderfeed.solarforge.magic.projectiles;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.entities.CrystalBossEntity;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.client.particles.ParticleTypesRegistry;
import com.finderfeed.solarforge.registries.entities.Entities;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class FallingStarCrystalBoss extends AbstractHurtingProjectile implements CrystalBossBuddy {

    private boolean removeIT = false;

    public FallingStarCrystalBoss(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }


    public FallingStarCrystalBoss( Level p_36834_,double x,double y,double z) {
        super(Entities.FALLING_STAR_CRYSTAL_BOSS.get(), p_36834_);
        this.setDeltaMovement(x,y,z);
    }

    public FallingStarCrystalBoss(Level p_36834_, Vec3 speed) {
        super(Entities.FALLING_STAR_CRYSTAL_BOSS.get(), p_36834_);
        this.setDeltaMovement(speed);
    }


    @Override
    public void tick() {
        if (!level.isClientSide){
            if (this.removeIT){
                this.kill();
            }
        }
        super.tick();

        if (!level.isClientSide){

            this.setDeltaMovement(this.getDeltaMovement().add(0,-this.getSpeedDecrementPerTick(),0));
        }

        if (this.level.isClientSide){
            this.level.addParticle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x,this.position().y,this.position().z,0,0,0);
        }
    }


    @Override
    protected void onHitEntity(EntityHitResult hit) {
        super.onHitEntity(hit);
        if (!(hit.getEntity() instanceof CrystalBossBuddy)){
            if (Helpers.isVulnerable(hit.getEntity())){
                hit.getEntity().hurt(DamageSource.MAGIC, CrystalBossEntity.AIR_STRIKE_DAMAGE);
                hit.getEntity().invulnerableTime = 0;
                this.kill();
            }

        }

    }

    @Override
    protected void onHitBlock(BlockHitResult hit) {
        super.onHitBlock(hit);
        explode();
    }

    private void explode(){
        if (this.level.isClientSide){


            Helpers.createSmallSolarStrikeParticleExplosionWithLines(level,this.position().add(this.getDeltaMovement().multiply(0.7,0.7,0.7)),2,0.1f,0.5f);
        }else{
            level.playSound(null,this.getX(),this.getY(),this.getZ(), Sounds.SOLAR_EXPLOSION.get(), SoundSource.AMBIENT,level.random.nextFloat()*0.5f+0.5f,1f);
            this.level.getEntitiesOfClass(LivingEntity.class,new AABB(this.position().add(-1.5,-1.5,-1.5),this.position().add(1.5,1.5,1.5)),(living)->{
                return !(living instanceof CrystalBossBuddy);
            }).forEach((entity)->{
                if (Helpers.isVulnerable(entity)){
                    entity.hurt(DamageSource.MAGIC,3);
                    entity.invulnerableTime = 0;
                }
            });
            this.removeIT = true;
        }
    }

    @Override
    public boolean save(CompoundTag p_20224_) {
        p_20224_.putBoolean("removeit",this.removeIT);
        return super.save(p_20224_);
    }

    @Override
    public void load(CompoundTag p_20259_) {
        this.removeIT= p_20259_.getBoolean("removeit");
        super.load(p_20259_);
    }

    public double getSpeedDecrementPerTick(){
        return 0.04;
    }

    @Override
    protected float getInertia() {
        return 1;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }


    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypesRegistry.INVISIBLE_PARTICLE.get();
    }
}
