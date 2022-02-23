package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.local_library.entities.BossAttackChain;
import com.finderfeed.solarforge.local_library.other.InterpolatedValue;
import com.finderfeed.solarforge.magic.projectiles.FallingMagicMissile;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.registries.entities.EntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
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
            .addAttack("earthquake",this::earthquake,120,30,3)
            .addAttack("vartDader",this::varthDader,120,1,4)
            .addAttack("deployRefractionCrystals",this::deployRefractionCrystals,40,1,10)
            .addAftermathAttack(this::resetAttackTypeAndTicker)
            .setTimeBetweenAttacks(30)
            .build();

    public static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(RunicElementalBoss.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_TYPE = SynchedEntityData.defineId(RunicElementalBoss.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(RunicElementalBoss.class, EntityDataSerializers.INT);


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
            for (RefractionCrystal crystal : getRefractionCrystalsAround()){
                between = target.position().add(0,target.getEyeHeight(target.getPose())*0.8,0).subtract(crystal.position().add(0, crystal.getBbHeight()/2, 0));
                FallingMagicMissile m = new FallingMagicMissile(level,between.normalize().multiply(2,2,2));
                m.setSpeedDecrement(0);
                m.setDamage(10f);
                m.setPos(crystal.position().add(0,crystal.getBbHeight()/2,0).add(between.normalize().multiply(0.5,0.5,0.5)));
                level.addFreshEntity(m);
            }
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

                    List<RefractionCrystal> crystals = getRefractionCrystalsAround();
                    if (!crystals.isEmpty()){
                        List<BlockPos> positions = Helpers.getValidSpawningPositionsAround(level,this.getOnPos(),12,2,2);
                        for (RefractionCrystal crystal : crystals){
                            SunstrikeEntity s = new SunstrikeEntity(EntityTypes.SUNSTRIKE.get(),level);
                            s.setDamage(10);
                            s.setPos(Helpers.getBlockCenter(positions.get(level.random.nextInt(positions.size())).above()).add(0,-0.5,0));
                            level.addFreshEntity(s);
                        }
                    }
                }
            }
        }
    }

    public void earthquake(){
        this.setAttackType(AttackType.EARTHQUAKE);
        for (int i = 0; i < 4; i++) {
            Vec3 dir = new Vec3(level.random.nextDouble() * 2 - 1, 0, level.random.nextDouble() * 2 - 1).normalize();
            Vec3 pos = position().add(dir);
            EarthquakeEntity earthquake = new EarthquakeEntity(level, dir, 10);
            earthquake.setPos(pos);
            earthquake.setDamage(20);
            level.addFreshEntity(earthquake);

        }
        for (RefractionCrystal crystal : getRefractionCrystalsAround()){
            for (int i = 0; i < 2 ;i++) {
                Vec3 d = new Vec3(level.random.nextDouble() * 2 - 1, 0, level.random.nextDouble() * 2 - 1).normalize();
                Vec3 p = crystal.position().add(d);
                EarthquakeEntity e = new EarthquakeEntity(level, d, 10);
                e.setPos(p);
                e.setDamage(10);
                level.addFreshEntity(e);
            }
        }
    }



    public void varthDader(){
        this.setAttackType(AttackType.VARTH_DADER);
        if (BOSS_ATTACK_CHAIN.getTicker() > 15 && BOSS_ATTACK_CHAIN.getTicker() < 105) {
            Player player = (Player) getTarget();
            this.setVarthDaderTarget(player.getId());
            Helpers.setServerPlayerSpeed((ServerPlayer) player, new Vec3(0, 4 / 90f, 0));
            if (BOSS_ATTACK_CHAIN.getTicker() % 10 == 0) {
                player.hurt(DamageSource.MAGIC, 3);
            }
        }
    }

    public void deployRefractionCrystals(){
        this.setAttackType(AttackType.REFRACTION_CRYSTALS);
        if (BOSS_ATTACK_CHAIN.getTicker() == 30){
            int c =getRefractionCrystalsAround().size();
            if (c >= 4) return;
            List<BlockPos> positions = Helpers.getValidSpawningPositionsAround(level,this.getOnPos(),12,2,2);
            this.removeDuplicatePositions(positions);
            if (!positions.isEmpty()) {
                BlockPos randomPos1 = positions.get(level.random.nextInt(positions.size()));
                RefractionCrystal crystal = new RefractionCrystal(EntityTypes.REFRACTION_CRYSTAL.get(), level);
                crystal.setPos(Helpers.getBlockCenter(randomPos1.above()).add(0,-0.5,0));
                level.addFreshEntity(crystal);
                if (c == 3) return;
                positions.remove(randomPos1);
                if (!positions.isEmpty()){
                    BlockPos randomPos2 = positions.get(level.random.nextInt(positions.size()));
                    RefractionCrystal crystal2 = new RefractionCrystal(EntityTypes.REFRACTION_CRYSTAL.get(), level);
                    crystal2.setPos(Helpers.getBlockCenter(randomPos2.above()).add(0,-0.5,0));
                    level.addFreshEntity(crystal2);
                }
            }
        }
    }

    private void removeDuplicatePositions(List<BlockPos> positions){
        for (RefractionCrystal crystal : getRefractionCrystalsAround()){
            positions.remove(crystal.getOnPos());
        }
    }

    public void setVarthDaderTarget(int eID){
        this.entityData.set(DATA_ID_ATTACK_TARGET,eID);
    }

    public int getVarthDaderTarget(){
        return this.entityData.get(DATA_ID_ATTACK_TARGET);
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
        this.setAttackType(0);
        this.setAttackTick(0);
        this.setVarthDaderTarget(0);
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
        this.entityData.define(DATA_ID_ATTACK_TARGET,0);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1,new NearestAttackableTargetGoal<Player>(this,Player.class,30,true,true,(t)->true));
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

    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }


    private List<RefractionCrystal> getRefractionCrystalsAround(){
        return level.getEntitiesOfClass(RefractionCrystal.class,new AABB(-16,-4,-16,16,4,16).move(position()),(c)->{
            return !c.isDeploying();
        });
    }

    public static class AttackType{
        public static final int MAGIC_MISSILES = 1;
        public static final int REFRACTION_CRYSTALS = 2;
        public static final int SUNSTRIKES = 3;
        public static final int EARTHQUAKE = 4;
        public static final int VARTH_DADER = 5;

    }
//    public void flyUpAndThrowFireballs(){
//        this.setAttackType(AttackType.FIREBALLS);
//        if (BOSS_ATTACK_CHAIN.getTicker() <= 60){
//            this.setDeltaMovement(0,7/60f,0);
//        }else{
//            this.setDeltaMovement(0,0,0);
//            if (BOSS_ATTACK_CHAIN.getTicker() % 7 == 0) {
//                for (int i = 0; i < 3; i++) {
//                    Vec3 speed = new Vec3(level.random.nextDouble() * 2 - 1, level.random.nextDouble() * -0.6, level.random.nextDouble() * 2 - 1).normalize();
//                    SolarFireballProjectile fireball = new SolarFireballProjectile(EntityTypes.SOLAR_FIREBALL.get(), level);
//                    fireball.setDamage(10);
//                    fireball.setPos(this.position());
//                    fireball.setOwner(this);
//                    fireball.setDeltaMovement(speed.multiply(0.4,0.4,0.4));
//                    level.addFreshEntity(fireball);
//                }
//            }
//
//        }
//    }
}
