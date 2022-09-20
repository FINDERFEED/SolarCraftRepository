package com.finderfeed.solarforge.registries.tile_entities;

import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.CrystalEnergyVinesTile;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalTile;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualMainTile;
import com.finderfeed.solarforge.content.blocks.infusing_table_things.infusing_pool.InfusingStandTileEntity;
import com.finderfeed.solarforge.content.blocks.blockentities.*;
import com.finderfeed.solarforge.content.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarforge.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarforge.content.world_generation.structures.blocks.tile_entities.ColdStarInfuserTile;
import com.finderfeed.solarforge.content.world_generation.structures.blocks.tile_entities.KeyDefenderTile;
import com.finderfeed.solarforge.content.world_generation.structures.blocks.tile_entities.KeyLockStructureTile;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SolarcraftTileEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,"solarforge");



    public static final RegistryObject<BlockEntityType<InfusingStandTileEntity>> INFUSING_POOL_BLOCKENTITY = TILE_ENTITY_TYPE.register("infusing_pool_blockentity",()->
            BlockEntityType.Builder.of(InfusingStandTileEntity::new, SolarcraftBlocks.INFUSING_POOL.get()).build(null));

    public static final RegistryObject<BlockEntityType<KeyLockStructureTile>> KEY_LOCK_TILE = TILE_ENTITY_TYPE.register("key_lock_tile",()->
            BlockEntityType.Builder.of(KeyLockStructureTile::new, SolarcraftBlocks.KEY_LOCK_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ColdStarInfuserTile>> COLD_STAR_INFUSER = TILE_ENTITY_TYPE.register("cold_star_infuser_tile",()->
            BlockEntityType.Builder.of(ColdStarInfuserTile::new, SolarcraftBlocks.COLD_STAR_INFUSER.get()).build(null));

    public static final RegistryObject<BlockEntityType<KeyDefenderTile>> KEY_DEFENDER_TILE = TILE_ENTITY_TYPE.register("key_defender_tile",()->
            BlockEntityType.Builder.of(KeyDefenderTile::new, SolarcraftBlocks.KEY_DEFENDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SolarLensTile>> SOLAR_LENS_TILE = TILE_ENTITY_TYPE.register("solar_lens_tile",()->
            BlockEntityType.Builder.of(SolarLensTile::new, SolarcraftBlocks.SOLAR_LENS.get()).build(null));

    public static final RegistryObject<BlockEntityType<MagnetBlockTile>> MAGNET_BLOCK_TILE = TILE_ENTITY_TYPE.register("magnet_block_tile",()->
            BlockEntityType.Builder.of(MagnetBlockTile::new, SolarcraftBlocks.MAGNET_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnergyGeneratorTile>> ENERGY_GENERATOR_TILE = TILE_ENTITY_TYPE.register("energy_generator_tile",()->
            BlockEntityType.Builder.of(EnergyGeneratorTile::new, SolarcraftBlocks.SOLAR_ENERGY_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<SolarEnergyRepeaterBlockEntity>> SOLAR_REPEATER = TILE_ENTITY_TYPE.register("energy_repeater_tile",()->
            BlockEntityType.Builder.of(SolarEnergyRepeaterBlockEntity::new, SolarcraftBlocks.SOLAR_REPEATER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SolarCoreBlockEntity>> SOLAR_CORE_TILE = TILE_ENTITY_TYPE.register("energy_core_tile",()->
            BlockEntityType.Builder.of(SolarCoreBlockEntity::new, SolarcraftBlocks.SOLAR_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<AuraHealerTile>> AURA_HEALER_TILE = TILE_ENTITY_TYPE.register("aura_healer_tile",()->
            BlockEntityType.Builder.of(AuraHealerTile::new, SolarcraftBlocks.AURA_HEALER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MortarTileEntity>> MORTAR_TILE_ENTITY = TILE_ENTITY_TYPE.register("mortar_tile",()->
            BlockEntityType.Builder.of(MortarTileEntity::new, SolarcraftBlocks.SOLAR_MORTAR_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SolarEnergyFurnaceTile>> SOLAR_FURNACE_TILE_ENTITY = TILE_ENTITY_TYPE.register("solar_furnace",()->
            BlockEntityType.Builder.of(SolarEnergyFurnaceTile::new, SolarcraftBlocks.SOLAR_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<TurretTileEntity>> TURRET_TILE_ENTITY = TILE_ENTITY_TYPE.register("turret_tile_entity",()->
            BlockEntityType.Builder.of(TurretTileEntity::new, SolarcraftBlocks.TURRET_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<RayTrapTileEntity>> RAY_TRAP_TILE_ENTITY = TILE_ENTITY_TYPE.register("ray_trap_tile_entity",()->
            BlockEntityType.Builder.of(RayTrapTileEntity::new, SolarcraftBlocks.RAY_TRAP_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<TrapControllerTile>> TRAP_STRUCT_CONTROLLER = TILE_ENTITY_TYPE.register("trap_controller_tile",()->
            BlockEntityType.Builder.of(TrapControllerTile::new, SolarcraftBlocks.TRAP_CONTROLLER.get()).build(null));

    public static final RegistryObject<BlockEntityType<RunicTableTileEntity>> RUNIC_TABLE_TILE = TILE_ENTITY_TYPE.register("runic_table_tile",()->
            BlockEntityType.Builder.of(RunicTableTileEntity::new, SolarcraftBlocks.RUNIC_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<RuneEnergyPylonTile>> RUNE_ENERGY_PYLON = TILE_ENTITY_TYPE.register("rune_energy_pylon",()->
            BlockEntityType.Builder.of(RuneEnergyPylonTile::new, SolarcraftBlocks.RUNE_ENERGY_PYLON.get()).build(null));

    public static final RegistryObject<BlockEntityType<BaseRepeaterTile>> REPEATER = TILE_ENTITY_TYPE.register("repeater",()->
            BlockEntityType.Builder.of(BaseRepeaterTile::new, SolarcraftBlocks.REPEATER.get()).build(null));

    public static final RegistryObject<BlockEntityType<WormholeTileEntity>> WORMHOLE = TILE_ENTITY_TYPE.register("wormhole",()->
            BlockEntityType.Builder.of(WormholeTileEntity::new, SolarcraftBlocks.WORMHOLE.get()).build(null));

//    public static final RegistryObject<BlockEntityType<RadiantPortalCreatorTile>> PORTAL_CREATOR = TILE_ENTITY_TYPE.register("portal_creator",()->
//            BlockEntityType.Builder.of(RadiantPortalCreatorTile::new, SolarcraftBlocks.RADIANT_LAND_PORTAL_CREATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ZapTurretTile>> ZAP_TURRET_TILE = TILE_ENTITY_TYPE.register("zap_turret_tile",()->
            BlockEntityType.Builder.of(ZapTurretTile::new, SolarcraftBlocks.ZAP_TURRET_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<BonemealerTileEntity>> BONEMEALER = TILE_ENTITY_TYPE.register("bonemealer",()->
            BlockEntityType.Builder.of(BonemealerTileEntity::new, SolarcraftBlocks.BONEMEALER.get()).build(null));

    public static final RegistryObject<BlockEntityType<InfusingTableTile>> INFUSING_CRAFTING_TABLE = TILE_ENTITY_TYPE.register("infusing_crafting_table",()->
            BlockEntityType.Builder.of(InfusingTableTile::new, SolarcraftBlocks.INFUSING_CRAFTING_TABLE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ExplosionBlockerBlockEntity>> EXPLOSTION_BLOCKER = TILE_ENTITY_TYPE.register("explosion_blocker",()->
            BlockEntityType.Builder.of(ExplosionBlockerBlockEntity::new, SolarcraftBlocks.EXPLOSION_BLOCKER.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnchanterBlockEntity>> ENCHANTER = TILE_ENTITY_TYPE.register("enchanter",()->
            BlockEntityType.Builder.of(EnchanterBlockEntity::new, SolarcraftBlocks.ENCHANTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<RunicEnergyChargerTileEntity>> RUNIC_ENERGY_CHARGER = TILE_ENTITY_TYPE.register("runic_energy_charger",()->
            BlockEntityType.Builder.of(RunicEnergyChargerTileEntity::new, SolarcraftBlocks.RUNIC_ENERGY_CHARGER.get()).build(null));

    public static final RegistryObject<BlockEntityType<UlderaPylon>> ULDERA_PYLON = TILE_ENTITY_TYPE.register("uldera_pylon",()->
            BlockEntityType.Builder.of(UlderaPylon::new, SolarcraftBlocks.ULDERA_PYLON.get()).build(null));

    public static final RegistryObject<BlockEntityType<CrystalEnergyVinesTile>> CRYSTAL_ENERGY_VINES = TILE_ENTITY_TYPE.register("crystal_energy_vines",()->
            BlockEntityType.Builder.of(CrystalEnergyVinesTile::new, SolarcraftBlocks.CRYSTAL_ENERGY_VINES.get()).build(null));

    public static final RegistryObject<BlockEntityType<ClearingRitualCrystalTile>> CLEARING_RITUAL_CRYSTAL = TILE_ENTITY_TYPE.register("clearing_ritual_crystal",()->
            BlockEntityType.Builder.of(ClearingRitualCrystalTile::new, SolarcraftBlocks.CLEARING_RITUAL_CRYSTAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<ClearingRitualMainTile>> CLEARING_RITUAL_MAIN_BLOCK = TILE_ENTITY_TYPE.register("clearing_ritual_main_block",()->
            BlockEntityType.Builder.of(ClearingRitualMainTile::new, SolarcraftBlocks.CLEARING_RITUAL_MAIN_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<DimensionCoreTile>> DIMENSION_CORE_TILE = TILE_ENTITY_TYPE.register("dimension_core",()->
            BlockEntityType.Builder.of(DimensionCoreTile::new, SolarcraftBlocks.DIMENSION_CORE.get()).build(null));
}
