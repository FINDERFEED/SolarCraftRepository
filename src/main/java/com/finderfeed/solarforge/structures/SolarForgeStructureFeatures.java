package com.finderfeed.solarforge.structures;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class SolarForgeStructureFeatures {
    public static StructureFeature<?,?> CONF_DUNGEON_ONE = SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?,?> CONF_DUNGEON_MAZE = SolarForgeStructures.DUNGEON_MAZE.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?,?> CONF_DUNGEON_CHARGING_STATION = SolarForgeStructures.CHARGING_STATION.get().configured(IFeatureConfig.NONE);
   // public static FlowersFeature<?> CONF_FEATURE_TEST = new SolarFlowerFeature(BlockClusterFeatureConfig.CODEC);
   public static StructureFeature<?,?> CONF_MAGICIAN_TOWER = SolarForgeStructures.MAGICIAN_TOWER.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?,?> CONF_DIM_SHARD_STRUCT = SolarForgeStructures.DIM_SHARD_STRUCTURE.get().configured(IFeatureConfig.NONE);
    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation("solarforge", "configured_dungeon_one"), CONF_DUNGEON_ONE);
        Registry.register(registry, new ResourceLocation("solarforge", "cold_star_charging_station"), CONF_DUNGEON_CHARGING_STATION);
        Registry.register(registry, new ResourceLocation("solarforge", "labyrinth"), CONF_DUNGEON_MAZE);
        Registry.register(registry, new ResourceLocation("solarforge", "magician_tower"), CONF_MAGICIAN_TOWER);
        Registry.register(registry, new ResourceLocation("solarforge", "dimensional_shard_structure"), CONF_DIM_SHARD_STRUCT);
      //  Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","flowers"),CONF_FEATURE_TEST.delegate.get());


        FlatGenerationSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get(), CONF_DUNGEON_ONE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.DUNGEON_MAZE.get(), CONF_DUNGEON_MAZE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.CHARGING_STATION.get(), CONF_DUNGEON_CHARGING_STATION);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.MAGICIAN_TOWER.get(), CONF_MAGICIAN_TOWER);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(SolarForgeStructures.DIM_SHARD_STRUCTURE.get(), CONF_DIM_SHARD_STRUCT);
    }
}
