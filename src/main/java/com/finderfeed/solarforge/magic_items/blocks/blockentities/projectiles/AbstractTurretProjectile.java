package com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles;

import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AbstractTurretProjectile extends DamagingProjectileEntity {

    public float damage;
    public float explosionPower;

    public AbstractTurretProjectile(EntityType<? extends AbstractTurretProjectile> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

    public AbstractTurretProjectile(EntityType<? extends AbstractTurretProjectile> p_i50174_1_, double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, World p_i50174_14_) {
        super(Projectiles.TURRET_PROJECTILE.get(), p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public AbstractTurretProjectile(EntityType<? extends AbstractTurretProjectile> p_i50175_1_, LivingEntity p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World p_i50175_9_) {
        super(Projectiles.TURRET_PROJECTILE.get(), p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
    }

    public AbstractTurretProjectile(World level,AbstractTurretProjectile.Constructor cons){
        super(Projectiles.TURRET_PROJECTILE.get(), level);
        this.setPos(cons.position.x,cons.position.y,cons.position.z);
        this.setDeltaMovement(cons.velocity);
        this.damage = cons.damage;
        this.explosionPower = cons.explosionPower;

    }



    @Override
    protected void onHitEntity(EntityRayTraceResult ctx) {
        if (!level.isClientSide) {
            ctx.getEntity().hurt(DamageSource.MAGIC, damage);
            if (explosionPower > 0) {
                level.explode(null, ctx.getLocation().x, ctx.getLocation().y, ctx.getLocation().z, explosionPower,true, Explosion.Mode.BREAK);
            }
        }
        this.remove();
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult result) {
        if (!level.isClientSide) {
            if (explosionPower > 0) {
                level.explode(null, result.getLocation().x, result.getLocation().y, result.getLocation().z, explosionPower,true, Explosion.Mode.BREAK);
            }
        }
        this.remove();
    }




    @Override
    protected float getInertia() {
        return 1F;
    }


    @Override
    public void tick(){
        if (level.isClientSide) {
            level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(), true, position().x, position().y + 0.15, position().z, 0, 0, 0);
        }
        super.tick();

    }

    @Override
    protected IParticleData getTrailParticle() {
        return ParticlesList.INVISIBLE_PARTICLE.get();
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }
    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    public static class Constructor{
        private float explosionPower = 0;
        private Vector3d position;
        private Vector3d velocity;
        private float damage;
        private DamageSource src;
        public Constructor setDamage(float a){
            damage = a;
            return this;
        }

        public Constructor setVelocity(Vector3d vel){
            velocity = vel;
            return this;
        }

        public Constructor setVelocity(double a,double b, double c){
            velocity = new Vector3d(a,b,c);
            return this;
        }

        public Constructor setPosition(Vector3d vel){
            position = vel;
            return this;
        }

        public Constructor setPosition(double a,double b, double c){
            position = new Vector3d(a,b,c);
            return this;
        }

        public Constructor setDamageSource(DamageSource src){
                this.src = src;
            return this;
        }

        public Constructor setExplosionPower(float a){
            this.explosionPower = a;
            return this;
        }


        public void editDamage(float a){
            damage = a;

        }

        public void editVelocity(Vector3d vel){
            velocity = vel;

        }

        public void editVelocity(double a,double b, double c){
            velocity = new Vector3d(a,b,c);

        }

        public void editPosition(Vector3d vel){
            position = vel;

        }

        public void editPosition(double a,double b, double c){
            position = new Vector3d(a,b,c);

        }

        public void editDamageSource(DamageSource src){
            this.src = src;

        }

        public void editExplosionPower(float a){
            this.explosionPower = a;

        }
    }
}
