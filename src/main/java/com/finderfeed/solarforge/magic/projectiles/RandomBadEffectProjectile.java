package com.finderfeed.solarforge.magic.projectiles;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.registries.entities.SolarcraftEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.Map;
import java.util.Random;

public class RandomBadEffectProjectile extends AbstractHurtingProjectile {

    private static Map<Integer, MobEffect> ID = Map.of(
            0,MobEffects.POISON,
            1,MobEffects.MOVEMENT_SLOWDOWN,
            2,MobEffects.WEAKNESS,
            3,MobEffects.WITHER,
            4,MobEffects.BLINDNESS
    );

    private static Map<MobEffect, Integer> REVERSE_ID = Map.of(
            MobEffects.POISON,0,
            MobEffects.MOVEMENT_SLOWDOWN,1,
            MobEffects.WEAKNESS, 2,
            MobEffects.WITHER,3,
            MobEffects.BLINDNESS,4
    );

    private  boolean removeit = false;

    private static EntityDataAccessor<Integer> POTION_DATA_ID = SynchedEntityData.defineId(RandomBadEffectProjectile.class, EntityDataSerializers.INT);

    public int potionID = 0;

    public RandomBadEffectProjectile(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public RandomBadEffectProjectile(double p_36818_, double p_36819_, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(SolarcraftEntityTypes.RANDOM_BAD_EFFECT_PROJECTILE.get(), p_36818_, p_36819_, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
        MobEffect effect = ID.get(new Random().nextInt(ID.keySet().size()));
        this.potionID = REVERSE_ID.get(effect);
    }

    public RandomBadEffectProjectile(LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(SolarcraftEntityTypes.RANDOM_BAD_EFFECT_PROJECTILE.get(), p_36827_, p_36828_, p_36829_, p_36830_, p_36831_);
        MobEffect effect = ID.get(new Random().nextInt(ID.keySet().size()));
        this.potionID = REVERSE_ID.get(effect);
    }



    @Override
    public void tick() {
        if (!this.level.isClientSide){
            this.entityData.set(POTION_DATA_ID,potionID);
            if (removeit){
                this.kill();
            }
        }
        super.tick();
        if (this.level.isClientSide){
            ClientHelpers.createEffectParticle(this.position().x,this.position().y,this.position().z,0,0,0,ID.get(this.entityData.get(POTION_DATA_ID)));

        }
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        explode();
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {
        super.onHitEntity(p_37259_);
        if (!(p_37259_.getEntity()  instanceof  RandomBadEffectProjectile) && !(p_37259_.getEntity() instanceof CrystalBossBuddy)) {
            explode();
        }
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    public void explode(){
        if (this.level.isClientSide){
            for (int i = 0; i < 12;i++){
                float radius = 3f;
                for (int g = 0; g < radius;g++) {
                    double[] xz = FDMathHelper.polarToCartesian(g, i * 30);
                    ClientHelpers.createEffectParticle(
                            this.position().x + xz[0],
                            this.position().y + this.getBbHeight()/2,
                            this.position().z + xz[1],
                            level.random.nextDouble()*0.2f-0.1f,
                            level.random.nextDouble()*0.2f-0.1f,
                            level.random.nextDouble()*0.2f-0.1f,
                            ID.get(this.entityData.get(POTION_DATA_ID)));
                }
            }
        }else{
            level.getEntitiesOfClass(LivingEntity.class,new AABB(-3,-2,-3,3,2,3).move(this.position()))
            .forEach((entity)->{
                MobEffect effect = ID.get(potionID);
               if (!entity.hasEffect(effect)){
                   entity.addEffect(new MobEffectInstance(effect,300,0));
               }
            });
            this.removeit = true;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POTION_DATA_ID,0);
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return SolarcraftParticleTypes.INVISIBLE_PARTICLE.get();
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putInt("potionID",potionID);
        tag.putBoolean("removeit",removeit);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.potionID = tag.getInt("potionID");
        this.removeit= tag.getBoolean("removeit");
        super.load(tag);
    }
}
