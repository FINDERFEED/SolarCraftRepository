package com.finderfeed.solarforge.content.world_generation.structures;

import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.SolarForge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;



public class SolarForgeConfiguredStructures {


    public static ConfiguredStructureFeature<?,?> CONF_DUNGEON_ONE = SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get()
            .configured(FeatureConfiguration.NONE, SolarCraftTags.SStructures.HAS_DUNGEON_ONE,true);
    public static ConfiguredStructureFeature<?,?> CONF_DUNGEON_TRAP_DUNGEON = SolarForgeStructures.TRAP_DUNGEON.get()
            .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_TRAP_DUNGEON,true);
    public static ConfiguredStructureFeature<?,?> CONF_DUNGEON_CHARGING_STATION = SolarForgeStructures.CHARGING_STATION.get()
            .configured(FeatureConfiguration.NONE, SolarCraftTags.SStructures.HAS_CHARGING_STATION,true);
   public static ConfiguredStructureFeature<?,?> CONF_MAGICIAN_TOWER = SolarForgeStructures.MAGICIAN_TOWER.get()
           .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_MAGICIAN_TOWER,true);
    public static ConfiguredStructureFeature<?,?> CONF_DIM_SHARD_STRUCT = SolarForgeStructures.DIM_SHARD_STRUCTURE.get()
            .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_DIM_SHARD_DUNGEON);
    public static ConfiguredStructureFeature<?,?> CONF_CRYSTAL_BOSS_ROOM = SolarForgeStructures.CRYSTAL_BOSS_ROOM.get()
            .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_CRYSTAL_BOSS_ROOM,true);
    public static ConfiguredStructureFeature<?,?> RUNIC_ELEMENTAL_ARENA = SolarForgeStructures.RUNIC_ELEMENTAL_ARENA.get()
            .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_RUNIC_ELEMENTAL_ARENA,true);
    public static ConfiguredStructureFeature<?,?> CLEARING_RITUAL_STRUCTURE = SolarForgeStructures.CLEARING_RITUAL_STRUCTURE.get()
            .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_CLEARING_RITUAL_STRUCTURE,true);



    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "configured_dungeon_one"), CONF_DUNGEON_ONE);
        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "cold_star_charging_station"), CONF_DUNGEON_CHARGING_STATION);
        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "trap_dungeon"), CONF_DUNGEON_TRAP_DUNGEON);
        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "magician_tower"), CONF_MAGICIAN_TOWER);
        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "dimensional_shard_structure"), CONF_DIM_SHARD_STRUCT);
        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "crystal_boss_room"), CONF_CRYSTAL_BOSS_ROOM);
        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "runic_elemental_arena"), RUNIC_ELEMENTAL_ARENA);
        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "clearing_ritual_structure"), CLEARING_RITUAL_STRUCTURE);

    }
}
