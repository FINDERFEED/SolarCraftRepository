package com.finderfeed.solarforge.registries.tile_entities;

import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool.InfusingPoolTileEntity;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.*;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities.ColdStarInfuserTile;
import com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities.KeyDefenderTile;
import com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities.KeyLockStructureTile;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntitiesRegistry {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,"solarforge");



    public static final RegistryObject<BlockEntityType<InfusingPoolTileEntity>> INFUSING_POOL_BLOCKENTITY = TILE_ENTITY_TYPE.register("infusing_pool_blockentity",()->
            BlockEntityType.Builder.of(InfusingPoolTileEntity::new, BlocksRegistry.SOLAR_POOL.get()).build(null));

    public static final RegistryObject<BlockEntityType<KeyLockStructureTile>> KEY_LOCK_TILE = TILE_ENTITY_TYPE.register("key_lock_tile",()->
            BlockEntityType.Builder.of(KeyLockStructureTile::new, BlocksRegistry.KEY_LOCK_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ColdStarInfuserTile>> COLD_STAR_INFUSER = TILE_ENTITY_TYPE.register("cold_star_infuser_tile",()->
            BlockEntityType.Builder.of(ColdStarInfuserTile::new, BlocksRegistry.COLD_STAR_INFUSER.get()).build(null));

    public static final RegistryObject<BlockEntityType<KeyDefenderTile>> KEY_DEFENDER_TILE = TILE_ENTITY_TYPE.register("key_defender_tile",()->
            BlockEntityType.Builder.of(KeyDefenderTile::new, BlocksRegistry.KEY_DEFENDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SolarLensTile>> SOLAR_LENS_TILE = TILE_ENTITY_TYPE.register("solar_lens_tile",()->
            BlockEntityType.Builder.of(SolarLensTile::new, BlocksRegistry.SOLAR_LENS.get()).build(null));

    public static final RegistryObject<BlockEntityType<MagnetBlockTile>> MAGNET_BLOCK_TILE = TILE_ENTITY_TYPE.register("magnet_block_tile",()->
            BlockEntityType.Builder.of(MagnetBlockTile::new, BlocksRegistry.MAGNET_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnergyGeneratorTile>> ENERGY_GENERATOR_TILE = TILE_ENTITY_TYPE.register("energy_generator_tile",()->
            BlockEntityType.Builder.of(EnergyGeneratorTile::new, BlocksRegistry.SOLAR_ENERGY_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<SolarEnergyRepeaterBlockEntity>> SOLAR_REPEATER = TILE_ENTITY_TYPE.register("energy_repeater_tile",()->
            BlockEntityType.Builder.of(SolarEnergyRepeaterBlockEntity::new, BlocksRegistry.SOLAR_REPEATER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SolarCoreBlockEntity>> SOLAR_CORE_TILE = TILE_ENTITY_TYPE.register("energy_core_tile",()->
            BlockEntityType.Builder.of(SolarCoreBlockEntity::new, BlocksRegistry.SOLAR_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<AuraHealerTile>> AURA_HEALER_TILE = TILE_ENTITY_TYPE.register("aura_healer_tile",()->
            BlockEntityType.Builder.of(AuraHealerTile::new, BlocksRegistry.AURA_HEALER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MortarTileEntity>> MORTAR_TILE_ENTITY = TILE_ENTITY_TYPE.register("mortar_tile",()->
            BlockEntityType.Builder.of(MortarTileEntity::new, BlocksRegistry.SOLAR_MORTAR_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SolarEnergyFurnaceTile>> SOLAR_FURNACE_TILE_ENTITY = TILE_ENTITY_TYPE.register("solar_furnace",()->
            BlockEntityType.Builder.of(SolarEnergyFurnaceTile::new, BlocksRegistry.SOLAR_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<TurretTileEntity>> TURRET_TILE_ENTITY = TILE_ENTITY_TYPE.register("turret_tile_entity",()->
            BlockEntityType.Builder.of(TurretTileEntity::new, BlocksRegistry.TURRET_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<RayTrapTileEntity>> RAY_TRAP_TILE_ENTITY = TILE_ENTITY_TYPE.register("ray_trap_tile_entity",()->
            BlockEntityType.Builder.of(RayTrapTileEntity::new, BlocksRegistry.RAY_TRAP_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<TrapControllerTile>> TRAP_STRUCT_CONTROLLER = TILE_ENTITY_TYPE.register("trap_controller_tile",()->
            BlockEntityType.Builder.of(TrapControllerTile::new, BlocksRegistry.TRAP_CONTROLLER.get()).build(null));

    public static final RegistryObject<BlockEntityType<RunicTableTileEntity>> RUNIC_TABLE_TILE = TILE_ENTITY_TYPE.register("runic_table_tile",()->
            BlockEntityType.Builder.of(RunicTableTileEntity::new, BlocksRegistry.RUNIC_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<RuneEnergyPylonTile>> RUNE_ENERGY_PYLON = TILE_ENTITY_TYPE.register("rune_energy_pylon",()->
            BlockEntityType.Builder.of(RuneEnergyPylonTile::new, BlocksRegistry.RUNE_ENERGY_PYLON.get()).build(null));

    public static final RegistryObject<BlockEntityType<BaseRepeaterTile>> REPEATER = TILE_ENTITY_TYPE.register("repeater",()->
            BlockEntityType.Builder.of(BaseRepeaterTile::new, BlocksRegistry.REPEATER.get()).build(null));

    public static final RegistryObject<BlockEntityType<RadiantPortalTile>> PORTAL = TILE_ENTITY_TYPE.register("portal",()->
            BlockEntityType.Builder.of(RadiantPortalTile::new, BlocksRegistry.RADIANT_LAND_PORTAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<RadiantPortalCreatorTile>> PORTAL_CREATOR = TILE_ENTITY_TYPE.register("portal_creator",()->
            BlockEntityType.Builder.of(RadiantPortalCreatorTile::new, BlocksRegistry.RADIANT_LAND_PORTAL_CREATOR.get()).build(null));

}
