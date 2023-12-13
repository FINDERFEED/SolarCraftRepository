package com.finderfeed.solarcraft.content.entities.projectiles;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.NetworkHooks;

public abstract class NormalProjectile extends AbstractHurtingProjectile {
    protected NormalProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public NormalProjectile(EntityType<? extends AbstractHurtingProjectile> x, double y, double z, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(x, y, z, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
    }

    public NormalProjectile(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity owner, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(p_36826_, owner, p_36828_, p_36829_, p_36830_, p_36831_);
    }


    @Override
    protected float getInertia() {
        return 1f;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource src, float p_36840_) {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return SCParticleTypes.INVISIBLE_PARTICLE.get();
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
