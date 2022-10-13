package com.finderfeed.solarforge.content.world_generation.structures;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.world_generation.structures.charging_station.ChargingStationStructure;
import com.finderfeed.solarforge.content.world_generation.structures.clearing_ritual_structure.ClearingRitualStructure;
import com.finderfeed.solarforge.content.world_generation.structures.crystal_boss_room.CrystalBossRoomStructure;
import com.finderfeed.solarforge.content.world_generation.structures.dimensional_shard_structure.DimensionalShardStructure;
import com.finderfeed.solarforge.content.world_generation.structures.magician_tower.MagicianTowerStructure;
import com.finderfeed.solarforge.content.world_generation.structures.maze_key_keeper.MazeStructure;
import com.finderfeed.solarforge.content.world_generation.structures.runic_elemental_arena.RunicElementalArenaStructure;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class SolarcraftStructureTypes {
//    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUC,"solarforge");

//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> DUNGEON_ONE_KEY_LOCK = STRUCTURES.register("solarforge_dungeon_one",()-> new DungeonOne(NoneFeatureConfiguration.CODEC));
//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> DUNGEON_MAZE = STRUCTURES.register("labyrinth",()-> new MazeStructure(NoneFeatureConfiguration.CODEC));
//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> CHARGING_STATION = STRUCTURES.register("cold_star_charging_station",()-> new ChargingStationStructure(NoneFeatureConfiguration.CODEC));
//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> MAGICIAN_TOWER = STRUCTURES.register("magician_tower",()-> new MagicianTowerStructure(NoneFeatureConfiguration.CODEC));
//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> DIM_SHARD_STRUCTURE = STRUCTURES.register("dimensional_shard_structure",()-> new DimensionalShardStructure(NoneFeatureConfiguration.CODEC));
//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> CRYSTAL_BOSS_ROOM = STRUCTURES.register("crystal_boss_room",()-> new CrystalBossRoomStructure(NoneFeatureConfiguration.CODEC));
//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> RUNIC_ELEMENTAL_ARENA = STRUCTURES.register("runic_elemental_arena",()-> new RunicElementalArenaStructure(NoneFeatureConfiguration.CODEC));
//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> CLEARING_RITUAL_STRUCTURE = STRUCTURES.register("clearing_ritual_structure",()-> new ClearingRitualStructure(NoneFeatureConfiguration.CODEC));

    public static final StructureType<DungeonOne> DUNGEON_KEY_LOCK = register("solarforge_dungeon_one",DungeonOne.CODEC);
    public static final StructureType<ChargingStationStructure> CHARGING_STATION = register("cold_star_charging_station",ChargingStationStructure.CODEC);
    public static final StructureType<ClearingRitualStructure> CLEARING_RITUAL_STRUCTURE_STRUCTURE_TYPE = register("clearing_ritual_structure",ClearingRitualStructure.CODEC);
    public static final StructureType<CrystalBossRoomStructure> CRYSTAL_BOSS_ROOM_STRUCTURE_STRUCTURE_TYPE = register("crystal_boss_room",CrystalBossRoomStructure.CODEC);
    public static final StructureType<DimensionalShardStructure> DIMENSIONAL_SHARD_STRUCTURE_STRUCTURE_TYPE = register("dimensional_shard_structure",DimensionalShardStructure.CODEC);
    public static final StructureType<MagicianTowerStructure> MAGICIAN_TOWER_STRUCTURE_STRUCTURE_TYPE = register("magician_tower",MagicianTowerStructure.CODEC);
    public static final StructureType<MazeStructure>  MAZE_STRUCTURE_STRUCTURE_TYPE = register("labyrinth",MazeStructure.CODEC);
    public static final StructureType<RunicElementalArenaStructure>  RUNIC_ELEMENTAL_ARENA_STRUCTURE_STRUCTURE_TYPE = register("runic_elemental_arena",RunicElementalArenaStructure.CODEC);

    public static void init(){}


    private static <S extends Structure> StructureType<S> register(String id, Codec<S> codec) {
        return Registry.register(Registry.STRUCTURE_TYPES, new ResourceLocation(SolarForge.MOD_ID,id), () -> {
            return codec;
        });
    }
}
