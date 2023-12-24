package com.finderfeed.solarcraft.content.entities.not_alive;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.misc_things.CrystalBossBuddy;
import com.finderfeed.solarcraft.registries.attributes.AttributesRegistry;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.NetworkHooks;
import javax.annotation.Nullable;

public class RefractionCrystal extends Mob implements CrystalBossBuddy {
    public static final int DEPLOYING_TIME = 20;

    public RefractionCrystal(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH,120).add(Attributes.ARMOR,10)
                .add(Attributes.FOLLOW_RANGE).add(AttributesRegistry.MAGIC_RESISTANCE.get(),30);
    }


    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide){
            if (isDeploying()){
                for (int i = 0;i < 6;i++){
                    double[] xz = FDMathHelper.rotatePointDegrees(1.5*(1-(float)tickCount / DEPLOYING_TIME),0,i*60 + tickCount*5);
                    ClientHelpers.Particles.createParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                            position().x + xz[0],position().y + 1.5,position().z + xz[1],0,0,0,()->200 + level.random.nextInt(55),
                            ()->200 + level.random.nextInt(55),()->0,0.3f);
                }
            }
        }
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean isPushable() {
        return false;
    }



    @Override
    protected void doPush(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.setDeltaMovement(entity.position().add(0,entity.getBbHeight()/2,0).subtract(this.position().add(0,this.getBbHeight()/2,0)).normalize());
    }

    public boolean isDeploying(){
        return tickCount <= DEPLOYING_TIME;
    }

    @Override
    public boolean attackable() {
        return !isDeploying();
    }

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {}

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
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
    public boolean canChangeDimensions() {
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
    public boolean fireImmune() {
        return true;
    }
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SCSounds.CRYSTAL_HIT.get();
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SCSounds.CRYSTAL_HIT.get();
    }

    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }
}
