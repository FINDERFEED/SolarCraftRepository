package com.finderfeed.solarforge.magic.blocks.blockentities.projectiles;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.client.particles.ParticleTypesRegistry;
import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.packet_handler.packets.ShadowBoltExplosionPacket;
import com.finderfeed.solarforge.registries.SolarcraftDamageSources;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.mojang.math.Matrix3f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class ShadowBolt extends AbstractHurtingProjectile {
    public ShadowBolt(EntityType<? extends AbstractHurtingProjectile> type, Level world) {
        super(type, world);
    }

    //x1 * x2 + y1 * y2 + z1 * z2 = 0
    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide){
            Vec3 pos = this.position().add(0,0.125,0);
            Vec3 v = Helpers.randomVector().multiply(0.25,0.25,0.25);
            Vec3 vec = new Vec3(v.x+pos.x,v.y+pos.y,v.z+pos.z);
            ClientHelpers.ParticleAnimationHelper.createParticle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    vec.x,vec.y,vec.z,0,0,0,()->70,()->0,()->200,0.25f);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult res) {
        super.onHitEntity(res);
        if (!level.isClientSide){
            Entity e = res.getEntity();
            if (e instanceof LivingEntity entity){
                this.explode();
                this.kill();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult res) {
        if (!level.isClientSide){
            this.explode();
            this.kill();
        }
        super.onHitBlock(res);

    }


    private void explode(){
        ShadowBoltExplosionPacket.send(level,this.position());
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class,new AABB(-4,-4,-4,4,4,4).move(position()),(en)->{
            return en.distanceToSqr(this.position()) <= 3.5*3.5;
        })){
            e.hurt(SolarcraftDamageSources.SHADOW.setMagic().bypassArmor().setProjectile(),5);
        }
        level.playSound(null,position().x,position().y,position().z, Sounds.SHADOW_BOLT_EXPLOSION.get(), SoundSource.HOSTILE,4,0.75f);
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
    protected float getInertia() {
        return 1f;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypesRegistry.INVISIBLE_PARTICLE.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
