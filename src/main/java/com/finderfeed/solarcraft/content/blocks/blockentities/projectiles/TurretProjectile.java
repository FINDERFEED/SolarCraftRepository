package com.finderfeed.solarcraft.content.blocks.blockentities.projectiles;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.content.entities.projectiles.OwnedProjectile;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import net.minecraft.world.level.Level;
import java.util.function.Consumer;

public class TurretProjectile extends OwnedProjectile {

    public float damage;
    public float explosionPower;


    public TurretProjectile(EntityType<? extends TurretProjectile> p_i50173_1_, Level p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

    public TurretProjectile(EntityType<? extends TurretProjectile> p_i50174_1_, double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, Level p_i50174_14_) {
        super(SCEntityTypes.TURRET_PROJECTILE.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public TurretProjectile(EntityType<? extends TurretProjectile> p_i50175_1_, LivingEntity p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level p_i50175_9_) {
        super(SCEntityTypes.TURRET_PROJECTILE.get(), p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
    }




    @Override
    protected void onHitEntity(EntityHitResult ctx) {
        if (!level().isClientSide) {
            ctx.getEntity().hurt(level().damageSources().magic(), damage);
            if (explosionPower > 0) {
                level().explode(null, ctx.getLocation().x, ctx.getLocation().y, ctx.getLocation().z, explosionPower,true, Level.ExplosionInteraction.BLOCK);
            }
        }

        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!level().isClientSide) {
            if (explosionPower > 0) {
                level().explode(null, result.getLocation().x, result.getLocation().y, result.getLocation().z, explosionPower,true, Level.ExplosionInteraction.BLOCK);
            }
        }


        this.remove(RemovalReason.KILLED);
    }




    @Override
    protected float getInertia() {
        return 1F;
    }


    @Override
    public void tick(){
        if (level().isClientSide) {
            level().addParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), true, position().x, position().y + 0.15, position().z, 0, 0, 0);
        }
        super.tick();

    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
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
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }
//    @Override
//    public Packet<ClientGamePacketListener> getAddEntityPacket() {
//        return NetworkHooks.getEntitySpawningPacket(this);
//    }

    

//    public static class Constructor{
//        private float explosionPower = 0;
//        private Vec3 position;
//        private Vec3 velocity;
//        private float damage;
//        private DamageSource src;
//        private Consumer<EntityHitResult> ON_HIT_ENTITY;
//        private Consumer<BlockHitResult> ON_HIT_BLOCK;
//        public Constructor setDamage(float a){
//            damage = a;
//            return this;
//        }
//
//        public Constructor setVelocity(Vec3 vel){
//            velocity = vel;
//            return this;
//        }
//
//        public Constructor setVelocity(double a,double b, double c){
//            velocity = new Vec3(a,b,c);
//            return this;
//        }
//
//        public Constructor setPosition(Vec3 vel){
//            position = vel;
//            return this;
//        }
//
//        public Constructor setPosition(double a,double b, double c){
//            position = new Vec3(a,b,c);
//            return this;
//        }
//
//        public Constructor setDamageSource(DamageSource src){
//                this.src = src;
//            return this;
//        }
//
//        public Constructor setExplosionPower(float a){
//            this.explosionPower = a;
//            return this;
//        }
//
//
//        public void editDamage(float a){
//            damage = a;
//
//        }
//
//        public void editVelocity(Vec3 vel){
//            velocity = vel;
//
//        }
//
//        public void editVelocity(double a,double b, double c){
//            velocity = new Vec3(a,b,c);
//
//        }
//
//        public void editPosition(Vec3 vel){
//            position = vel;
//
//        }
//
//        public void editPosition(double a,double b, double c){
//            position = new Vec3(a,b,c);
//
//        }
//
//        public void editDamageSource(DamageSource src){
//            this.src = src;
//
//        }
//
//        public void editExplosionPower(float a){
//            this.explosionPower = a;
//
//        }
//
//        public Constructor addOnHitEntityEffect(Consumer<EntityHitResult> method){
//            ON_HIT_ENTITY = method;
//            return this;
//        }
//        public Constructor addOnHitBlockEffect(Consumer<BlockHitResult> method){
//            ON_HIT_BLOCK = method;
//            return this;
//        }
//    }
}
