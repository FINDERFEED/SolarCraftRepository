package com.finderfeed.solarforge.content.entities.projectiles;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.registries.entities.SolarcraftEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class SolarFireballProjectile extends AbstractHurtingProjectile {

    private float damage = 0;

    public SolarFireballProjectile(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public SolarFireballProjectile( double p_36818_, double p_36819_, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(SolarcraftEntityTypes.SOLAR_FIREBALL.get(), p_36818_, p_36819_, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
    }

    public SolarFireballProjectile( LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(SolarcraftEntityTypes.SOLAR_FIREBALL.get(), p_36827_, p_36828_, p_36829_, p_36830_, p_36831_);
    }


    @Override
    public void tick() {
        if (level.isClientSide ){
            for (int i = 0;i < 2;i++) {
                Vec3 vec = Helpers.randomVector().normalize().multiply(0.2, 0.2, 0.2);
                ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        position().x + vec.x, position().y + vec.y, position().z + vec.z, 0, 0, 0, () -> 200 + level.random.nextInt(55),
                        () -> 200 + level.random.nextInt(55), () -> 0, 0.4f);
            }
        }
        super.tick();
    }

    @Override
    protected void onHitBlock(BlockHitResult res) {
        super.onHitBlock(res);
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult res) {
        super.onHitEntity(res);
        Entity e = res.getEntity();
        if (!level.isClientSide && e instanceof LivingEntity living && living != getOwner()){
            living.hurt(DamageSource.MAGIC,damage);
            living.setSecondsOnFire(6);
            this.discard();
        }


    }

    @Override
    protected float getInertia() {
        return 1;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putFloat("damage",getDamage());
        return super.save(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        this.setDamage(tag.getFloat("damage"));
        super.load(tag);
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return SolarcraftParticleTypes.INVISIBLE_PARTICLE.get();
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
