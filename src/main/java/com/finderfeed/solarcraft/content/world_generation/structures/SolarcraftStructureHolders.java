package com.finderfeed.solarcraft.content.world_generation.structures;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.world_generation.structures.charging_station.ChargingStationStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.clearing_ritual_structure.ClearingRitualStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.crystal_boss_room.CrystalBossRoomStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.dimensional_shard_structure.DimensionalShardStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.magician_tower.MagicianTowerStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.maze_key_keeper.MazeStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.runic_elemental_arena.RunicElementalArenaStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Map;


public class SolarcraftStructureHolders {

    public static void init(){}


//    public static ConfiguredStructureFeature<?,?> CONF_DUNGEON_ONE = SolarForgeStructures.DUNGEON_ONE_KEY_LOCK.get()
//            .configured(FeatureConfiguration.NONE, SolarCraftTags.SStructures.HAS_DUNGEON_ONE,true);
//    public static ConfiguredStructureFeature<?,?> CONF_DUNGEON_MAZE = SolarForgeStructures.DUNGEON_MAZE.get()
//            .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_LABYRINTH,true);
//    public static ConfiguredStructureFeature<?,?> CONF_DUNGEON_CHARGING_STATION = SolarForgeStructures.CHARGING_STATION.get()
//            .configured(FeatureConfiguration.NONE, SolarCraftTags.SStructures.HAS_CHARGING_STATION,true);
//   public static ConfiguredStructureFeature<?,?> CONF_MAGICIAN_TOWER = SolarForgeStructures.MAGICIAN_TOWER.get()
//           .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_MAGICIAN_TOWER,true);
//    public static ConfiguredStructureFeature<?,?> CONF_DIM_SHARD_STRUCT = SolarForgeStructures.DIM_SHARD_STRUCTURE.get()
//            .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_DIM_SHARD_DUNGEON);
//    public static ConfiguredStructureFeature<?,?> CONF_CRYSTAL_BOSS_ROOM = SolarForgeStructures.CRYSTAL_BOSS_ROOM.get()
//            .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_CRYSTAL_BOSS_ROOM,true);
//    public static ConfiguredStructureFeature<?,?> RUNIC_ELEMENTAL_ARENA = SolarForgeStructures.RUNIC_ELEMENTAL_ARENA.get()
//            .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_RUNIC_ELEMENTAL_ARENA,true);
//    public static ConfiguredStructureFeature<?,?> CLEARING_RITUAL_STRUCTURE = SolarForgeStructures.CLEARING_RITUAL_STRUCTURE.get()
//            .configured(FeatureConfiguration.NONE,SolarCraftTags.SStructures.HAS_CLEARING_RITUAL_STRUCTURE,true);

    public static final Holder<Structure> DUNGEON_ONE = register(Keys.DUNGEON_ONE,new DungeonOne(structure(SolarCraftTags.SStructures.HAS_DUNGEON_ONE, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_BOX)));
    public static final Holder<Structure> LABYRINTH = register(Keys.LABYRINTH,new MazeStructure(structure(SolarCraftTags.SStructures.HAS_LABYRINTH, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.NONE)));
    public static final Holder<Structure> CHARGING_STATION = register(Keys.COLD_STAR_CHARGING_STATION,new ChargingStationStructure(structure(SolarCraftTags.SStructures.HAS_CHARGING_STATION,GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
    public static final Holder<Structure> MAGICIAN_TOWER = register(Keys.MAGICIAN_TOWER,new MagicianTowerStructure(structure(SolarCraftTags.SStructures.HAS_MAGICIAN_TOWER, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
    public static final Holder<Structure> DIM_SHARD_STRUCT = register(Keys.DIMENSIONAL_SHARD,new DimensionalShardStructure(structure(SolarCraftTags.SStructures.HAS_DIM_SHARD_DUNGEON, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
    public static final Holder<Structure> CRYSTAL_BOSS_ROOM = register(Keys.CRYSTAL_BOSS_ROOM,new CrystalBossRoomStructure(structure(SolarCraftTags.SStructures.HAS_CRYSTAL_BOSS_ROOM, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
    public static final Holder<Structure> RUNIC_ELEMENTAL_ARENA = register(Keys.RUNIC_ELEMENTAL_ARENA,new RunicElementalArenaStructure(structure(SolarCraftTags.SStructures.HAS_RUNIC_ELEMENTAL_ARENA, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
    public static final Holder<Structure> CLEARING_RITUAL = register(Keys.CLEARING_RITUAL_STRUCTURE,new ClearingRitualStructure(structure(SolarCraftTags.SStructures.HAS_CLEARING_RITUAL_STRUCTURE, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));

    private static Holder<Structure> register(ResourceKey<Structure> key, Structure structure) {
        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, key, structure);
    }

    private static Structure.StructureSettings structure(TagKey<Biome> tag, GenerationStep.Decoration step, TerrainAdjustment adjustment) {
        return structure(tag, Map.of(), step, adjustment);
    }

    private static Structure.StructureSettings structure(TagKey<Biome> tag, Map<MobCategory, StructureSpawnOverride> spawns, GenerationStep.Decoration step, TerrainAdjustment a) {
        return new Structure.StructureSettings(biomes(tag), spawns, step, a);
    }

    private static HolderSet<Biome> biomes(TagKey<Biome> keys) {
        return BuiltinRegistries.BIOME.getOrCreateTag(keys);
    }

    public static class Keys{

        public static final ResourceKey<Structure> DUNGEON_ONE = createKey("dungeon_one");
        public static final ResourceKey<Structure> CLEARING_RITUAL_STRUCTURE = createKey("clearing_ritual_structure");
        public static final ResourceKey<Structure> COLD_STAR_CHARGING_STATION = createKey("cold_star_charging_station");
        public static final ResourceKey<Structure> CRYSTAL_BOSS_ROOM = createKey("crystal_boss_room");
        public static final ResourceKey<Structure> DIMENSIONAL_SHARD = createKey("dimensional_shard_structure");
        public static final ResourceKey<Structure> LABYRINTH = createKey("labyrinth");
        public static final ResourceKey<Structure> MAGICIAN_TOWER = createKey("magician_tower");
        public static final ResourceKey<Structure> RUNIC_ELEMENTAL_ARENA = createKey("runic_elemental_arena");

        private static ResourceKey<Structure> createKey(String id) {
            return ResourceKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(SolarCraft.MOD_ID,id));
        }

    }


//    public static void registerConfiguredStructures() {
////        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
////        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "configured_dungeon_one"), CONF_DUNGEON_ONE);
////        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "cold_star_charging_station"), CONF_DUNGEON_CHARGING_STATION);
////        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "labyrinth"), CONF_DUNGEON_MAZE);
////        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "magician_tower"), CONF_MAGICIAN_TOWER);
////        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "dimensional_shard_structure"), CONF_DIM_SHARD_STRUCT);
////        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "crystal_boss_room"), CONF_CRYSTAL_BOSS_ROOM);
////        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "runic_elemental_arena"), RUNIC_ELEMENTAL_ARENA);
////        Registry.register(registry, new ResourceLocation(SolarForge.MOD_ID, "clearing_ritual_structure"), CLEARING_RITUAL_STRUCTURE);
//
//    }
}
