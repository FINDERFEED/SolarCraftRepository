package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.local_library.entities.BossAttackChain;
import com.finderfeed.solarforge.local_library.other.InterpolatedValue;
import com.finderfeed.solarforge.magic.projectiles.FallingMagicMissile;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.registries.entities.EntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunicElementalBoss extends Mob implements CrystalBossBuddy {

    public static final String MAGIC_MISSILES_ATTACK = "magic_missiles";

    private Map<String,InterpolatedValue> ANIMATION_VALUES = new HashMap<>();
    public BossAttackChain BOSS_ATTACK_CHAIN = new BossAttackChain.Builder()
            .addAttack(MAGIC_MISSILES_ATTACK,this::magicMissilesAttack,220,10,1)
            .addAttack("sunstrikes",this::sunstrikes,130,1,2)
            .addAftermathAttack(this::resetAttackTypeAndTicker)
            .setTimeBetweenAttacks(20)
            .build();

    public static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(RunicElementalBoss.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_TYPE = SynchedEntityData.defineId(RunicElementalBoss.class, EntityDataSerializers.INT);

    public RunicElementalBoss(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }


    @Override
    public void tick() {

        if (!level.isClientSide){
            LivingEntity target = getTarget();
            if (target != null){
                BOSS_ATTACK_CHAIN.tick();
                this.setAttackTick(BOSS_ATTACK_CHAIN.getTicker());
                this.lookControl.setLookAt(target.position().add(0,target.getEyeHeight(target.getPose())*0.8,0));
            }else{
                if (getAttackTick() != 0 && getAttackType() != 0){
                    resetAttackTypeAndTicker();
                }
            }
        }
        if (level.isClientSide){
            List<String> delete = new ArrayList<>();
            for (Map.Entry<String,InterpolatedValue> entry : ANIMATION_VALUES.entrySet()){
                if (entry.getValue().canBeDeleted()){
                    delete.add(entry.getKey());
                }
                entry.getValue().tick();
            }
            for (String s : delete){
                ANIMATION_VALUES.remove(s);
            }
        }
        super.tick();
    }

    public void magicMissilesAttack(){
        this.setAttackType(AttackType.MAGIC_MISSILES);
        if (BOSS_ATTACK_CHAIN.getTicker() > 20 && BOSS_ATTACK_CHAIN.getTicker() < 205) {
            LivingEntity target = getTarget();
            Vec3 between = target.position().add(0,target.getEyeHeight(target.getPose())*0.8,0).subtract(position().add(0, 2, 0));
            FallingMagicMissile missile = new FallingMagicMissile(level,between.normalize().multiply(2,2,2));
            missile.setSpeedDecrement(0);
            missile.setDamage(10f);
            missile.setPos(this.position().add(0,2,0).add(between.normalize().multiply(0.5,0.5,0.5)));
            level.addFreshEntity(missile);
        }
    }

    public void sunstrikes(){
        this.setAttackType(AttackType.SUNSTRIKES);
        if (BOSS_ATTACK_CHAIN.getTicker() >= 15 && BOSS_ATTACK_CHAIN.getTicker() <= 115){
            if (BOSS_ATTACK_CHAIN.getTicker() % 9 == 0){
                for (Player player : level.getEntitiesOfClass(Player.class,new AABB(-16,-8,-16,16,8,16).move(position()))){
                    SunstrikeEntity sunstrike = new SunstrikeEntity(EntityTypes.SUNSTRIKE.get(),level);
                    Vec3 playerSpeed = player.getLookAngle().multiply(1f,0,1f).normalize().multiply(0.5,0,0.5);
                    sunstrike.setDamage(20);
                    sunstrike.setPos(player.position().add(playerSpeed));
                    level.addFreshEntity(sunstrike);
                }
            }
        }
    }

    public void flyUpAndThrowFireballs(){
        this.setAttackType(AttackType.FIREBALLS);

    }

    @Override
    public boolean isNoGravity() {
        return this.getAttackType() == AttackType.FIREBALLS;
    }

    @Override
    protected int calculateFallDamage(float p_21237_, float p_21238_) {
        return 0;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    public void resetAttackTypeAndTicker(){
        setAttackType(0);
        setAttackTick(0);
    }

    public void setAttackTick(int t){
        this.entityData.set(ATTACK_TICK,t);
    }

    public void setAttackType(int type){
        this.entityData.set(ATTACK_TYPE,type);
    }

    public int getAttackTick(){
        return this.entityData.get(ATTACK_TICK);
    }
    public int getAttackType(){
        return this.entityData.get(ATTACK_TYPE);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_TICK,0);
        this.entityData.define(ATTACK_TYPE,0);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(3,new NearestAttackableTargetGoal<>(this,Player.class,true));
        super.registerGoals();
    }

    public InterpolatedValue getOrCreateAnimationValue(String str, InterpolatedValue value){
        return ANIMATION_VALUES.computeIfAbsent(str,(s)->value);
    }

    @Override
    public boolean save(CompoundTag tag) {
        BOSS_ATTACK_CHAIN.save(tag);
        return super.save(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        BOSS_ATTACK_CHAIN.load(tag);
        super.load(tag);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static class AttackType{
        public static final int MAGIC_MISSILES = 1;
        public static final int FIREBALLS = 2;
        public static final int SUNSTRIKES = 3;

    }
}
