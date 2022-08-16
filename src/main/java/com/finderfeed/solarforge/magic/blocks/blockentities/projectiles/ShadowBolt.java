package com.finderfeed.solarforge.magic.blocks.blockentities.projectiles;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.packet_handler.packets.ShadowBoltExplosionPacket;
import com.finderfeed.solarforge.registries.SolarcraftDamageSources;
import com.finderfeed.solarforge.registries.sounds.SolarcraftSounds;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
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
            Vec3 pos = this.position();

            Vec3 movement = getDeltaMovement();
            Vector3f mv = new Vector3f((float)movement.x,(float)movement.y,(float)movement.z);
            float z2 = -mv.x() / (mv.z() + 0.000001f);
            for (int i = 0;i < 2;i++) {
                Vector3f rotateIt = new Vector3f(1, 0, z2);
                rotateIt.normalize();
                Quaternion q = mv.rotationDegrees((level.getGameTime() * 20 + i*180)  % 360);
                rotateIt.transform(q);
                Vec3 normalVec = new Vec3(rotateIt.x()*0.1, rotateIt.y()*0.1, rotateIt.z()*0.1);
                Vec3 p = pos.add(normalVec);
                ClientHelpers.ParticleAnimationHelper.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        p.x, p.y, p.z, normalVec.x*0.3, normalVec.y*0.3, normalVec.z*0.3, () -> 70, () -> 0, () -> 200, 0.25f);
            }

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
        level.playSound(null,position().x,position().y,position().z, SolarcraftSounds.SHADOW_BOLT_EXPLOSION.get(), SoundSource.HOSTILE,4,0.75f);
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
        return SolarcraftParticleTypes.INVISIBLE_PARTICLE.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
