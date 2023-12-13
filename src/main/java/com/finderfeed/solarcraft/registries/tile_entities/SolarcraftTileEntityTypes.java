package com.finderfeed.solarcraft.registries.tile_entities;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.CrystalEnergyVinesTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualMainTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity.SunShardPuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities.BeamGenerator;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities.BeamReflectorTile;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStandTileEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.*;
import com.finderfeed.solarcraft.content.blocks.solar_energy.SolarEnergyCoreTile;
import com.finderfeed.solarcraft.content.blocks.solar_energy.SolarEnergyGeneratorTile;
import com.finderfeed.solarcraft.content.blocks.solar_energy.SolarEnergyRepeaterTile;
import com.finderfeed.solarcraft.content.runic_network.repeater.RunicEnergyRepeaterTile;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.content.world_generation.structures.blocks.tile_entities.ColdStarInfuserTile;
import com.finderfeed.solarcraft.content.world_generation.structures.blocks.tile_entities.KeyDefenderTile;
import com.finderfeed.solarcraft.content.world_generation.structures.blocks.tile_entities.KeyLockStructureTile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class SolarcraftTileEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SolarCraft.MOD_ID);



    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<InfusingStandTileEntity>> INFUSING_POOL_BLOCKENTITY = TILE_ENTITY_TYPE.register("infusing_pool_blockentity",()->
            BlockEntityType.Builder.of(InfusingStandTileEntity::new, SCBlocks.INFUSING_POOL.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<KeyLockStructureTile>> KEY_LOCK_TILE = TILE_ENTITY_TYPE.register("key_lock_tile",()->
            BlockEntityType.Builder.of(KeyLockStructureTile::new, SCBlocks.KEY_LOCK_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<ColdStarInfuserTile>> COLD_STAR_INFUSER = TILE_ENTITY_TYPE.register("cold_star_infuser_tile",()->
            BlockEntityType.Builder.of(ColdStarInfuserTile::new, SCBlocks.COLD_STAR_INFUSER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<KeyDefenderTile>> KEY_DEFENDER_TILE = TILE_ENTITY_TYPE.register("key_defender_tile",()->
            BlockEntityType.Builder.of(KeyDefenderTile::new, SCBlocks.KEY_DEFENDER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<SolarLensTile>> SOLAR_LENS_TILE = TILE_ENTITY_TYPE.register("solar_lens_tile",()->
            BlockEntityType.Builder.of(SolarLensTile::new, SCBlocks.SOLAR_LENS.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<MagnetBlockTile>> MAGNET_BLOCK_TILE = TILE_ENTITY_TYPE.register("magnet_block_tile",()->
            BlockEntityType.Builder.of(MagnetBlockTile::new, SCBlocks.MAGNET_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<SolarEnergyGeneratorTile>> ENERGY_GENERATOR_TILE = TILE_ENTITY_TYPE.register("energy_generator_tile",()->
            BlockEntityType.Builder.of(SolarEnergyGeneratorTile::new, SCBlocks.SOLAR_ENERGY_GENERATOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<SolarEnergyRepeaterTile>> SOLAR_REPEATER = TILE_ENTITY_TYPE.register("energy_repeater_tile",()->
            BlockEntityType.Builder.of(SolarEnergyRepeaterTile::new, SCBlocks.SOLAR_REPEATER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<SolarEnergyCoreTile>> SOLAR_CORE_TILE = TILE_ENTITY_TYPE.register("energy_core_tile",()->
            BlockEntityType.Builder.of(SolarEnergyCoreTile::new, SCBlocks.SOLAR_CORE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<AuraHealerTile>> AURA_HEALER_TILE = TILE_ENTITY_TYPE.register("aura_healer_tile",()->
            BlockEntityType.Builder.of(AuraHealerTile::new, SCBlocks.AURA_HEALER_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<MortarTileEntity>> MORTAR_TILE_ENTITY = TILE_ENTITY_TYPE.register("mortar_tile",()->
            BlockEntityType.Builder.of(MortarTileEntity::new, SCBlocks.SOLAR_MORTAR_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<SolarEnergyFurnaceTile>> SOLAR_FURNACE_TILE_ENTITY = TILE_ENTITY_TYPE.register("solar_furnace",()->
            BlockEntityType.Builder.of(SolarEnergyFurnaceTile::new, SCBlocks.SOLAR_FURNACE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<TurretTileEntity>> TURRET_TILE_ENTITY = TILE_ENTITY_TYPE.register("turret_tile_entity",()->
            BlockEntityType.Builder.of(TurretTileEntity::new, SCBlocks.TURRET_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<RayTrapTileEntity>> RAY_TRAP_TILE_ENTITY = TILE_ENTITY_TYPE.register("ray_trap_tile_entity",()->
            BlockEntityType.Builder.of(RayTrapTileEntity::new, SCBlocks.RAY_TRAP_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<TrapControllerTile>> TRAP_STRUCT_CONTROLLER = TILE_ENTITY_TYPE.register("trap_controller_tile",()->
            BlockEntityType.Builder.of(TrapControllerTile::new, SCBlocks.TRAP_CONTROLLER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<RunicTableTileEntity>> RUNIC_TABLE_TILE = TILE_ENTITY_TYPE.register("runic_table_tile",()->
            BlockEntityType.Builder.of(RunicTableTileEntity::new, SCBlocks.RUNIC_TABLE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<RuneEnergyPylonTile>> RUNE_ENERGY_PYLON = TILE_ENTITY_TYPE.register("rune_energy_pylon",()->
            BlockEntityType.Builder.of(RuneEnergyPylonTile::new, SCBlocks.RUNE_ENERGY_PYLON.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<RunicEnergyRepeaterTile>> RUNIC_ENERGY_REPEATER = TILE_ENTITY_TYPE.register("repeater",()->
            BlockEntityType.Builder.of(RunicEnergyRepeaterTile::new, SCBlocks.REPEATER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<WormholeTileEntity>> WORMHOLE = TILE_ENTITY_TYPE.register("wormhole",()->
            BlockEntityType.Builder.of(WormholeTileEntity::new, SCBlocks.WORMHOLE.get()).build(null));

//    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<RadiantPortalCreatorTile>> PORTAL_CREATOR = TILE_ENTITY_TYPE.register("portal_creator",()->
//            BlockEntityType.Builder.of(RadiantPortalCreatorTile::new, SolarcraftBlocks.RADIANT_LAND_PORTAL_CREATOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<ZapTurretTile>> ZAP_TURRET_TILE = TILE_ENTITY_TYPE.register("zap_turret_tile",()->
            BlockEntityType.Builder.of(ZapTurretTile::new, SCBlocks.ZAP_TURRET_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<BonemealerTileEntity>> BONEMEALER = TILE_ENTITY_TYPE.register("bonemealer",()->
            BlockEntityType.Builder.of(BonemealerTileEntity::new, SCBlocks.BONEMEALER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<InfusingTableTile>> INFUSING_CRAFTING_TABLE = TILE_ENTITY_TYPE.register("infusing_crafting_table",()->
            BlockEntityType.Builder.of(InfusingTableTile::new, SCBlocks.INFUSING_CRAFTING_TABLE_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<ExplosionBlockerBlockEntity>> EXPLOSTION_BLOCKER = TILE_ENTITY_TYPE.register("explosion_blocker",()->
            BlockEntityType.Builder.of(ExplosionBlockerBlockEntity::new, SCBlocks.EXPLOSION_BLOCKER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<EnchanterBlockEntity>> ENCHANTER = TILE_ENTITY_TYPE.register("enchanter",()->
            BlockEntityType.Builder.of(EnchanterBlockEntity::new, SCBlocks.ENCHANTER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<RunicEnergyChargerTileEntity>> RUNIC_ENERGY_CHARGER = TILE_ENTITY_TYPE.register("runic_energy_charger",()->
            BlockEntityType.Builder.of(RunicEnergyChargerTileEntity::new, SCBlocks.RUNIC_ENERGY_CHARGER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<UlderaPylon>> ULDERA_PYLON = TILE_ENTITY_TYPE.register("uldera_pylon",()->
            BlockEntityType.Builder.of(UlderaPylon::new, SCBlocks.ULDERA_PYLON.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<CrystalEnergyVinesTile>> CRYSTAL_ENERGY_VINES = TILE_ENTITY_TYPE.register("crystal_energy_vines",()->
            BlockEntityType.Builder.of(CrystalEnergyVinesTile::new, SCBlocks.CRYSTAL_ENERGY_VINES.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<ClearingRitualCrystalTile>> CLEARING_RITUAL_CRYSTAL = TILE_ENTITY_TYPE.register("clearing_ritual_crystal",()->
            BlockEntityType.Builder.of(ClearingRitualCrystalTile::new, SCBlocks.CLEARING_RITUAL_CRYSTAL.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<ClearingRitualMainTile>> CLEARING_RITUAL_MAIN_BLOCK = TILE_ENTITY_TYPE.register("clearing_ritual_main_block",()->
            BlockEntityType.Builder.of(ClearingRitualMainTile::new, SCBlocks.CLEARING_RITUAL_MAIN_BLOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<DimensionCoreTile>> DIMENSION_CORE_TILE = TILE_ENTITY_TYPE.register("dimension_core",()->
            BlockEntityType.Builder.of(DimensionCoreTile::new, SCBlocks.DIMENSION_CORE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<TemporaryLightTile>> TEMPORARY_LIGHT = TILE_ENTITY_TYPE.register("temporary_light",()->
            BlockEntityType.Builder.of(TemporaryLightTile::new, SCBlocks.TEMPORARY_LIGHT.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<SavannaDungeonKeeperTile>> SAVANNA_DUNGEON_KEEPER = TILE_ENTITY_TYPE.register("savanna_dungeon_keeper",()->
            BlockEntityType.Builder.of(SavannaDungeonKeeperTile::new, SCBlocks.SAVANNA_DUNGEON_KEEPER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<RunicEnergyCoreTile>> RUNIC_ENERGY_CORE = TILE_ENTITY_TYPE.register("runic_energy_core",()->
            BlockEntityType.Builder.of(RunicEnergyCoreTile::new, SCBlocks.RUNIC_ENERGY_CORE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<SunShardPuzzleBlockEntity>> SUN_SHARD_PUZZLE_TILE = TILE_ENTITY_TYPE.register("sun_shard_puzzle_tile",()->
            BlockEntityType.Builder.of(SunShardPuzzleBlockEntity::new, SCBlocks.SUN_SHARD_LOCK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<BeamGenerator>> BEAM_GENERATOR = TILE_ENTITY_TYPE.register("beam_generator",()->
            BlockEntityType.Builder.of(BeamGenerator::new, SCBlocks.BEAM_GENERATOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<BeamReflectorTile>> BEAM_REFLECTOR = TILE_ENTITY_TYPE.register("beam_reflector",()->
            BlockEntityType.Builder.of(BeamReflectorTile::new, SCBlocks.BEAM_REFLECTOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<ElementWeaverTileEntity>> ELEMENT_WEAVER = TILE_ENTITY_TYPE.register("element_weaver",()->
            BlockEntityType.Builder.of(ElementWeaverTileEntity::new, SCBlocks.ELEMENT_WEAVER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<SolarOrbitalMissileLauncherTileEntity>> ORBITAL_MISSILE_LAUNCHER = TILE_ENTITY_TYPE.register("orbital_missile_launcher",()->
            BlockEntityType.Builder.of(SolarOrbitalMissileLauncherTileEntity::new, SCBlocks.ORBITAL_MISSILE_LAUNCHER.get()).build(null));
}
