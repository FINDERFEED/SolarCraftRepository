package com.finderfeed.solarcraft.content.entities.not_alive;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.packet_handler.packets.misc_packets.BallLightningSpawnLightningParticles;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;


public class BallLightningProjectile extends AbstractHurtingProjectile {

    public static final AABB BOX = new AABB(-10,-10,-10,10,10,10);
    private int lifeTicks = 0;

    public BallLightningProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public BallLightningProjectile( double x, double y, double z, double xv, double yv, double zv, Level world) {
        super(SCEntityTypes.BALL_LIGHTNING.get(), x, y, z, xv, yv, zv, world);
    }

    public BallLightningProjectile( LivingEntity owner, double xv, double yv, double zv, Level world) {
        super(SCEntityTypes.BALL_LIGHTNING.get(), owner, xv, yv, zv, world);
    }


    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            if (lifeTicks++ > 120) {
                doExplosion(position());
                this.kill();
            }
        }
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return SCParticleTypes.INVISIBLE_PARTICLE.get();
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putInt("lifeticks",lifeTicks);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.lifeTicks = tag.getInt("lifeticks");
        super.load(tag);
    }

    @Override
    protected void onHitEntity(EntityHitResult res) {
        super.onHitEntity(res);
        if (!this.level.isClientSide && !(res.getEntity() instanceof  Player)){
            doExplosion(res.getLocation());
            this.kill();
        }
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    protected void onHitBlock(BlockHitResult res) {
        super.onHitBlock(res);
        if (!this.level.isClientSide){
            doExplosion(res.getLocation());
            this.kill();
        }
    }


    private void doExplosion(Vec3 position){
        List<LivingEntity> living = this.level.getEntitiesOfClass(LivingEntity.class,BOX.move(position),(l)->!(l instanceof Player));
        BallLightningSpawnLightningParticles.sendToServer(level,position);
        this.level.playSound(null,this.getX(),this.getY(),this.getZ(), SCSounds.BALL_LIGHTNING_BLOW.get(), SoundSource.PLAYERS,10,1);
        for (LivingEntity ent : living){
            if (ent.distanceTo(this) <= 10){
                ent.hurt(level.damageSources().lightningBolt(),10);
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
