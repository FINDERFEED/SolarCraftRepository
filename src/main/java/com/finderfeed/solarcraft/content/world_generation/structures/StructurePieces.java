package com.finderfeed.solarcraft.content.world_generation.structures;


import com.finderfeed.solarcraft.content.world_generation.structures.charging_station.ChargingStationPieces;
import com.finderfeed.solarcraft.content.world_generation.structures.clearing_ritual_structure.ClearingRitualPieces;
import com.finderfeed.solarcraft.content.world_generation.structures.crystal_boss_room.CrystalBossRoomStructurePieces;
import com.finderfeed.solarcraft.content.world_generation.structures.dimensional_shard_structure.DimStructPieces;
import com.finderfeed.solarcraft.content.world_generation.structures.dungeon_one_key_lock.DungeonOnePieces;
import com.finderfeed.solarcraft.content.world_generation.structures.magician_tower.MagicianTowerPieces;
import com.finderfeed.solarcraft.content.world_generation.structures.maze_key_keeper.TrapStructurePieces;
import com.finderfeed.solarcraft.content.world_generation.structures.runic_elemental_arena.RunicElementalArenaStructurePieces;
import com.finderfeed.solarcraft.content.world_generation.structures.sun_shard_puzzle.SunShardPuzzleStructurePieces;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StructurePieces {


    public static StructurePieceType DUNGEON_ONE_PIECE = DungeonOnePieces.Piece::new;
    public static StructurePieceType DUNGEON_MAZE_PIECE = TrapStructurePieces.Piece::new;
    public static StructurePieceType CHARGING_PIECE = ChargingStationPieces.Piece::new;
    public static StructurePieceType MAGICIAN_TOWER = MagicianTowerPieces.Piece::new;
    public static StructurePieceType DIMENSIONAL_SHARD_STRUCTURE = DimStructPieces.Piece::new;
    public static StructurePieceType CRYSTAL_BOSS_ROOM_PIECE = CrystalBossRoomStructurePieces.Piece::new;
    public static StructurePieceType RUNIC_ELEMENTAL_ARENA_PIECE = RunicElementalArenaStructurePieces.Piece::new;
    public static StructurePieceType SUN_SHARD_PUZZLE_PIECE = SunShardPuzzleStructurePieces.Piece::new;
    public static StructurePieceType CLEARING_RITUAL_STRUCTURE = ClearingRitualPieces.Piece::new;

    
    @SubscribeEvent
    public static void registerFeatures(FMLCommonSetupEvent event){
        event.enqueueWork(()->{

            Registry.register(BuiltInRegistries.STRUCTURE_PIECE, "solar_forge_dungeon_one_piece_test", DUNGEON_ONE_PIECE);
            Registry.register(BuiltInRegistries.STRUCTURE_PIECE, "solar_forge_dungeon_maze_piece", DUNGEON_MAZE_PIECE);
            Registry.register(BuiltInRegistries.STRUCTURE_PIECE, "solar_forge_charging_piece", CHARGING_PIECE);
            Registry.register(BuiltInRegistries.STRUCTURE_PIECE, "magician_tower_piece", MAGICIAN_TOWER);
            Registry.register(BuiltInRegistries.STRUCTURE_PIECE, "dimensional_shard_structure", DIMENSIONAL_SHARD_STRUCTURE);
            Registry.register(BuiltInRegistries.STRUCTURE_PIECE, "crystal_boss_room_piece", CRYSTAL_BOSS_ROOM_PIECE);
            Registry.register(BuiltInRegistries.STRUCTURE_PIECE, "runic_elemental_arena_piece", RUNIC_ELEMENTAL_ARENA_PIECE);
            Registry.register(BuiltInRegistries.STRUCTURE_PIECE, "clearing_ritual_piece", CLEARING_RITUAL_STRUCTURE);
            Registry.register(BuiltInRegistries.STRUCTURE_PIECE, "sun_shard_puzzle_piece", SUN_SHARD_PUZZLE_PIECE);
            });
        }
}
