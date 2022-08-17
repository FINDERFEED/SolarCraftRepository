package com.finderfeed.solarforge.world_generation.structures;

import com.finderfeed.solarforge.world_generation.structures.charging_station.ChargingStationStructure;
import com.finderfeed.solarforge.world_generation.structures.clearing_ritual_structure.ClearingRitualStructure;
import com.finderfeed.solarforge.world_generation.structures.crystal_boss_room.CrystalBossRoomStructure;
import com.finderfeed.solarforge.world_generation.structures.dimensional_shard_structure.DimensionalShardStructure;
import com.finderfeed.solarforge.world_generation.structures.magician_tower.MagicianTowerStructure;
import com.finderfeed.solarforge.world_generation.structures.maze_key_keeper.MazeStructure;
import com.finderfeed.solarforge.world_generation.structures.runic_elemental_arena.RunicElementalArenaStructure;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SolarForgeStructures {
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES,"solarforge");

    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> DUNGEON_ONE_KEY_LOCK = STRUCTURES.register("solarforge_dungeon_one",()-> new DungeonOne(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> DUNGEON_MAZE = STRUCTURES.register("labyrinth",()-> new MazeStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> CHARGING_STATION = STRUCTURES.register("cold_star_charging_station",()-> new ChargingStationStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> MAGICIAN_TOWER = STRUCTURES.register("magician_tower",()-> new MagicianTowerStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> DIM_SHARD_STRUCTURE = STRUCTURES.register("dimensional_shard_structure",()-> new DimensionalShardStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> CRYSTAL_BOSS_ROOM = STRUCTURES.register("crystal_boss_room",()-> new CrystalBossRoomStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> RUNIC_ELEMENTAL_ARENA = STRUCTURES.register("runic_elemental_arena",()-> new RunicElementalArenaStructure(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> CLEARING_RITUAL_STRUCTURE = STRUCTURES.register("clearing_ritual_structure",()-> new ClearingRitualStructure(NoneFeatureConfiguration.CODEC));

}
