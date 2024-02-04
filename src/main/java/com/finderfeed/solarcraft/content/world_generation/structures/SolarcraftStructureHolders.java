package com.finderfeed.solarcraft.content.world_generation.structures;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.world_generation.structures.charging_station.ChargingStationStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.clearing_ritual_structure.ClearingRitualStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.crystal_boss_room.CrystalBossRoomStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.dimensional_shard_structure.DimensionalShardStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.magician_tower.MagicianTowerStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.maze_key_keeper.TrapDungeon;
import com.finderfeed.solarcraft.content.world_generation.structures.runic_elemental_arena.RunicElementalArenaStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.sun_shard_puzzle.SunShardPuzzleStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
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

//    public static final Holder<Structure> DUNGEON_ONE = register(Keys.DUNGEON_ONE,new DungeonOne(structure(SolarCraftTags.SStructures.HAS_DUNGEON_ONE, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BURY)));
//    public static final Holder<Structure> TRAP_DUNGEON = register(Keys.TRAP_DUNEGON,new TrapDungeon(structure(SolarCraftTags.SStructures.HAS_TRAP_DUNGEON, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_BOX)));
//    public static final Holder<Structure> CHARGING_STATION = register(Keys.COLD_STAR_CHARGING_STATION,new ChargingStationStructure(structure(SolarCraftTags.SStructures.HAS_CHARGING_STATION,GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
//    public static final Holder<Structure> MAGICIAN_TOWER = register(Keys.MAGICIAN_TOWER,new MagicianTowerStructure(structure(SolarCraftTags.SStructures.HAS_MAGICIAN_TOWER, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
//    public static final Holder<Structure> DIM_SHARD_STRUCT = register(Keys.DIMENSIONAL_SHARD,new DimensionalShardStructure(structure(SolarCraftTags.SStructures.HAS_DIM_SHARD_DUNGEON, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
//    public static final Holder<Structure> CRYSTAL_BOSS_ROOM = register(Keys.CRYSTAL_BOSS_ROOM,new CrystalBossRoomStructure(structure(SolarCraftTags.SStructures.HAS_CRYSTAL_BOSS_ROOM, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
//    public static final Holder<Structure> RUNIC_ELEMENTAL_ARENA = register(Keys.RUNIC_ELEMENTAL_ARENA,new RunicElementalArenaStructure(structure(SolarCraftTags.SStructures.HAS_RUNIC_ELEMENTAL_ARENA, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
//    public static final Holder<Structure> CLEARING_RITUAL = register(Keys.CLEARING_RITUAL_STRUCTURE,new ClearingRitualStructure(structure(SolarCraftTags.SStructures.HAS_CLEARING_RITUAL_STRUCTURE, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));
//    public static final Holder<Structure> SUN_SHARD_PUZZLE = register(Keys.SUN_SHARD_DUNGEON,new SunShardPuzzleStructure(structure(SolarCraftTags.SStructures.HAS_SUN_SHARD_PUZZLE, GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_THIN)));

//    private static Holder<Structure> register(ResourceKey<Structure> key, Structure structure) {
//        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, key, structure);
//    }
//
//    private static Structure.StructureSettings structure(TagKey<Biome> tag, GenerationStep.Decoration step, TerrainAdjustment adjustment) {
//        return structure(tag, Map.of(), step, adjustment);
//    }
//
//    private static Structure.StructureSettings structure(TagKey<Biome> tag, Map<MobCategory, StructureSpawnOverride> spawns, GenerationStep.Decoration step, TerrainAdjustment a) {
//        return new Structure.StructureSettings(biomes(tag), spawns, step, a);
//    }

//    private static HolderSet<Biome> biomes(TagKey<Biome> keys) {
//        return BuiltinRegistries.BIOME.getOrCreateTag(keys);
//    }

    public static class Keys{

        public static final ResourceKey<Structure> DUNGEON_ONE = createKey("dungeon_one");
        public static final ResourceKey<Structure> CLEARING_RITUAL_STRUCTURE = createKey("clearing_ritual_structure");
        public static final ResourceKey<Structure> COLD_STAR_CHARGING_STATION = createKey("cold_star_charging_station");
        public static final ResourceKey<Structure> CRYSTAL_BOSS_ROOM = createKey("crystal_boss_room");
        public static final ResourceKey<Structure> DIMENSIONAL_SHARD = createKey("dimensional_shard_structure");
        public static final ResourceKey<Structure> TRAP_DUNEGON = createKey("trap_dungeon");
        public static final ResourceKey<Structure> MAGICIAN_TOWER = createKey("magician_tower");
        public static final ResourceKey<Structure> RUNIC_ELEMENTAL_ARENA = createKey("runic_elemental_arena");
        public static final ResourceKey<Structure> SUN_SHARD_DUNGEON = createKey("sun_shard_puzzle");

        private static ResourceKey<Structure> createKey(String id) {
            return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(SolarCraft.MOD_ID,id));
        }

    }


}
