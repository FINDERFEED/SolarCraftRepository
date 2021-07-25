package com.finderfeed.solarforge.registries.projectiles;


import com.finderfeed.solarforge.entities.VillagerSolarMaster;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarforge.magic_items.items.projectiles.BlockBoomerangProjectile;
import com.finderfeed.solarforge.magic_items.items.projectiles.UltraCrossbowProjectile;
import com.finderfeed.solarforge.magic_items.items.solar_disc_gun.SolarDiscProjectile;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Projectiles {

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
}