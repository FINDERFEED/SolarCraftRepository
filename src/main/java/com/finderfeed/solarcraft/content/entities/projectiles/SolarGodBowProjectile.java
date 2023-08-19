package com.finderfeed.solarcraft.content.entities.projectiles;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.registries.damage_sources.SolarcraftDamageSources;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
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

public class SolarGodBowProjectile extends OwnedProjectile {

    private int projectileLevel;

    private float explosionPower = 0;
    private float damage;
    private static AABB AOE = new AABB(-15,-5,-15,15,5,15);

    public SolarGodBowProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public SolarGodBowProjectile(EntityType<? extends AbstractHurtingProjectile> x, double y, double z, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(x, y, z, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
    }

    public SolarGodBowProjectile(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity owner, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(p_36826_, owner, p_36828_, p_36829_, p_36830_, p_36831_);
    }


    private void onHit(EntityHitResult ctx){
        Entity entity1 = ctx.getEntity();
        LivingEntity owner = null;
        if (entity1 instanceof LivingEntity ent && ent != (owner = this.getLivingEntityOwner())) {
            if (owner != null){
                ent.hurt(SolarcraftDamageSources.livingArmorPierceProjectile(owner),damage);
            }else{
                ent.hurt(damageSources().magic(),damage);
            }

            if (explosionPower > 0){
                Vec3 p = this.position();
                level.explode(owner,p.x,p.y,p.z,explosionPower, Level.ExplosionInteraction.BLOCK);
            }

            if (projectileLevel >= 2){
                ent.setSecondsOnFire(8);
            }
            if (projectileLevel >= 3) {
                ent.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
            }
            if (projectileLevel >= 5){
                List<LivingEntity> targets = ent.level.getEntitiesOfClass(LivingEntity.class, AOE.move(ent.position()),(target)->{
                    if (target instanceof Player || target.equals(ent) || target.equals(this.getOwner())) {
                        return false;
                    }else{
                        return true;
                    }
                });
                targets.forEach((target)->{
                    Vec3 origPos = ent.position().add(0,ent.getBbHeight()/1.2,0);
                    Vec3 targetPos = target.position().add(0,target.getBbHeight()/1.2,0);
                    Vec3 velocity = new Vec3(targetPos.x - origPos.x,targetPos.y - origPos.y,targetPos.z - origPos.z);

                    SolarGodBowProjectile projectile = new SolarGodBowProjectile(SCEntityTypes.SOLAR_GOD_BOW_PROJECTILE.get(),level);
                    projectile.setPos(origPos);
                    projectile.setDeltaMovement(velocity.normalize().multiply(3,3,3));
                    projectile.projectileLevel = 2;
                    projectile.setDamage(this.getDamage()/2);
                    ctx.getEntity().level.addFreshEntity(projectile);
                });
            }
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            if (tickCount > 2000){
                this.remove(RemovalReason.DISCARDED);
            }
        }else {
            level.addParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), true, position().x, position().y + 0.15, position().z, 0, 0, 0);
        }
    }

    public void setProjectileLevel(int projectileLevel) {
        this.projectileLevel = projectileLevel;
    }

    public int getProjectileLevel() {
        return projectileLevel;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setExplosionPower(float explosionPower) {
        this.explosionPower = explosionPower;
    }

    public float getExplosionPower() {
        return explosionPower;
    }

    @Override
    protected void onHitEntity(EntityHitResult ctx) {
        if (!level.isClientSide){
            this.onHit(ctx);
        }
        super.onHitEntity(ctx);
    }

    @Override
    protected void onHitBlock(BlockHitResult res) {
        super.onHitBlock(res);
        if (!level.isClientSide){
            this.kill();
        }
    }

    @Override
    public boolean save(CompoundTag tag) {

        tag.putFloat("explosionPower",this.explosionPower);
        tag.putInt("projectileLevel",this.projectileLevel);
        tag.putFloat("damage",this.damage);
        return super.save(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        this.damage = tag.getFloat("damage");
        this.projectileLevel = tag.getInt("projectileLevel");
        this.explosionPower = tag.getFloat("explosionPower");
        super.load(tag);
    }
}
