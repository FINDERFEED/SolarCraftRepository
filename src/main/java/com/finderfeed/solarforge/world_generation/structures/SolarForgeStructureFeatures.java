package com.finderfeed.solarforge.world_generation.structures;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;



public class SolarForgeStructureFeatures {
    public static ConfiguredStructureFeature<?,?> CONF_DUNGEON_ONE = SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get().configured(FeatureConfiguration.NONE);
    public static ConfiguredStructureFeature<?,?> CONF_DUNGEON_MAZE = SolarForgeStructures.DUNGEON_MAZE.get().configured(FeatureConfiguration.NONE);
    public static ConfiguredStructureFeature<?,?> CONF_DUNGEON_CHARGING_STATION = SolarForgeStructures.CHARGING_STATION.get().configured(FeatureConfiguration.NONE);
   // public static FlowersFeature<?> CONF_FEATURE_TEST = new SolarFlowerFeature(BlockClusterFeatureConfig.CODEC);
   public static ConfiguredStructureFeature<?,?> CONF_MAGICIAN_TOWER = SolarForgeStructures.MAGICIAN_TOWER.get().configured(FeatureConfiguration.NONE);
    public static ConfiguredStructureFeature<?,?> CONF_DIM_SHARD_STRUCT = SolarForgeStructures.DIM_SHARD_STRUCTURE.get().configured(FeatureConfiguration.NONE);
    public static ConfiguredStructureFeature<?,?> CONF_CRYSTAL_BOSS_ROOM = SolarForgeStructures.CRYSTAL_BOSS_ROOM.get().configured(FeatureConfiguration.NONE);
    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation("solarforge", "configured_dungeon_one"), CONF_DUNGEON_ONE);
        Registry.register(registry, new ResourceLocation("solarforge", "cold_star_charging_station"), CONF_DUNGEON_CHARGING_STATION);
        Registry.register(registry, new ResourceLocation("solarforge", "labyrinth"), CONF_DUNGEON_MAZE);
        Registry.register(registry, new ResourceLocation("solarforge", "magician_tower"), CONF_MAGICIAN_TOWER);
        Registry.register(registry, new ResourceLocation("solarforge", "dimensional_shard_structure"), CONF_DIM_SHARD_STRUCT);
        Registry.register(registry, new ResourceLocation("solarforge", "crystal_boss_room"), CONF_CRYSTAL_BOSS_ROOM);
      //  Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","flowers"),CONF_FEATURE_TEST.delegate.get());


        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get(), CONF_DUNGEON_ONE);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.DUNGEON_MAZE.get(), CONF_DUNGEON_MAZE);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.CHARGING_STATION.get(), CONF_DUNGEON_CHARGING_STATION);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.MAGICIAN_TOWER.get(), CONF_MAGICIAN_TOWER);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.DIM_SHARD_STRUCTURE.get(), CONF_DIM_SHARD_STRUCT);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.CRYSTAL_BOSS_ROOM.get(), CONF_CRYSTAL_BOSS_ROOM);
    }
}
