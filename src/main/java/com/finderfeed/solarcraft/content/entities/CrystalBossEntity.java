package com.finderfeed.solarcraft.content.entities;


import com.finderfeed.solarcraft.content.entities.runic_elemental.RunicElementalBoss;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.entities.not_alive.LegendaryItem;
import com.finderfeed.solarcraft.content.entities.not_alive.MineEntityCrystalBoss;
import com.finderfeed.solarcraft.content.entities.not_alive.RipRayGenerator;
import com.finderfeed.solarcraft.content.entities.not_alive.ShieldingCrystalCrystalBoss;
import com.finderfeed.solarcraft.events.my_events.AbilityUseEvent;
import com.finderfeed.solarcraft.events.other_events.event_handler.EventHandler;
import com.finderfeed.solarcraft.local_library.entities.BossAttackChain;
import com.finderfeed.solarcraft.local_library.entities.bossbar.server.CustomServerBossEvent;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.other.CyclingInterpolatedValue;
import com.finderfeed.solarcraft.content.entities.projectiles.CrystalBossAttackHoldingMissile;
import com.finderfeed.solarcraft.content.entities.projectiles.MagicMissile;
import com.finderfeed.solarcraft.content.entities.projectiles.RandomBadEffectProjectile;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.misc_things.CrystalBossBuddy;
import com.finderfeed.solarcraft.misc_things.NoHealthLimitMob;
import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.registries.attributes.AttributesRegistry;
import com.finderfeed.solarcraft.registries.damage_sources.SolarcraftDamageSources;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;


public class CrystalBossEntity extends NoHealthLimitMob implements CrystalBossBuddy {

    public static final int RAY_LENGTH = 13;
    public static final float RAY_DAMAGE = 12;
    public static final float MISSILE_DAMAGE = 1.5F;
    public static final float MINES_DAMAGE = 3F;
    public static final float AIR_STRIKE_DAMAGE = 4.5F;
    public static final float RIP_RAY_DAMAGE = 5F;
    public static final float UP_SPEED_MULTIPLIER_AIR_STRIKE = 0.9F;
    public static final float SIDE_SPEED_MULTIPLIER_AIR_STRIKE = 0.18F;


    private CyclingInterpolatedValue rayparticlesvalue = new CyclingInterpolatedValue(0,RAY_LENGTH,100);
    private int currentCrystals = 0;
    private int maxShieldingCrystalsCount;
    private static EntityDataAccessor<Boolean> CHARGING_UP = SynchedEntityData.defineId(CrystalBossEntity.class, EntityDataSerializers.BOOLEAN);
    private static EntityDataAccessor<Boolean> GET_OFF_ME = SynchedEntityData.defineId(CrystalBossEntity.class, EntityDataSerializers.BOOLEAN);
    public static EntityDataAccessor<Float> RAY_STATE_FLOAT_OR_ANGLE = SynchedEntityData.defineId(CrystalBossEntity.class,EntityDataSerializers.FLOAT);

    public int clientGetOffMeTicker = 0;
    public static final int RAY_STOPPED = -3;
    public static final int RAY_NOT_ACTIVE = -1;
    public static final int RAY_PREPARING = -2;

//    private ServerBossEvent CRYSTAL_BOSS_EVENT = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_20);
    private CustomServerBossEvent CRYSTAL_BOSS_EVENT = new CustomServerBossEvent(this.getDisplayName(),"defense_crystal");
    private final BossAttackChain ATTACK_CHAIN = new BossAttackChain.Builder()
            .addAttack("missiles",this::holdingMissilesAttack,60,10,1)
            .addAttack("mines",this::spawnMines,200,20,2)
            .addAttack("air_strike",this::airStrike,200,10,2)
            .addAttack("shielding_crystals",this::spawnShieldingCrystals,40,null,2)
            .addAttack("ray_attack",this::rayAttack,700,1,3)
            .addAttack("random_effects",this::throwRandomEffects,300,10,3)
            .addAttack("charge_up",this::chargeUp,160,10,4)
            .addAttack("throw_rrg",this::throwRipRayGenerators,40,null,1)
            .addPostEffectToAttack("charge_up",this::chargeUpPost)
            .addPostEffectToAttack("ray_attack",this::rayAttackPost)
            .addAftermathAttack(this::getOffMEEE)
            .setTimeBetweenAttacks(20)
            .build();
    private int ticker = 0;
    public List<ShieldingCrystalCrystalBoss> entitiesAroundClient = null;
    private int rayPreparingTicks = -1;

