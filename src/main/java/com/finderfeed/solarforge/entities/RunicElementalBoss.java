package com.finderfeed.solarforge.entities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.local_library.entities.BossAttackChain;
import com.finderfeed.solarforge.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarforge.local_library.other.InterpolatedValue;
import com.finderfeed.solarforge.magic.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.magic.projectiles.MagicMissile;
import com.finderfeed.solarforge.magic.projectiles.RunicWarriorSummoningRocket;
import com.finderfeed.solarforge.misc_things.CrystalBossBuddy;
import com.finderfeed.solarforge.packet_handler.packets.DisablePlayerFlightPacket;
import com.finderfeed.solarforge.packet_handler.packets.TeleportEntityPacket;
import com.finderfeed.solarforge.registries.attributes.AttributesRegistry;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.data_serializers.FDEntityDataSerializers;
import com.finderfeed.solarforge.registries.effects.EffectsRegister;
import com.finderfeed.solarforge.registries.entities.EntityTypes;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunicElementalBoss extends Mob implements CrystalBossBuddy {


    public ServerBossEvent BOSS_INFO = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_20);
    public static final String MAGIC_MISSILES_ATTACK = "magic_missiles";
    private BlockPos summoningPos = null;

    public static final float MISSILES_DAMAGE = 10f;
    public static final float SUNTRIKES_DAMAGE = 20f;
    public static final float EARTHQUAKE_DAMAGE = 30f;
    public static final float VARTH_DADER_DAMAGE = 3f;
    public static final float HAMMER_ATTACK_DAMAGE = 60f;

    private Map<String,InterpolatedValue> ANIMATION_VALUES = new HashMap<>();
    public BossAttackChain BOSS_ATTACK_CHAIN = new BossAttackChain.Builder()
            .addAttack("teleport",this::teleport,5,1,0)
            .addAttack(MAGIC_MISSILES_ATTACK,this::magicMissilesAttack,220,10,3)
            .addAttack("sunstrikes",this::sunstrikes,130,1,2)
            .addAttack("earthquake",this::earthquake,120,30,2)
            .addAttack("vartDader",this::varthDader,120,1,4)
            .addAttack("deployRefractionCrystals",this::deployRefractionCrystals,40,1,1)
            .addAttack("deployExplosiveCrystals",this::deployExplosiveCrystals,40,1,5)
            .addAttack("throwSummoningRockets",this::throwSummoningRockets,23,1,6)
            .addAttack("hammerAttack",this::hammerAttack,41*3,1,7)
            .addPostEffectToAttack("deployRefractionCrystals",this::spawnWarrior)
            .addPostEffectToAttack(MAGIC_MISSILES_ATTACK,this::spawnWarrior)
            .addPostEffectToAttack("earthquake",this::spawnWarrior)
            .addPostEffectToAttack("hammerAttack",()->{
                this.spawnWarrior();
                rotating = false;
            })
            .addPostEffectToAttack("throwSummoningRockets",()->{
                if (summoningPos != null) {
                    Vec3 pos = Helpers.getBlockCenter(summoningPos);
                    this.teleportTo(pos.x,pos.y-0.5,pos.z);
                }
                isWaitingForPlayerToDestroyExplosiveCrystals = true;
            })
            .addAftermathAttack(this::postAttack)
            .setTimeBetweenAttacks(20)
            .build();


    public static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(RunicElementalBoss.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_TYPE = SynchedEntityData.defineId(RunicElementalBoss.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(RunicElementalBoss.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Byte> UPDATE_PUSH_WAVE_TICKER = SynchedEntityData.defineId(RunicElementalBoss.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Boolean> IS_ALREADY_SUMMONED = SynchedEntityData.defineId(RunicElementalBoss.class,EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Vec3> HAMMER_ATTACK_DIRECTION = SynchedEntityData.defineId(RunicElementalBoss.class,
            (EntityDataSerializer<Vec3>)FDEntityDataSerializers.VEC3.get().getSerializer());
    public int summoningTicks = 0;
    public int pushWaveTicker = 0;
    public boolean isWaitingForPlayerToDestroyExplosiveCrystals = false;
    private int seekTargetCooldown = 0;
    private int checkTargetInterval = 0;
    private boolean rotating = false;



    public RunicElementalBoss(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 30.0D).add(Attributes.ATTACK_KNOCKBACK)
                .add(Attributes.MAX_HEALTH,1000).add(Attributes.ARMOR,20).add(AttributesRegistry.MAGIC_RESISTANCE.get(),40);
    }


    @Override
    public void tick() {
        if (summoningTicks > 0){
            summoningTicks--;
        }
        if (seekTargetCooldown > 0){
            seekTargetCooldown--;
        }
        if (checkTargetInterval > 0){
            checkTargetInterval--;
        }
        if (!level.isClientSide){
            if (wasAlreadySummoned() && summoningTicks <= 0) {
                this.bossActivity();
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


            if (this.getAttackType() == AttackType.HAMMER_SWING && getAttackTick() <= 2){
                Vec3 attackVec = this.position().add(getHammerAttackDirection());
                this.lookControl.setLookAt(attackVec.x, attackVec.y + this.getBbHeight()*0.8, attackVec.z, 30, this.getMaxHeadXRot());
            }
        }
        super.tick();
    }

    private void bossActivity(){
        LivingEntity target = getTarget();
        if (target != null){
            if (level.getGameTime() % 10 == 0){
                for (Player player : getPlayersAround(false)){
                    if (player.getAbilities().flying) {
                        DisablePlayerFlightPacket.send((ServerPlayer) player, true);
                    }
                }
            }
            if (isWaitingForPlayerToDestroyExplosiveCrystals && tickCount % 20 == 0){
                isWaitingForPlayerToDestroyExplosiveCrystals = !getExplosiveCrystalsAround().isEmpty();
            }
            if (!isWaitingForPlayerToDestroyExplosiveCrystals && !rotating) {
                BOSS_ATTACK_CHAIN.tick();
                this.setAttackTick(BOSS_ATTACK_CHAIN.getTicker());
            }

            int attackType = getAttackType();
            if (attackType != AttackType.HAMMER_SWING) {
                this.lookControl.setLookAt(target.position().add(0, target.getEyeHeight(target.getPose()) * 0.8, 0));
            }else{
                if (!finishedRotation()) {
                    Vec3 attackVec = this.position().add(getHammerAttackDirection());
                    this.lookControl.setLookAt(attackVec.x, attackVec.y + this.getBbHeight()*0.8, attackVec.z, 30, this.getMaxHeadXRot());
                }else{
                    rotating = false;
                }
            }

        }else{
            if (getAttackTick() != 0 && getAttackType() != 0){
                setAttackTick(0);
                setAttackType(0);
            }
        }

        if (pushWaveTicker > 0){
            pushWaveTicker--;
            if (pushWaveTicker <= 0){
                this.entityData.set(UPDATE_PUSH_WAVE_TICKER,(byte)0);
            }
        }else{
            if (this.entityData.get(UPDATE_PUSH_WAVE_TICKER) == 1){
                pushWaveTicker = 8;
            }
        }

        if (level.getGameTime() % 20 == 0){
            Block b = getBlockBelow();
            if (b == BlocksRegistry.REGENERATION_AMPLIFICATION_BLOCK.get()){
                this.heal(3);
            }else if(b == BlocksRegistry.ARMOR_AMPLIFICATION_BLOCK.get()){
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,100,0));
            }else if (b == BlocksRegistry.EVASION_AMPLIFICATION_BLOCK.get()){
                this.addEffect(new MobEffectInstance(EffectsRegister.EVASION.get(),100,1));
            }
        }
    }

    public void magicMissilesAttack(){
        this.setAttackType(AttackType.MAGIC_MISSILES);
        if (BOSS_ATTACK_CHAIN.getTicker() >= 30 && BOSS_ATTACK_CHAIN.getTicker() < 205) {

            LivingEntity target = getTarget();
            if (target == null) return;
            float speedMod = 1.5f;
            Vec3 between = target.position().add(0,target.getEyeHeight(target.getPose())*0.8,0).subtract(position().add(0, 2, 0));
            MagicMissile missile = new MagicMissile(level,between.normalize().multiply(speedMod,speedMod,speedMod));
            missile.setSpeedDecrement(0);
            float damage = (MISSILES_DAMAGE + getDamageBonus()) * getDamageModifier();
            missile.setDamage(damage);
            missile.setPos(this.position().add(0,2,0).add(between.normalize().multiply(0.5,0.5,0.5)));
            level.addFreshEntity(missile);
            for (RefractionCrystal crystal : getRefractionCrystalsAround()){
                between = target.position().add(0,target.getEyeHeight(target.getPose())*0.8,0).subtract(crystal.position().add(0, crystal.getBbHeight()/2, 0));
                MagicMissile m = new MagicMissile(level,between.normalize().multiply(speedMod,speedMod,speedMod));
                m.setSpeedDecrement(0);
                m.setDamage(damage);
                m.setPos(crystal.position().add(0,crystal.getBbHeight()/2,0).add(between.normalize().multiply(0.5,0.5,0.5)));
                level.addFreshEntity(m);
            }
        }
    }

    public void sunstrikes(){
        this.setAttackType(AttackType.SUNSTRIKES);
        if (BOSS_ATTACK_CHAIN.getTicker() >= 15 && BOSS_ATTACK_CHAIN.getTicker() <= 115){
            if (BOSS_ATTACK_CHAIN.getTicker() % 9 == 0){
                for (Player player : getPlayersAround(false)){
                    SunstrikeEntity sunstrike = new SunstrikeEntity(EntityTypes.SUNSTRIKE.get(),level);
                    Vec3 playerSpeed = player.getLookAngle().multiply(1f,0,1f).normalize().multiply(0.5,0,0.5);
                    float damage = (SUNTRIKES_DAMAGE + getDamageBonus()) * getDamageModifier();
                    sunstrike.setDamage(damage);
                    sunstrike.setPos(player.position().add(playerSpeed));
                    level.addFreshEntity(sunstrike);

                    List<RefractionCrystal> crystals = getRefractionCrystalsAround();
                    if (!crystals.isEmpty()){
                        List<BlockPos> positions = Helpers.getValidSpawningPositionsAround(level,this.getOnPos(),12,2,2);
                        for (RefractionCrystal crystal : crystals){
                            SunstrikeEntity s = new SunstrikeEntity(EntityTypes.SUNSTRIKE.get(),level);
                            s.setDamage(damage);
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
        float damage = (EARTHQUAKE_DAMAGE + getDamageBonus()) * getDamageModifier();
        for (int i = 0; i < 4; i++) {
            Vec3 dir = new Vec3(level.random.nextDouble() * 2 - 1, 0, level.random.nextDouble() * 2 - 1).normalize();
            Vec3 pos = position().add(dir);
            EarthquakeEntity earthquake = new EarthquakeEntity(level, dir, EarthquakeEntity.MAX_LENGTH);
            earthquake.setPos(pos);

            earthquake.setDamage(damage);
            level.addFreshEntity(earthquake);

        }
        for (RefractionCrystal crystal : getRefractionCrystalsAround()){
            for (int i = 0; i < 2 ;i++) {
                Vec3 d = new Vec3(level.random.nextDouble() * 2 - 1, 0, level.random.nextDouble() * 2 - 1).normalize();
                Vec3 p = crystal.position().add(d);
                EarthquakeEntity e = new EarthquakeEntity(level, d, EarthquakeEntity.MAX_LENGTH);
                e.setPos(p);
                e.setDamage(damage);
                level.addFreshEntity(e);
            }
        }
    }



    public void varthDader(){
        this.setAttackType(AttackType.VARTH_DADER);
        if (BOSS_ATTACK_CHAIN.getTicker() > 15 && BOSS_ATTACK_CHAIN.getTicker() < 105) {
            LivingEntity living =  getTarget();
            if (living == null) return;
            this.setVarthDaderTarget(living.getId());
            if (living instanceof Player player1) {
                Helpers.setServerPlayerSpeed((ServerPlayer) living, new Vec3(0, 4 / 90f, 0));
            }else{
                living.setDeltaMovement(new Vec3(0, 4 / 90f, 0));
            }
            if (BOSS_ATTACK_CHAIN.getTicker() % 10 == 0) {
                List<MobEffect> toRemove = new ArrayList<>();
                float damage = (VARTH_DADER_DAMAGE + getDamageBonus()/4f) * getDamageModifier();
                living.hurt(DamageSource.mobAttack(this).setMagic().bypassArmor(), damage);
                for (MobEffectInstance effect : living.getActiveEffects()){
                    if (effect.getEffect().isBeneficial() && effect.getEffect() != EffectsRegister.IMMORTALITY_EFFECT.get()){
                        toRemove.add(effect.getEffect());
                    }
                }
                for (MobEffect effect : toRemove){
                    living.removeEffect(effect);
                }
            }
        }
    }

    public void deployRefractionCrystals(){
        this.setAttackType(AttackType.REFRACTION_CRYSTALS);
        if (BOSS_ATTACK_CHAIN.getTicker() == 30){
            int c =getRefractionCrystalsAround().size();
            if (c >= 4) return;
            List<BlockPos> positions = Helpers.getValidSpawningPositionsAround(level,this.getSummoningPos(),12,2,2);
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

    public void deployExplosiveCrystals(){
        this.setAttackType(AttackType.REFRACTION_CRYSTALS);
        if (BOSS_ATTACK_CHAIN.getTicker() == 30){
            int c =getExplosiveCrystalsAround().size();
            if (c >= 2) return;
            List<BlockPos> positions = Helpers.getValidSpawningPositionsAround(level,this.getSummoningPos(),12,2,2);
            this.removeDuplicatePositions(positions);
            if (!positions.isEmpty()) {
                BlockPos randomPos1 = positions.get(level.random.nextInt(positions.size()));
                ExplosiveCrystal crystal = new ExplosiveCrystal(EntityTypes.EXPLOSIVE_CRYSTAL.get(), level);
                crystal.setPos(Helpers.getBlockCenter(randomPos1.above()).add(0,-0.5,0));
                level.addFreshEntity(crystal);
                if (c == 1) return;
                positions.remove(randomPos1);
                if (!positions.isEmpty()){
                    BlockPos randomPos2 = positions.get(level.random.nextInt(positions.size()));
                    ExplosiveCrystal crystal2 = new ExplosiveCrystal(EntityTypes.EXPLOSIVE_CRYSTAL.get(), level);
                    crystal2.setPos(Helpers.getBlockCenter(randomPos2.above()).add(0,-0.5,0));
                    level.addFreshEntity(crystal2);
                }
            }
        }
    }
    public void throwSummoningRockets(){
        this.setAttackType(AttackType.SUMMONING_ROCKETS);
        if (BOSS_ATTACK_CHAIN.getTicker() == 8) {
            int playersAround = getPlayersAround(false).size();
            for (int i = 0; i < 3 * playersAround; i++) {
                RunicWarriorSummoningRocket rocket = new RunicWarriorSummoningRocket(EntityTypes.RUNIC_WARRIOR_ROCKET.get(),level);
                Vec3 rnd = new Vec3(level.random.nextDouble()*0.5f - 0.25f,0.4f,level.random.nextDouble()*0.5f - 0.25f);
                rocket.setDeltaMovement(rnd);
                rocket.setPos(this.position().add(0,this.getBbHeight()/2,0));
                level.addFreshEntity(rocket);
            }
        }
    }
    public void hammerAttack(){
        this.setAttackType(AttackType.HAMMER_SWING);
        int ticker = BOSS_ATTACK_CHAIN.getTicker() % 42;
        if (ticker == 1){
            rotating = true;
            List<Player> players = getPlayersAround(false);
            Vec3 vec = Helpers.getBlockCenter(players.get(level.random.nextInt(players.size())).getOnPos().above()).subtract(this.position()).multiply(1,0,1).normalize();
            this.setHammerAttackDirection(vec);
        }
        if (ticker == 21){
            for (Player player : getPlayersAround(false)){
                Vec3 vec = player.position().subtract(this.position()).multiply(1,0,1).normalize();
                Vec3 attackDir = getHammerAttackDirection();
                double angleVec = Math.toDegrees(Math.atan2(vec.x,vec.z));
                double attackDirAngle = Math.toDegrees(Math.atan2(attackDir.x,attackDir.z));
                if (Math.abs(attackDirAngle - angleVec) <= 110 && vec.length() <= 16){
                    float damage = (HAMMER_ATTACK_DAMAGE + getDamageBonus()) * getDamageModifier();
                    player.hurt(DamageSource.mobAttack(this),damage);
                }
            }

        }
    }

    public void teleport(){
        if (BOSS_ATTACK_CHAIN.getTicker() == 3) {
            List<BlockPos> positions = getTeleportPositions();
            BlockPos rnd = positions.get(level.random.nextInt(positions.size()));
            this.teleportTo(rnd.getX() + 0.5,rnd.getY(),rnd.getZ() + 0.5);
            TeleportEntityPacket.sendPacket((ServerLevel)level,this,new Vec3(rnd.getX() + 0.5,rnd.getY(),rnd.getZ() + 0.5));
        }
    }

    public List<BlockPos> getTeleportPositions(){
        if (summoningPos == null) return List.of(getOnPos().above());
        ArrayList<BlockPos> p = new ArrayList<>();
        BlockPos initPos = summoningPos;
        p.add(initPos.north(5));
        p.add(initPos.south(5));
        p.add(initPos.west(5));
        p.add(initPos.east(5));
        p.remove(getOnPos());
        return p;
    }

    public Block getBlockBelow(){
        return level.getBlockState(getOnPos()).getBlock();
    }

    public float getDamageBonus(){
        return level.getBlockState(getOnPos()).is(BlocksRegistry.DAMAGE_AMPLIFICATION_BLOCK.get()) ? 10 : 0;
    }

    public float getDamageModifier(){
        return this.getHealth() / this.getMaxHealth() <= 0.5 ? 0.5f : 1f;
    }


    private void removeDuplicatePositions(List<BlockPos> positions){
        for (RefractionCrystal crystal : getRefractionCrystalsAround()){
            positions.remove(crystal.getOnPos());
        }
        for (ExplosiveCrystal crystal : getExplosiveCrystalsAround()){
            positions.remove(crystal.getOnPos());
        }
        positions.remove(getOnPos().above());
    }

    public void setVarthDaderTarget(int eID){
        this.entityData.set(DATA_ID_ATTACK_TARGET,eID);
    }

    public int getVarthDaderTarget(){
        return this.entityData.get(DATA_ID_ATTACK_TARGET);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND && !wasAlreadySummoned()) {
            ItemStack item = player.getItemInHand(hand);
            if (item.is(ItemsRegister.CRYSTALLITE_CORE.get())){
                setSummoned(true);
                setSummoningPos(this.getOnPos().above());
                item.shrink(1);
                player.swing(hand);
            }
        }
        return super.interactAt(player, vec, hand);
    }

    @Override
    protected int getExperienceReward(Player p_21511_) {
        return 200;
    }

    @Override
    protected void doPush(Entity entity) {
        if (!(entity instanceof Player)) return;
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
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {}

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        BOSS_INFO.setProgress(this.getHealth()/this.getMaxHealth());
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer pl) {
        BOSS_INFO.removePlayer(pl);
        super.stopSeenByPlayer(pl);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer pl) {
        BOSS_INFO.addPlayer(pl);
        super.startSeenByPlayer(pl);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() == MobEffects.DAMAGE_RESISTANCE || effect.getEffect() == EffectsRegister.EVASION.get();
    }


    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.CRYSTAL_HIT.get();
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

    public void postAttack(){
        this.setAttackType(0);
        this.setAttackTick(0);
        this.setVarthDaderTarget(0);
        AABB box = new AABB(-3,-3,-3,3,3,3).move(position());
        this.entityData.set(UPDATE_PUSH_WAVE_TICKER,(byte)1);
        for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class,box,(l)->!(l instanceof CrystalBossBuddy))){
            Vec3 speed = position().subtract(living.position()).normalize().reverse().multiply(2,2,2).add(0,0.2,0);
            if (living instanceof Player player){
                Helpers.setServerPlayerSpeed((ServerPlayer) player,speed);
            }else{
                living.setDeltaMovement(speed);
            }
        }
    }
    public void spawnWarrior(){
        if (this.getHealth()/this.getMaxHealth() <= 0.5 && getAttackType() != 0) {
            RunicWarriorSummoningRocket rocket = new RunicWarriorSummoningRocket(EntityTypes.RUNIC_WARRIOR_ROCKET.get(), level);
            Vec3 rnd = new Vec3(level.random.nextDouble() * 0.5f - 0.25f, 0.4f, level.random.nextDouble() * 0.5f - 0.25f);
            rocket.setDeltaMovement(rnd);
            rocket.setPos(this.position().add(0, this.getBbHeight() / 2, 0));
            level.addFreshEntity(rocket);
        }
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

    public Vec3 getHammerAttackDirection(){
        return this.entityData.get(HAMMER_ATTACK_DIRECTION);
    }

    public void setHammerAttackDirection(Vec3 vec){
        this.entityData.set(HAMMER_ATTACK_DIRECTION,vec);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_TICK,0);
        this.entityData.define(ATTACK_TYPE,0);
        this.entityData.define(DATA_ID_ATTACK_TARGET,0);
        this.entityData.define(UPDATE_PUSH_WAVE_TICKER,(byte)0);
        this.entityData.define(IS_ALREADY_SUMMONED,false);
        this.entityData.define(HAMMER_ATTACK_DIRECTION,Vec3.ZERO);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> dataParameter) {
        if (dataParameter == IS_ALREADY_SUMMONED && wasAlreadySummoned()){
            this.summoningTicks = 20;
        }
        super.onSyncedDataUpdated(dataParameter);
    }

    @Override
    protected void registerGoals() {
//        this.targetSelector.addGoal(1,new NearestAttackableTargetGoal<Player>(this,Player.class,30,true,true,(t)->true));
        super.registerGoals();
    }

    public InterpolatedValue getOrCreateAnimationValue(String str, InterpolatedValue value){
        return ANIMATION_VALUES.computeIfAbsent(str,(s)->value);
    }

    @Override
    public boolean save(CompoundTag tag) {
        BOSS_ATTACK_CHAIN.save(tag);
        if (summoningPos != null) {
            CompoundNBTHelper.writeBlockPos("sumPos", summoningPos, tag);
        }
        CompoundNBTHelper.writeVec3("hammerAttack",getHammerAttackDirection(),tag);
        tag.putBoolean("waiting",isWaitingForPlayerToDestroyExplosiveCrystals);
        tag.putBoolean("summoned",wasAlreadySummoned());
        return super.save(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("summoned")){
            setSummoned(tag.getBoolean("summoned"));
        }
        if (tag.contains("hammerAttack1")){
            this.setHammerAttackDirection(CompoundNBTHelper.getVec3("hammerAttack",tag));
        }

        BOSS_ATTACK_CHAIN.load(tag);
        this.isWaitingForPlayerToDestroyExplosiveCrystals = tag.getBoolean("waiting");
        if (tag.contains("sumPos1")) {
            this.summoningPos = CompoundNBTHelper.getBlockPos("sumPos", tag);
        }
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
        return level.getEntitiesOfClass(RefractionCrystal.class,new AABB(-32,-4,-32,16,4,32).move(position()),(c)->{
            return !c.isDeploying();
        });
    }


    private List<ExplosiveCrystal> getExplosiveCrystalsAround(){
        return level.getEntitiesOfClass(ExplosiveCrystal.class,new AABB(-32,-4,-32,32,4,32).move(position()),(c)->{
            return !c.isDeploying();
        });
    }

    private List<Player> getPlayersAround(boolean includeCreative){
        return level.getEntitiesOfClass(Player.class,new AABB(-32,-32,-32,32,32,32).move(position()),(c)->{
            return !c.isSpectator() && (includeCreative || !c.isCreative());
        });
    }


    private List<LivingEntity> getLivingEntitiesAround(){
        return level.getEntitiesOfClass(LivingEntity.class,new AABB(-32,-32,-32,32,32,32).move(position()),(c)->{
            if (c instanceof CrystalBossBuddy) return false;
            if (c instanceof Player pl){
                return !(pl.isSpectator() || pl.isCreative());
            }
            return true;
        });
    }

    public void setSummoningPos(BlockPos summoningPos) {
        this.summoningPos = summoningPos;
    }

    public BlockPos getSummoningPos() {
        if (summoningPos != null){
            Vec3 sumPos = Helpers.getBlockCenter(summoningPos);
            Vec3 onPos = Helpers.getBlockCenter(getOnPos());
            if (sumPos.multiply(1,0,1).subtract(onPos.multiply(1,0,1)).length() < 30){
                return summoningPos;
            }else{
                return getOnPos();
            }
        }else{
            return getOnPos();
        }
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        LivingEntity target = super.getTarget();
        if (target == null) {
            if (this.seekTargetCooldown > 0) return null;
            this.seekTargetCooldown = 20;
            List<Player> players = level.getEntitiesOfClass(Player.class,new AABB(-21,-21,-21,21,21,21).move(this.position()),(pl)->{
                return !pl.isCreative() && !pl.isSpectator() &&
                        pl.position().subtract(this.position()).multiply(1,0,1).length() <= 21
                        && this.getSensing().hasLineOfSight(pl);
            });


            if (players.isEmpty()) return null;
            Player t = players.get(level.random.nextInt(players.size()));
            this.setTarget(t);
            return t;
        }else{
            if (checkTargetInterval > 0) return target;
            checkTargetInterval = 5;
            Vec3 vec = target.position().subtract(this.position()).multiply(1,0,1);
            if (target instanceof Player player && (player.isCreative() || player.isSpectator())) {
                setTarget(null);
                seekTargetCooldown = 20;
                return null;
            }

            if (vec.length() <= 21 && this.getSensing().hasLineOfSight(target) && !target.isDeadOrDying()){
                return target;
            }else{
                this.seekTargetCooldown = 20;
                this.setTarget(null);
                return null;
            }
        }
    }

    public boolean wasAlreadySummoned(){
        return this.entityData.get(IS_ALREADY_SUMMONED);
    }

    public void setSummoned(boolean b){
        this.entityData.set(IS_ALREADY_SUMMONED,b);
    }

    private boolean finishedRotation(){
        Vec3 hammerAttackVec = getHammerAttackDirection();
        double angle = -1*Math.toDegrees(Math.atan2(hammerAttackVec.x,hammerAttackVec.z));
        double otherAngle = yBodyRot % 180;
        double result = Math.abs(Mth.degreesDifference(Math.round(angle),Math.round(otherAngle)));
        return result <= 10 || result >= 170;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return wasAlreadySummoned();
    }

    @Override
    public boolean hurt(DamageSource src, float amount) {
        Entity attacker = src.getEntity();

        if (attacker != null ){
            if (this.isWaitingForPlayerToDestroyExplosiveCrystals) {
                attacker.hurt(DamageSource.MAGIC, 4);
            }
            if (attacker.position().subtract(this.position()).multiply(1,0,1).length() >= 10){
                return false;
            }
        }
        return super.hurt(src, amount);
    }

    @Override
    protected void dropAllDeathLoot(DamageSource src) {
        super.dropAllDeathLoot(src);
        LegendaryItem item = new LegendaryItem(level, new ItemStack(ItemsRegister.CRYSTAL_HEART.get(),1));
        item.setPos(this.position().add(0,this.getBbHeight()/2,0));
        level.addFreshEntity(item);
        if (!level.isClientSide) {
            for (Player player : getPlayersAround(false)) {
                Helpers.fireProgressionEvent(player, Progression.KILL_RUNIC_ELEMENTAL);
            }
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource src) {
        return src.isProjectile();
    }

    public static class AttackType{
        public static final int MAGIC_MISSILES = 1;
        public static final int REFRACTION_CRYSTALS = 2;
        public static final int SUNSTRIKES = 3;
        public static final int EARTHQUAKE = 4;
        public static final int VARTH_DADER = 5;
        public static final int SUMMONING_ROCKETS = 6;
        public static final int HAMMER_SWING = 7;
    }
}