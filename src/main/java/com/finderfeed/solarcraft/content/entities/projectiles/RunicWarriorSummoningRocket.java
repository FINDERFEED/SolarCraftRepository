package com.finderfeed.solarcraft.content.entities.projectiles;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.content.entities.RunicWarrior;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class RunicWarriorSummoningRocket extends AbstractHurtingProjectile {


    public RunicWarriorSummoningRocket(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public RunicWarriorSummoningRocket(EntityType<? extends AbstractHurtingProjectile> p_36817_, double p_36818_, double p_36819_, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(p_36817_, p_36818_, p_36819_, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
    }

    public RunicWarriorSummoningRocket(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(p_36826_, p_36827_, p_36828_, p_36829_, p_36830_, p_36831_);
    }


    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            this.setDeltaMovement(this.getDeltaMovement().add(0,-1/20f,0));
        }
        if (level.isClientSide){
            for (int i = 0;i < 2;i++) {
                Vec3 vec = Helpers.randomVector().normalize().multiply(0.2, 0.2, 0.2);
                ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        position().x + vec.x, position().y + vec.y, position().z + vec.z, 0, 0, 0, () -> 200 + level.random.nextInt(55),
                        () -> 200 + level.random.nextInt(55), () -> 0, 0.4f);
            }
        }
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected float getInertia() {
        return 1f;
    }


    @Override
    protected ParticleOptions getTrailParticle() {
        return SolarcraftParticleTypes.INVISIBLE_PARTICLE.get();
    }

    @Override
    public boolean isAttackable() {
        return false;
    }


    @Override
    protected void onHitBlock(BlockHitResult res) {
        BlockPos pos = res.getBlockPos();
        if (!level.isClientSide){
            RunicWarrior warrior = new RunicWarrior(SCEntityTypes.RUNIC_WARRIOR.get(),level);
            warrior.setPos(Helpers.getBlockCenter(pos.above()));
            level.addFreshEntity(warrior);
            this.kill();
        }
        super.onHitBlock(res);
    }
}