    public CrystalBossEntity(EntityType<? extends Mob> p_21368_, Level level) {
        super(p_21368_, level);
        switch (level.getDifficulty()){
            case PEACEFUL -> maxShieldingCrystalsCount = 15;
            case EASY -> maxShieldingCrystalsCount = 30;
            case NORMAL -> maxShieldingCrystalsCount = 45;
            case HARD -> maxShieldingCrystalsCount = 60;
        }
    }

    

    @Override
    public void tick() {
        preventFlying();
        super.tick();
        if (!level.isClientSide){
            if (this.entityData.get(GET_OFF_ME)){
                this.entityData.set(GET_OFF_ME,false);
            }
            ticker++;

            if (this.hasEnemiesNearby(false)) {
                ATTACK_CHAIN.tick();
            }else{
                if (level.getGameTime() % 20 == 0) {
                    if (this.tickCount % 20 == 0) {
                        this.heal(20);
                    }
                    getAlliesNearby().forEach((ally) -> {
                        ally.heal(20);
                    });
                }
            }
            if (this.isDeadOrDying()){
                this.getEnemiesNearby(false).forEach((player -> Helpers.fireProgressionEvent(player, Progression.KILL_CRYSTAL_BOSS)));
            }
        }
        if (level.isClientSide){
            rayparticlesvalue.tick();
            this.entitiesAroundClient = level.getEntitiesOfClass(ShieldingCrystalCrystalBoss.class,
                    new AABB(-16,-16,-16,16,16,16).move(position()),(cr)->{
                        return (cr.distanceTo(this) <= 16 ) ;
                    });
            rayAttackParticles();
            setGetOffMeClient();
            if (this.entityData.get(CHARGING_UP)){
                chargingUpClient();
            }
        }

    }

    private void chargeUp(){
        boolean bool = this.entityData.get(CHARGING_UP);
        if (!bool){
            this.entityData.set(CHARGING_UP,true);
        }
    }

    private void chargeUpPost(){
        this.entityData.set(CHARGING_UP,false);
    }

    public void throwRandomEffects(){
        for (int i = 0 ; i < 5; i ++){
            Vec3 vec = new Vec3(16,0,0)
                    .zRot((float)Math.toRadians(45 + (level.random.nextInt(70)-35)))
                    .yRot((float)Math.toRadians(level.random.nextInt(360)))
                    .normalize();
            RandomBadEffectProjectile proj = new RandomBadEffectProjectile(this.position().x,this.position().y + this.getBbHeight()/2+0.3f,this.position().z,
                    vec.x,vec.y,vec.z,level);
            level.addFreshEntity(proj);
        }
    }

    private void chargingUpClient(){
        for (int i = 0;i < 5;i++) {
            Vec3 vec = Helpers.randomVector().multiply(3,3,3);
            level.addParticle(SolarcraftParticleTypes.SOLAR_EXPLOSION_PARTICLE.get(),
                    this.position().x + vec.x,this.position().y + vec.y + this.getBbHeight()/2,this.position().z + vec.z,
                    -vec.x*0.05,-vec.y*0.05,-vec.z*0.05);
        }
    }

    private void setGetOffMeClient(){
        boolean flagData = this.entityData.get(GET_OFF_ME);
        boolean flagTicker = clientGetOffMeTicker > 0;
        if (flagData && !flagTicker){
            this.clientGetOffMeTicker = 1;
        }else if (!flagData && flagTicker){
            this.clientGetOffMeTicker+=4;
            if (clientGetOffMeTicker > 33){
                this.clientGetOffMeTicker = 0;
            }
        }
    }



