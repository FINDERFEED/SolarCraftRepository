package com.finderfeed.solarforge.registries.entities;


import com.finderfeed.solarforge.entities.*;
import com.finderfeed.solarforge.entities.not_alive.*;
import com.finderfeed.solarforge.magic.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarforge.magic.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarforge.magic.blocks.blockentities.projectiles.ShadowBolt;
import com.finderfeed.solarforge.magic.projectiles.*;
import com.finderfeed.solarforge.magic.items.solar_disc_gun.SolarDiscProjectile;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, "solarforge");
    public static final RegistryObject<EntityType<SolarDiscProjectile>> SOLAR_DISC = ENTITY_TYPE_REGISTER.register("solar_forge_solar_disc", () ->
            EntityType.Builder.<SolarDiscProjectile>of(SolarDiscProjectile::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).build("solar_forge_solar_disc"));

    public static final RegistryObject<EntityType<BlockBoomerangProjectile>> BLOCK_BOOMERANG = ENTITY_TYPE_REGISTER.register("block_boomerang_projectile", () ->
            EntityType.Builder.<BlockBoomerangProjectile>of(BlockBoomerangProjectile::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).build("block_boomerang_projectile"));

    public static final RegistryObject<EntityType<UltraCrossbowProjectile>> ULTRA_CROSSBOW_SHOT = ENTITY_TYPE_REGISTER.register("ultra_crossbow_projectile", () ->
            EntityType.Builder.<UltraCrossbowProjectile>of(UltraCrossbowProjectile::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).build("ultra_crossbow_projectile"));

    public static final RegistryObject<EntityType<MortarProjectile>> MORTAR_PROJECTILE = ENTITY_TYPE_REGISTER.register("mortar_projectile", () ->
            EntityType.Builder.<MortarProjectile>of(MortarProjectile::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).build("mortar_projectile"));

    public static final RegistryObject<EntityType<BallLightningProjectile>> BALL_LIGHTNING = ENTITY_TYPE_REGISTER.register("ball_lightning", () ->
            EntityType.Builder.<BallLightningProjectile>of(BallLightningProjectile::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f).build("ball_lightning"));

    public static final RegistryObject<EntityType<AbstractTurretProjectile>> TURRET_PROJECTILE = ENTITY_TYPE_REGISTER.register("turret_projectile", () ->
            EntityType.Builder.<AbstractTurretProjectile>of(AbstractTurretProjectile::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).build("turret_projectile"));

    public static final RegistryObject<EntityType<VillagerSolarMaster>> VILLAGER_SOLAR_MASTER = ENTITY_TYPE_REGISTER.register("solar_master_villager",()->
            EntityType.Builder.of(VillagerSolarMaster::new,MobCategory.CREATURE).sized(1,2).build("solar_master_villager"));

    public static final RegistryObject<EntityType<ShadowZombie>> SHADOW_ZOMBIE = ENTITY_TYPE_REGISTER.register("shadow_zombie",()->
            EntityType.Builder.of(ShadowZombie::new,MobCategory.MONSTER).sized(1,2).build("shadow_zombie"));

    public static final RegistryObject<EntityType<MyFallingBlockEntity>> FALLING_BLOCK = ENTITY_TYPE_REGISTER.register("my_falling_block",()->
            EntityType.Builder.<MyFallingBlockEntity>of(MyFallingBlockEntity::new,MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(20).build("my_falling_block"));

    public static final RegistryObject<EntityType<CrystalBossEntity>> CRYSTAL_BOSS = ENTITY_TYPE_REGISTER.register("crystal_boss",()->
            EntityType.Builder.of(CrystalBossEntity::new,MobCategory.CREATURE).sized(2,6).build("crystal_boss"));

    public static final RegistryObject<EntityType<CrystalBossAttackHoldingMissile>> CRYSTAL_BOSS_ATTACK_HOLDING_MISSILE = ENTITY_TYPE_REGISTER.register("holding_missile", () ->
            EntityType.Builder.<CrystalBossAttackHoldingMissile>of(CrystalBossAttackHoldingMissile::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).updateInterval(5).build("holding_missile"));

    public static final RegistryObject<EntityType<ShieldingCrystalCrystalBoss>> CRYSTAL_BOSS_SHIELDING_CRYSTAL = ENTITY_TYPE_REGISTER.register("crystal_boss_shielding_crystal",()->
            EntityType.Builder.of(ShieldingCrystalCrystalBoss::new,MobCategory.CREATURE).sized(1,2).build("crystal_boss_shielding_crystal"));

    public static final RegistryObject<EntityType<RunicWarrior>> RUNIC_WARRIOR = ENTITY_TYPE_REGISTER.register("runic_warrior",()->
            EntityType.Builder.of(RunicWarrior::new,MobCategory.CREATURE).sized(1,1f).build("runic_warrior"));

    public static final RegistryObject<EntityType<RefractionCrystal>> REFRACTION_CRYSTAL = ENTITY_TYPE_REGISTER.register("refraction_crystal",()->
            EntityType.Builder.of(RefractionCrystal::new,MobCategory.CREATURE).sized(1,2.7f).build("refraction_crystal"));

    public static final RegistryObject<EntityType<ExplosiveCrystal>> EXPLOSIVE_CRYSTAL = ENTITY_TYPE_REGISTER.register("explosive_crystal",()->
            EntityType.Builder.of(ExplosiveCrystal::new,MobCategory.CREATURE).sized(1,2.7f).build("explosive_crystal"));

    public static final RegistryObject<EntityType<MineEntityCrystalBoss>> CRYSTAL_BOSS_MINE = ENTITY_TYPE_REGISTER.register("crystal_boss_mine",()->
            EntityType.Builder.of(MineEntityCrystalBoss::new,MobCategory.CREATURE).sized(1,0.3f).build("crystal_boss_mine"));

    public static final RegistryObject<EntityType<MagicMissile>> FALLING_MAGIC_MISSILE = ENTITY_TYPE_REGISTER.register("falling_star", () ->
            EntityType.Builder.<MagicMissile>of(MagicMissile::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).updateInterval(5).build("falling_star"));

    public static final RegistryObject<EntityType<RandomBadEffectProjectile>> RANDOM_BAD_EFFECT_PROJECTILE = ENTITY_TYPE_REGISTER.register("rnd_effect_proj", () ->
            EntityType.Builder.<RandomBadEffectProjectile>of(RandomBadEffectProjectile::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).updateInterval(5).build("rnd_effect_proj"));

    public static final RegistryObject<EntityType<RipRayGenerator>> RIP_RAY_GENERATOR = ENTITY_TYPE_REGISTER.register("rip_ray_generator", () ->
            EntityType.Builder.<RipRayGenerator>of(RipRayGenerator::new, MobCategory.MISC)
                    .sized(0.8f, 0.45f).updateInterval(5).build("rip_ray_generator"));

    public static final RegistryObject<EntityType<LegendaryItem>> LEGENDARY_ITEM = ENTITY_TYPE_REGISTER.register("legendary_item", () ->
            EntityType.Builder.<LegendaryItem>of(LegendaryItem::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(6).updateInterval(20).build("legendary_item"));

    public static final RegistryObject<EntityType<RunicElementalBoss>> RUNIC_ELEMENTAL_BOSS = ENTITY_TYPE_REGISTER.register("runic_elemental_boss", () ->
            EntityType.Builder.<RunicElementalBoss>of(RunicElementalBoss::new, MobCategory.CREATURE)
                    .sized(1f, 2.5f).build("runic_elemental_boss"));

    public static final RegistryObject<EntityType<SunstrikeEntity>> SUNSTRIKE = ENTITY_TYPE_REGISTER.register("sunstrike", () ->
            EntityType.Builder.<SunstrikeEntity>of(SunstrikeEntity::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).build("sunstrike"));

    public static final RegistryObject<EntityType<SolarFireballProjectile>> SOLAR_FIREBALL = ENTITY_TYPE_REGISTER.register("solar_fireball", () ->
            EntityType.Builder.<SolarFireballProjectile>of(SolarFireballProjectile::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).build("solar_fireball"));

    public static final RegistryObject<EntityType<RunicWarriorSummoningRocket>> RUNIC_WARRIOR_ROCKET = ENTITY_TYPE_REGISTER.register("runic_warrior_rocket", () ->
            EntityType.Builder.<RunicWarriorSummoningRocket>of(RunicWarriorSummoningRocket::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).build("runic_warrior_rocket"));

    public static final RegistryObject<EntityType<EarthquakeEntity>> EARTHQUAKE = ENTITY_TYPE_REGISTER.register("earthquake", () ->
            EntityType.Builder.<EarthquakeEntity>of(EarthquakeEntity::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).build("earthquake"));

    public static final RegistryObject<EntityType<ShadowBolt>> SHADOW_BOLT = ENTITY_TYPE_REGISTER.register("shadow_bolt", () ->
            EntityType.Builder.<ShadowBolt>of(ShadowBolt::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).build("shadow_bolt"));
}