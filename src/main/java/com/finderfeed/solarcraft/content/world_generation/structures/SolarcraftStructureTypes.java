package com.finderfeed.solarcraft.content.world_generation.structures;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.world_generation.structures.charging_station.ChargingStationStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.clearing_ritual_structure.ClearingRitualStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.crystal_boss_room.CrystalBossRoomStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.dimensional_shard_structure.DimensionalShardStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.magician_tower.MagicianTowerStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.maze_key_keeper.TrapDungeon;
import com.finderfeed.solarcraft.content.world_generation.structures.runic_elemental_arena.RunicElementalArenaStructure;
import com.finderfeed.solarcraft.content.world_generation.structures.sun_shard_puzzle.SunShardPuzzleStructure;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class SolarcraftStructureTypes {

    public static final StructureType<DungeonOne> DUNGEON_KEY_LOCK = register("solarcraft_dungeon_one",DungeonOne.CODEC);
    public static final StructureType<ChargingStationStructure> CHARGING_STATION = register("cold_star_charging_station",ChargingStationStructure.CODEC);
    public static final StructureType<ClearingRitualStructure> CLEARING_RITUAL_STRUCTURE_STRUCTURE_TYPE = register("clearing_ritual_structure",ClearingRitualStructure.CODEC);
    public static final StructureType<CrystalBossRoomStructure> CRYSTAL_BOSS_ROOM_STRUCTURE_STRUCTURE_TYPE = register("crystal_boss_room",CrystalBossRoomStructure.CODEC);
    public static final StructureType<DimensionalShardStructure> DIMENSIONAL_SHARD_STRUCTURE_STRUCTURE_TYPE = register("dimensional_shard_structure",DimensionalShardStructure.CODEC);
    public static final StructureType<MagicianTowerStructure> MAGICIAN_TOWER_STRUCTURE_STRUCTURE_TYPE = register("magician_tower",MagicianTowerStructure.CODEC);
    public static final StructureType<TrapDungeon> TRAP_DUNGEON = register("trap_dungeon", TrapDungeon.CODEC);
    public static final StructureType<RunicElementalArenaStructure>  RUNIC_ELEMENTAL_ARENA_STRUCTURE_STRUCTURE_TYPE = register("runic_elemental_arena",RunicElementalArenaStructure.CODEC);
    public static final StructureType<SunShardPuzzleStructure>  SUN_SHARD_PUZZLE_STRUCTURE_TYPE = register("sun_shard_puzzle", SunShardPuzzleStructure.CODEC);

    public static void init(){}


    private static <S extends Structure> StructureType<S> register(String id, Codec<S> codec) {
        return Registry.register(Registry.STRUCTURE_TYPES, new ResourceLocation(SolarCraft.MOD_ID,id), () -> {
            return codec;
        });
    }
}