    public void getOffMEEE(){
        level.getEntitiesOfClass(LivingEntity.class,
                new AABB(-5,-5,-5,5,5,5).move(position().add(0,this.getBbHeight()/2,0)),
                (ent)-> !(ent instanceof CrystalBossBuddy)).forEach((entity)->{
                    Vec3 speed = entity.position().add(0,entity.getBbHeight()/2,0).subtract(this.position()).normalize().add(0,0.3,0);
                    if (entity instanceof ServerPlayer player){
                        Helpers.setServerPlayerSpeed(player,speed);
                    }else{
                        entity.setDeltaMovement(speed);
                    }
        });
        this.entityData.set(GET_OFF_ME,true);
    }

    public boolean isBlockingDamage(){
        return !(level.getEntitiesOfClass(ShieldingCrystalCrystalBoss.class,
                new AABB(-16,-16,-16,16,16,16).move(position()),(cr)->{
                    return (cr.distanceTo(this) <= 16 ) && !cr.isDeploying();
                }).isEmpty());
    }


    public void rayAttackPost(){
        this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE,(float)RAY_NOT_ACTIVE);
    }

    public void rayAttack(){
        float state = this.entityData.get(RAY_STATE_FLOAT_OR_ANGLE);
        int rounded = Math.round(state);
        if (rounded != RAY_STOPPED) {
            if (rounded == RAY_NOT_ACTIVE) {
                this.rayPreparingTicks = 60;
                this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE, (float) RAY_PREPARING);
            } else if (rounded == RAY_PREPARING) {
                if (this.rayPreparingTicks-- <= 0) {
                    this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE, 0f);
                }
            } else {
                Vec3 firstPos = this.position().add(0, 0.5, 0);
                Vec3 secondPos = firstPos.add(new Vec3(RAY_LENGTH, 0, 0).yRot((float) Math.toRadians(state)));
                EntityHitResult res = Helpers.getHitResult(level, firstPos, secondPos, (entity -> {
                    return Helpers.isVulnerable(entity) && !(entity instanceof CrystalBossBuddy);
                }));
                if (res != null) {
                    Entity ent = res.getEntity();
                    ent.hurt(SolarcraftDamageSources.livingArmorPierce(this), RAY_DAMAGE);
                }

                secondPos = firstPos.add(new Vec3(RAY_LENGTH, 0, 0).yRot((float) Math.toRadians(state+180)));
                res = Helpers.getHitResult(level, firstPos, secondPos,  (entity -> {
                    return Helpers.isVulnerable(entity) && !(entity instanceof CrystalBossBuddy);
                }));
                if (res != null) {
                    Entity ent = res.getEntity();
                    ent.hurt(SolarcraftDamageSources.livingArmorPierce(this), RAY_DAMAGE);
                }
                if (state <= 1300) {
                    this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE, state + 2f);
                } else {
                    this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE, (float) RAY_STOPPED);
                }

            }
        }
    }

    private void rayAttackParticles(){

        float state = this.entityData.get(RAY_STATE_FLOAT_OR_ANGLE);
        int rounded = Math.round(state);
        if (rounded != RAY_STOPPED){
            if (rounded != RAY_NOT_ACTIVE){
                if (rounded == RAY_PREPARING){

                    double[] coords = FDMathHelper.polarToCartesian(0.4,Math.toRadians(level.getGameTime()*30));
                    level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                            this.position().x+
                                    rayparticlesvalue.getValue(),
                            this.position().y+1.6+coords[0],
                            this.position().z+coords[1],
                            0,0,0);
                    level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                            this.position().x-
                                    rayparticlesvalue.getValue(),
                            this.position().y+1.6+coords[0],
                            this.position().z+coords[1],
                            0,0,0);
                }else{
                    float firstangle = (float)Math.toRadians(state);
                    float secondangle = (float)Math.toRadians(state+180);
                    double[] coords = FDMathHelper.polarToCartesian(0.4,Math.toRadians(level.getGameTime()*30));
                    Vec3 vec1 = new Vec3(rayparticlesvalue.getValue(),0,coords[0]).yRot(firstangle);
                    Vec3 vec2 = new Vec3(rayparticlesvalue.getValue(),0,coords[0]).yRot(secondangle);

                    level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                                this.position().x +vec1.x,
                                this.position().y+1.6+coords[1],
                                this.position().z + vec1.z,
                                0,0,0);

                    level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                                this.position().x  +vec2.x,
                                this.position().y+1.6 +coords[1],
                                this.position().z  + vec2.z,
                                0,0,0);

                }
            }
        }
    }



    public void airStrike(){
        for (int i = 0;i < 6;i++){
            double x = (level.random.nextDouble()*SIDE_SPEED_MULTIPLIER_AIR_STRIKE+0.01)* FDMathHelper.randomPlusMinus();
            double z = (level.random.nextDouble()*SIDE_SPEED_MULTIPLIER_AIR_STRIKE+0.01)* FDMathHelper.randomPlusMinus();
            MagicMissile star = new MagicMissile(level,x,UP_SPEED_MULTIPLIER_AIR_STRIKE,z);
            star.setPos(this.position().add(0,this.getBbHeight()*0.7,0));
            level.addFreshEntity(star);
        }
    }

    public void throwRipRayGenerators(){
        for (int i = 0; i < 4;i++){
            Vec3 vec = Helpers.randomVector().multiply(2+level.random.nextDouble()*9,0,1+2+level.random.nextDouble()*9);
            RipRayGenerator gen = new RipRayGenerator(SolarcraftEntityTypes.RIP_RAY_GENERATOR.get(),level);
            gen.setPos(this.position().add(vec.x,this.getBbHeight()/2,vec.z));

            level.addFreshEntity(gen);
        }
    }

    public void spawnMines(){
        List<Vec3> positions = Helpers.findRandomGroundPositionsAround(level,position(),20,15);
        for (Vec3 pos : positions){
            MineEntityCrystalBoss mine = new MineEntityCrystalBoss(SolarcraftEntityTypes.CRYSTAL_BOSS_MINE.get(),level);
            mine.setPos(pos);
            level.addFreshEntity(mine);
        }
    }

    public void spawnShieldingCrystals(){
        if (currentCrystals < maxShieldingCrystalsCount) {
            List<ShieldingCrystalCrystalBoss> crystals = this.level.getEntitiesOfClass(ShieldingCrystalCrystalBoss.class, new AABB(-10, -10, -10, 10, 10, 10).move(position()));
            for (int i = 0; i < 4; i++) {
                if (crystals.size() + i > 8) {
                    break;
                }
                ShieldingCrystalCrystalBoss crystal = new ShieldingCrystalCrystalBoss(SolarcraftEntityTypes.CRYSTAL_BOSS_SHIELDING_CRYSTAL.get(), level);
                Vec3 positon = this.position().add((level.random.nextDouble() * 4.5 + 3) * FDMathHelper.randomPlusMinus(), 0, (level.random.nextDouble() * 4.5 + 3) * FDMathHelper.randomPlusMinus());
                crystal.setPos(positon);
                level.addFreshEntity(crystal);
                currentCrystals++;

            }
        }
    }


    public void holdingMissilesAttack(){
        List<Player> possibleTargets = level.getEntitiesOfClass(Player.class,new AABB(this.position().add(-20,-8,-20),this.position().add(20,8,20)),
                (pl)->{
                    return !pl.isCreative() && !pl.isSpectator();
                });
        if (!possibleTargets.isEmpty()){
            float timeOffset = 0;
            for (int i = 0; i < 7*possibleTargets.size();i++){
                Vec3 vec= Helpers.randomVector();

                CrystalBossAttackHoldingMissile missile = new CrystalBossAttackHoldingMissile.Builder(level)
                        .setHoldingTime(1+timeOffset)
                        .setTarget(possibleTargets.get(level.random.nextInt(possibleTargets.size())).getUUID())
                        .setInitialSpeed(vec.multiply(1,0.2,1).normalize())
                        .setPosition(this.position().add(0,this.getBbHeight()/2,0))
                        .build();
                level.addFreshEntity(missile);
                timeOffset+=0.1;
            }
        }
    }

    public boolean hasEnemiesNearby(boolean includeCreative){
        return !level.getEntitiesOfClass(Player.class,new AABB(this.position().add(-13,-8,-13),this.position().add(14,8,14)),
                (pl)->{
                    return (!pl.isCreative() || includeCreative) && !pl.isSpectator() && (this.distanceTo(pl) <= 13);
                }).isEmpty();
    }

    public List<Player> getEnemiesNearby(boolean includeCreative){
        return level.getEntitiesOfClass(Player.class,new AABB(this.position().add(-13,-8,-13),this.position().add(14,8,14)),
                (pl)->{
                    return (!pl.isCreative() || includeCreative) && !pl.isSpectator() && (this.distanceTo(pl) <= 13);
                });
    }


    public List<LivingEntity> getAlliesNearby(){
        return level.getEntitiesOfClass(LivingEntity.class,new AABB(this.position().add(-13,-8,-13),this.position().add(14,8,14)),
                (pl)->{
            return pl instanceof CrystalBossBuddy;
                });
    }

    public static AttributeSupplier.Builder createAttributes(){
        return NoHealthLimitMob.createEntityAttributes()
                .add(AttributesRegistry.MAXIMUM_HEALTH_NO_LIMIT.get(),2500)
                .add(Attributes.ARMOR,10)
                .add(AttributesRegistry.MAGIC_RESISTANCE.get(),20);
    }

    @Override
    public void setHealth(float p_21154_) {
        super.setHealth(p_21154_);
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean save(CompoundTag cmp) {
        ATTACK_CHAIN.save(cmp);
        float state = this.entityData.get(RAY_STATE_FLOAT_OR_ANGLE);
        cmp.putFloat("ray_state",state);
        cmp.putInt("ticker",ticker);
        cmp.putInt("ray_preparing",rayPreparingTicks);
        cmp.putInt("crystals",currentCrystals);
        return super.save(cmp);
    }

    @Override
    public void load(CompoundTag cmp) {
        ATTACK_CHAIN.load(cmp);
        currentCrystals = cmp.getInt("crystals");
        this.ticker = cmp.getInt("ticker");
        this.rayPreparingTicks = cmp.getInt("ray_preparing");
        float c = cmp.getFloat("ray_state");
        if (((c < 1) && (c > -0.1) && (this.tickCount < 5))) {
            this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE, (float)RAY_NOT_ACTIVE);
        }else{
            this.entityData.set(RAY_STATE_FLOAT_OR_ANGLE,c);
        }
        super.load(cmp);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RAY_STATE_FLOAT_OR_ANGLE,(float)RAY_NOT_ACTIVE);
        this.entityData.define(GET_OFF_ME,false);
        this.entityData.define(CHARGING_UP,false);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer p_20119_) {
        super.startSeenByPlayer(p_20119_);
        CRYSTAL_BOSS_EVENT.addPlayer(p_20119_);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer p_20174_) {
        super.stopSeenByPlayer(p_20174_);
        CRYSTAL_BOSS_EVENT.removePlayer(p_20174_);
    }
    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }



    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        return super.hurt(p_21016_, p_21017_);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        CRYSTAL_BOSS_EVENT.setProgress(this.getHealth()/this.getMaxHealth());
    }

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {}


    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SolarcraftSounds.CRYSTAL_HIT.get();
    }

    @Override
    protected void doPush(Entity entity) {
        entity.setDeltaMovement(entity.position().add(0,entity.getBbHeight()/2,0).subtract(this.position().add(0,this.getBbHeight()/2,0)).normalize());
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


    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SolarcraftSounds.CRYSTAL_HIT.get();
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
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return true;
    }

    private void preventFlying(){
        if (this.level.getGameTime() % 5 == 0) {
            for (Player pl : this.getEnemiesNearby(false)){
                Abilities ab = pl.getAbilities();
                if (ab.mayfly){
                    ab.mayfly = false;
                }
                if (ab.flying){
                    ab.flying = false;
                }
            }
        }
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    protected void dropAllDeathLoot(DamageSource p_21192_) {
        super.dropAllDeathLoot(p_21192_);
        LegendaryItem item = new LegendaryItem(level, new ItemStack(SolarcraftItems.CRYSTALLITE_CORE.get(),1));
        item.setPos(this.position().add(0,this.getBbHeight()/2,0));
        level.addFreshEntity(item);
    }
}

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
class CancelAttack{

