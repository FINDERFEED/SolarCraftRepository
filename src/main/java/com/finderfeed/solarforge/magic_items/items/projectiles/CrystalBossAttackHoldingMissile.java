package com.finderfeed.solarforge.magic_items.items.projectiles;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.entities.CrystalBossEntity;
import com.finderfeed.solarforge.entities.ShieldingCrystalCrystalBoss;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.registries.entities.Entities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import java.util.List;
import java.util.UUID;

public class CrystalBossAttackHoldingMissile extends AbstractHurtingProjectile implements CrystalBossBuddy{

    private static EntityDataAccessor<Boolean> LAUNCHED = SynchedEntityData.defineId(CrystalBossAttackHoldingMissile.class, EntityDataSerializers.BOOLEAN);
    private UUID TARGET;
    private float HOLDING_TIME;
    private AABB findbox = new AABB(-16,-16,-16,16,16,16);
    private boolean launched = false;
    private int ticker = 0;

    public CrystalBossAttackHoldingMissile(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    private CrystalBossAttackHoldingMissile(Builder builder){
        super(Entities.CRYSTAL_BOSS_ATTACK_HOLDING_MISSILE.get(),builder.world);
        this.TARGET = builder.target;
        this.HOLDING_TIME = builder.holding_time;
        this.setPos(builder.position);
        this.setDeltaMovement(builder.initialSpeed);
    }




    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide){
            this.entityData.set(LAUNCHED,launched);
            List<Player> players = level.getEntitiesOfClass(Player.class,findbox.move(this.position()));
            Player targetPlayer = null;
            for (Player player : players){
                if (player.getUUID().equals(TARGET)){
                    targetPlayer = player;
                }
            }
            if (targetPlayer != null){
                if (ticker++ >= HOLDING_TIME*20){
                    if (!launched){
                        this.setDeltaMovement(targetPlayer
                                .position()
                                .add(0,targetPlayer.getBbHeight()/2,0)
                                .subtract(position())
                                .normalize()
                                .multiply(2,2,2));

                    }
                    this.launched = true;
                }
            }else{
                this.discard();
            }


        }
        if (this.level.isClientSide || (getOwner() == null || !getOwner().isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            this.level.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(), d0, d1 + 0.125D, d2, 0.0D, 0.0D, 0.0D);

        }
    }

    @Override
    protected float getInertia() {
        return this.entityData.get(LAUNCHED) ? 1.0f : 0.7f;
    }

    @Override
    public void load(CompoundTag cmp) {
        this.TARGET = cmp.getUUID("target");
        this.launched = cmp.getBoolean("released");
        this.HOLDING_TIME = cmp.getFloat("hold");
        this.ticker = cmp.getInt("ticker");
        super.load(cmp);
    }

    @Override
    public boolean save(CompoundTag cmp) {
        cmp.putUUID("target",TARGET);
        cmp.putBoolean("released",launched);
        cmp.putFloat("hold",HOLDING_TIME);
        cmp.putInt("ticker",ticker);
        return super.save(cmp);
    }

    @Override
    protected void onHitEntity(EntityHitResult ent) {
        if (!(ent.getEntity() instanceof CrystalBossEntity) && !(ent.getEntity() instanceof CrystalBossBuddy)){
            if (Helpers.isVulnerable(ent.getEntity())) {
                ent.getEntity().hurt(DamageSource.MAGIC, 2);
                ent.getEntity().invulnerableTime = 0;
            }
            this.discard();
        }
        super.onHitEntity(ent);
    }


    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        this.discard();
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
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticlesList.INVISIBLE_PARTICLE.get();
    }

    public static class Builder{
        private Level world;
        private UUID target;
        private float holding_time;
        private Vec3 position;
        private Vec3 initialSpeed;

        public Builder(Level world){
            this.world = world;
        }

        public Builder setTarget(UUID target) {
            this.target = target;
            return this;
        }

        public Builder setHoldingTime(float holding_time) {
            this.holding_time = holding_time;
            return this;
        }
        public Builder setInitialSpeed(Vec3 speed){
            this.initialSpeed = speed;
            return this;
        }
        public Builder setPosition(Vec3 pos){
            this.position = pos;
            return this;
        }


        public CrystalBossAttackHoldingMissile build(){
            return new CrystalBossAttackHoldingMissile(this);
        }

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LAUNCHED,false);
    }
}

