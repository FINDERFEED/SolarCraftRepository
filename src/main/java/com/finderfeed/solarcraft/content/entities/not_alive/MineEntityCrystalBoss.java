package com.finderfeed.solarcraft.content.entities.not_alive;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.entities.CrystalBossEntity;
import com.finderfeed.solarcraft.misc_things.CrystalBossBuddy;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.NetworkHooks;

public class MineEntityCrystalBoss extends PathfinderMob implements CrystalBossBuddy {

    public static EntityDataAccessor<Integer> TICKER = SynchedEntityData.defineId(MineEntityCrystalBoss.class, EntityDataSerializers.INT);
    private int ticker = 0;


    public MineEntityCrystalBoss(EntityType<? extends MineEntityCrystalBoss> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes(){
        return PathfinderMob.createMobAttributes();
    }

    @Override
    public void tick() {
        super.tick();
        int tick = this.entityData.get(TICKER);
        if (tick >= 61){
            this.discard();
        }
        if (tick == 60){
            blowUp();
        }

        if (!this.level.isClientSide){
            ticker++;
            this.entityData.set(TICKER,ticker);

        }


    }


    public void blowUp(){
        level.getEntitiesOfClass(LivingEntity.class,new AABB(-1.5,-1.0,-1.5,1.5,2,1.5).move(position()),(ent)->{
            return !(ent instanceof CrystalBossBuddy);
        }).forEach((living)->{
            living.setDeltaMovement(living.position().subtract(this.position()).normalize().add(0,1,0).multiply(1.5,1.5,1.5));
        });
        if (this.level.isClientSide){
            createExplosionParticles();
        }
        if (!this.level.isClientSide){
            level.playSound(null,this.getX(),this.getY(),this.getZ(), SolarcraftSounds.SOLAR_EXPLOSION.get(), SoundSource.AMBIENT,level.random.nextFloat()*0.5f+0.5f,1f);
            level.getEntitiesOfClass(LivingEntity.class,new AABB(-1.5,-1.0,-1.5,1.5,2,1.5).move(position()),(ent)->{
                return !(ent instanceof CrystalBossBuddy);
            }).forEach((living)->{
                living.hurt(SCDamageSources.RUNIC_MAGIC, CrystalBossEntity.MINES_DAMAGE);
            });
        }

    }

    private void createExplosionParticles(){
        Helpers.createSmallSolarStrikeParticleExplosion(level, this.position(),2,0.1f,0.5f);
    }


    @Override
    public boolean save(CompoundTag tag) {
        tag.putInt("ticker",ticker);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.ticker = tag.getInt("ticker");
        super.load(tag);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TICKER,0);
        super.defineSynchedData();
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    @Override
    protected void doPush(Entity pEntity) {
    }

    @Override
    public boolean isPushable() {
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
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {

    }
}
