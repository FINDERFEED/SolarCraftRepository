package com.finderfeed.solarforge.registries.entities;


import com.finderfeed.solarforge.entities.*;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarforge.magic_items.items.projectiles.BlockBoomerangProjectile;
import com.finderfeed.solarforge.magic_items.items.projectiles.CrystalBossAttackHoldingMissile;
import com.finderfeed.solarforge.magic_items.items.projectiles.FallingStarCrystalBoss;
import com.finderfeed.solarforge.magic_items.items.projectiles.UltraCrossbowProjectile;
import com.finderfeed.solarforge.magic_items.items.solar_disc_gun.SolarDiscProjectile;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Entities {

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

    public static final RegistryObject<EntityType<AbstractTurretProjectile>> TURRET_PROJECTILE = ENTITY_TYPE_REGISTER.register("turret_projectile", () ->
            EntityType.Builder.<AbstractTurretProjectile>of(AbstractTurretProjectile::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).build("turret_projectile"));

    public static final RegistryObject<EntityType<VillagerSolarMaster>> VILLAGER_SOLAR_MASTER = ENTITY_TYPE_REGISTER.register("solar_master_villager",()->
            EntityType.Builder.of(VillagerSolarMaster::new,MobCategory.CREATURE).sized(1,2).build("solar_master_villager"));

    public static final RegistryObject<EntityType<MyFallingBlockEntity>> FALLING_BLOCK = ENTITY_TYPE_REGISTER.register("my_falling_block",()->
            EntityType.Builder.<MyFallingBlockEntity>of(MyFallingBlockEntity::new,MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(20).build("my_falling_block"));

    public static final RegistryObject<EntityType<CrystalBossEntity>> CRYSTAL_BOSS = ENTITY_TYPE_REGISTER.register("crystal_boss",()->
            EntityType.Builder.of(CrystalBossEntity::new,MobCategory.CREATURE).sized(2,6).build("crystal_boss"));

    public static final RegistryObject<EntityType<CrystalBossAttackHoldingMissile>> CRYSTAL_BOSS_ATTACK_HOLDING_MISSILE = ENTITY_TYPE_REGISTER.register("holding_missile", () ->
            EntityType.Builder.<CrystalBossAttackHoldingMissile>of(CrystalBossAttackHoldingMissile::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).updateInterval(5).build("holding_missile"));

    public static final RegistryObject<EntityType<ShieldingCrystalCrystalBoss>> CRYSTAL_BOSS_SHIELDING_CRYSTAL = ENTITY_TYPE_REGISTER.register("crystal_boss_shielding_crystal",()->
            EntityType.Builder.of(ShieldingCrystalCrystalBoss::new,MobCategory.CREATURE).sized(1,2).build("crystal_boss_shielding_crystal"));

    public static final RegistryObject<EntityType<MineEntityCrystalBoss>> CRYSTAL_BOSS_MINE = ENTITY_TYPE_REGISTER.register("crystal_boss_mine",()->
            EntityType.Builder.of(MineEntityCrystalBoss::new,MobCategory.CREATURE).sized(1,0.3f).build("crystal_boss_mine"));

    public static final RegistryObject<EntityType<FallingStarCrystalBoss>> FALLING_STAR_CRYSTAL_BOSS = ENTITY_TYPE_REGISTER.register("falling_star", () ->
            EntityType.Builder.<FallingStarCrystalBoss>of(FallingStarCrystalBoss::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).updateInterval(5).build("falling_star"));
}