package com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles;

import com.finderfeed.solarforge.misc_things.AbstractMortarProjectile;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.registries.entities.Entities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.core.particles.ParticleOptions;

import net.minecraft.world.level.Level;

public class MortarProjectile extends AbstractMortarProjectile {

    public MortarProjectile(EntityType<? extends MortarProjectile> p_i50173_1_, Level p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }
    public MortarProjectile(EntityType<? extends MortarProjectile> p_i50174_1_, double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, Level p_i50174_14_) {
        super(Entities.MORTAR_PROJECTILE.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public MortarProjectile(EntityType<? extends MortarProjectile> p_i50175_1_, LivingEntity p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level p_i50175_9_) {
        super(Entities.MORTAR_PROJECTILE.get(), p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
    }




    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticlesList.INVISIBLE_PARTICLE.get();
    }

    @Override
    public double getMDamage() {
        return 20;
    }

    @Override
    public double getExplosionRadius() {
        return 5;
    }
}