    @SubscribeEvent
    public static void cancelCrystalBossAttack(LivingHurtEvent event){
        if (event.getEntity() instanceof CrystalBossEntity boss){
            if (boss.isBlockingDamage() && boss.hasEnemiesNearby(true)){
                event.setCanceled(true);
            }
        }else if (event.getEntity() instanceof ShieldingCrystalCrystalBoss shield){
            if (shield.isDeploying()){
                event.setCanceled(true);
            }
        }

    }
}

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
class AntiCheat{

    private static final AABB CHECK_AABB = new AABB(-30,-256,-30,30,256,30);
    private static final AABB CHECK_AABB_BIGGER = new AABB(-100,-256,-100,100,256,100);
    @SubscribeEvent
    public static void cancelBlockBreak(BlockEvent.BreakEvent event){
        Player pl = event.getPlayer();
        if (pl.level.dimension()  == EventHandler.RADIANT_LAND_KEY){
            if (!pl.level.getEntitiesOfClass(LivingEntity.class,CHECK_AABB.move(pl.position()),
                    (l)-> l instanceof CrystalBossEntity || l instanceof RunicElementalBoss).isEmpty()){
                pl.sendSystemMessage(Component.translatable("player.boss_cant_break_block").withStyle(ChatFormatting.RED));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void cancelBlockPlace(BlockEvent.EntityPlaceEvent event){
        Entity pl = event.getEntity();
        if (pl != null) {
            if (pl.level.dimension() == EventHandler.RADIANT_LAND_KEY) {
                if (!pl.level.getEntitiesOfClass(LivingEntity.class, CHECK_AABB.move(pl.position()),
                        (l)-> l instanceof CrystalBossEntity || l instanceof RunicElementalBoss).isEmpty()) {
                    pl.sendSystemMessage(Component.translatable("player.boss_cant_place_block").withStyle(ChatFormatting.RED));
                    event.setCanceled(true);
                }
            }
        }
    }
    @SubscribeEvent
    public static void cancelAbilities(AbilityUseEvent event){
        ServerPlayer player = event.getPlayer();
        AbstractAbility ability = event.getAbility();
        if ((player.level.dimension() == EventHandler.RADIANT_LAND_KEY) && EventHandler.ALLOWED_ABILITIES_DURING_BOSSFIGHT.contains(ability)) {
            if (!player.level.getEntitiesOfClass(LivingEntity.class, CHECK_AABB_BIGGER.move(player.position()),
                    (l)-> l instanceof CrystalBossEntity || l instanceof RunicElementalBoss).isEmpty()) {
                player.sendSystemMessage(Component.translatable("player.cant_use_ability_near_boss").withStyle(ChatFormatting.RED));
                event.setCanceled(true);
            }
        }
    }


    @SubscribeEvent
    public static void cancelExplosions(ExplosionEvent.Detonate event){

            if (event.getLevel().dimension() == EventHandler.RADIANT_LAND_KEY) {
                if (!event.getLevel().getEntitiesOfClass(LivingEntity.class, CHECK_AABB.move(event.getExplosion().getPosition()),
                        (l)-> l instanceof CrystalBossEntity || l instanceof RunicElementalBoss).isEmpty()) {

//                    if (ent != null) {
//                        ent.sendSystemMessage(Component.translatable("player.boss_cant_explode_blocks").withStyle(ChatFormatting.RED), ent.getUUID());
//
//                    }
                    event.getExplosion().getToBlow().clear();
                }
            }

    }



}
