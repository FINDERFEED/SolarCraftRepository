package com.finderfeed.solarcraft.content.entities.projectiles;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.entities.CrystalBossEntity;
import com.finderfeed.solarcraft.misc_things.CrystalBossBuddy;
import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.packet_handler.packets.misc_packets.ExplosionParticlesPacket;
import com.finderfeed.solarcraft.registries.damage_sources.SolarcraftDamageSources;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class MagicMissile extends AbstractHurtingProjectile implements CrystalBossBuddy {


    private double speedDecrement = 0.04;
    private Float damage;

    public MagicMissile(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }


    public MagicMissile(Level p_36834_, double x, double y, double z) {
        super(SolarcraftEntityTypes.FALLING_MAGIC_MISSILE.get(), p_36834_);
        this.setDeltaMovement(x,y,z);
    }

    public MagicMissile(Level p_36834_, Vec3 speed) {
        super(SolarcraftEntityTypes.FALLING_MAGIC_MISSILE.get(), p_36834_);
        this.setDeltaMovement(speed);
    }


    @Override
    public void tick() {

        super.tick();

        if (!level.isClientSide){

            this.setDeltaMovement(this.getDeltaMovement().add(0,-this.getSpeedDecrementPerTick(),0));
        }

        if (this.level.isClientSide){
            this.level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),this.position().x,this.position().y,this.position().z,0,0,0);
        }
    }


    @Override
    protected void onHitEntity(EntityHitResult hit) {
        super.onHitEntity(hit);
        if (!(hit.getEntity() instanceof CrystalBossBuddy) && !(hit.getEntity() instanceof MagicMissile)){
            if (Helpers.isVulnerable(hit.getEntity())){
                if (damage == null) {
                    hit.getEntity().hurt(SolarcraftDamageSources.RUNIC_MAGIC,CrystalBossEntity.AIR_STRIKE_DAMAGE);
                    hit.getEntity().invulnerableTime = 0;
                    this.kill();
                }else{
                    if (!level.isClientSide) {
                        level.playSound(null,this.getX(),this.getY(),this.getZ(), SolarcraftSounds.SOLAR_EXPLOSION.get(), SoundSource.AMBIENT,level.random.nextFloat()*0.5f+0.5f,1f);
                        ExplosionParticlesPacket.send(level, position().add(getDeltaMovement()));
                        if (Helpers.isVulnerable(hit.getEntity())){
                            hit.getEntity().hurt(SolarcraftDamageSources.RUNIC_MAGIC,damage != null ? damage : 3);
                            hit.getEntity().invulnerableTime = 0;
                        }
                    }
                    this.kill();
                }

            }

        }

    }

    @Override
    protected void onHitBlock(BlockHitResult hit) {
        super.onHitBlock(hit);
        explode();
    }

    private void explode(){
        if (!this.level.isClientSide){
            level.playSound(null,this.getX(),this.getY(),this.getZ(), SolarcraftSounds.SOLAR_EXPLOSION.get(), SoundSource.AMBIENT,level.random.nextFloat()*0.5f+0.5f,1f);
            this.level.getEntitiesOfClass(LivingEntity.class,new AABB(this.position().add(-1.5,-1.5,-1.5),this.position().add(1.5,1.5,1.5)),(living)->{
                return !(living instanceof CrystalBossBuddy);
            }).forEach((entity)->{
                if (Helpers.isVulnerable(entity)){
                    entity.hurt(SolarcraftDamageSources.RUNIC_MAGIC,damage != null ? damage : 3);
                    entity.invulnerableTime = 0;
                }
            });
            ExplosionParticlesPacket.send(level, position().add(getDeltaMovement()));
            this.kill();
        }
    }

    @Override
    public boolean save(CompoundTag tag) {

        tag.putDouble("speedD",speedDecrement);
        tag.putFloat("dam",damage);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {

        this.speedDecrement = tag.getDouble("speedD");
        this.damage = tag.getFloat("dam");
        super.load(tag);
    }

    public double getSpeedDecrementPerTick(){
        return speedDecrement;
    }

    public void setSpeedDecrement(double speedDecrement) {
        this.speedDecrement = speedDecrement;
    }

    public void setDamage(Float damage) {
        this.damage = damage;
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
        return SolarcraftParticleTypes.INVISIBLE_PARTICLE.get();
    }
}
