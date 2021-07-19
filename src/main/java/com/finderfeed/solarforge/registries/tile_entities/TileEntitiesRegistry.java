package com.finderfeed.solarforge.registries.tile_entities;

import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool.InfusingPoolTileEntity;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.*;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities.ColdStarInfuserTile;
import com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities.KeyDefenderTile;
import com.finderfeed.solarforge.world_generation.structures.blocks.tile_entities.KeyLockStructureTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntitiesRegistry {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES,"solarforge");



    public static final RegistryObject<TileEntityType<InfusingPoolTileEntity>> INFUSING_POOL_BLOCKENTITY = TILE_ENTITY_TYPE.register("infusing_pool_blockentity",()->
            TileEntityType.Builder.of(InfusingPoolTileEntity::new, BlocksRegistry.SOLAR_POOL.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<KeyLockStructureTile>> KEY_LOCK_TILE = TILE_ENTITY_TYPE.register("key_lock_tile",()->
            TileEntityType.Builder.of(KeyLockStructureTile::new, BlocksRegistry.KEY_LOCK_BLOCK.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<ColdStarInfuserTile>> COLD_STAR_INFUSER = TILE_ENTITY_TYPE.register("cold_star_infuser_tile",()->
            TileEntityType.Builder.of(ColdStarInfuserTile::new, BlocksRegistry.COLD_STAR_INFUSER.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<KeyDefenderTile>> KEY_DEFENDER_TILE = TILE_ENTITY_TYPE.register("key_defender_tile",()->
            TileEntityType.Builder.of(KeyDefenderTile::new, BlocksRegistry.KEY_DEFENDER.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<SolarLensTile>> SOLAR_LENS_TILE = TILE_ENTITY_TYPE.register("solar_lens_tile",()->
            TileEntityType.Builder.of(SolarLensTile::new, BlocksRegistry.SOLAR_LENS.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<MagnetBlockTile>> MAGNET_BLOCK_TILE = TILE_ENTITY_TYPE.register("magnet_block_tile",()->
            TileEntityType.Builder.of(MagnetBlockTile::new, BlocksRegistry.MAGNET_BLOCK.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<EnergyGeneratorTile>> ENERGY_GENERATOR_TILE = TILE_ENTITY_TYPE.register("energy_generator_tile",()->
            TileEntityType.Builder.of(EnergyGeneratorTile::new, BlocksRegistry.SOLAR_ENERGY_GENERATOR.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<SolarEnergyRepeaterBlockEntity>> SOLAR_REPEATER = TILE_ENTITY_TYPE.register("energy_repeater_tile",()->
            TileEntityType.Builder.of(SolarEnergyRepeaterBlockEntity::new, BlocksRegistry.SOLAR_REPEATER.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<SolarCoreBlockEntity>> SOLAR_CORE_TILE = TILE_ENTITY_TYPE.register("energy_core_tile",()->
            TileEntityType.Builder.of(SolarCoreBlockEntity::new, BlocksRegistry.SOLAR_CORE.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<AuraHealerTile>> AURA_HEALER_TILE = TILE_ENTITY_TYPE.register("aura_healer_tile",()->
            TileEntityType.Builder.of(AuraHealerTile::new, BlocksRegistry.AURA_HEALER_BLOCK.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<MortarTileEntity>> MORTAR_TILE_ENTITY = TILE_ENTITY_TYPE.register("mortar_tile",()->
            TileEntityType.Builder.of(MortarTileEntity::new, BlocksRegistry.SOLAR_MORTAR_BLOCK.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<SolarEnergyFurnaceTile>> SOLAR_FURNACE_TILE_ENTITY = TILE_ENTITY_TYPE.register("solar_furnace",()->
            TileEntityType.Builder.of(SolarEnergyFurnaceTile::new, BlocksRegistry.SOLAR_FURNACE.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<TurretTileEntity>> TURRET_TILE_ENTITY = TILE_ENTITY_TYPE.register("turret_tile_entity",()->
            TileEntityType.Builder.of(TurretTileEntity::new, BlocksRegistry.TURRET_BLOCK.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<RayTrapTileEntity>> RAY_TRAP_TILE_ENTITY = TILE_ENTITY_TYPE.register("ray_trap_tile_entity",()->
            TileEntityType.Builder.of(RayTrapTileEntity::new, BlocksRegistry.RAY_TRAP_BLOCK.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<TrapControllerTile>> TRAP_STRUCT_CONTROLLER = TILE_ENTITY_TYPE.register("trap_controller_tile",()->
            TileEntityType.Builder.of(TrapControllerTile::new, BlocksRegistry.TRAP_CONTROLLER.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<RunicTableTileEntity>> RUNIC_TABLE_TILE = TILE_ENTITY_TYPE.register("runic_table_tile",()->
            TileEntityType.Builder.of(RunicTableTileEntity::new, BlocksRegistry.RUNIC_TABLE.get().getBlock()).build(null));

    public static final RegistryObject<TileEntityType<RuneEnergyPylonTile>> RUNE_ENERGY_PYLON = TILE_ENTITY_TYPE.register("rune_energy_pylon",()->
            TileEntityType.Builder.of(RuneEnergyPylonTile::new, BlocksRegistry.RUNE_ENERGY_PYLON.get().getBlock()).build(null));

}
