package com.finderfeed.solarforge.events.other_events;


import com.finderfeed.solarforge.world_generation.structures.charging_station.ChargingStationPieces;
import com.finderfeed.solarforge.world_generation.structures.dimensional_shard_structure.DimStructPieces;
import com.finderfeed.solarforge.world_generation.structures.dungeon_one_key_lock.DungeonOnePieces;
import com.finderfeed.solarforge.world_generation.structures.magician_tower.MagicianTowerPieces;
import com.finderfeed.solarforge.world_generation.structures.maze_key_keeper.MazeStructurePieces;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureInit {


    public static IStructurePieceType DUNGEON_ONE_PIECE_TEST = DungeonOnePieces.Piece::new;
    public static IStructurePieceType DUNGEON_MAZE_PIECE = MazeStructurePieces.Piece::new;
    public static IStructurePieceType CHARGING_PIECE = ChargingStationPieces.Piece::new;
    public static IStructurePieceType MAGICIAN_TOWER = MagicianTowerPieces.Piece::new;
    public static IStructurePieceType DIMENSIONAL_SHARD_STRUCTURE = DimStructPieces.Piece::new;
    @SubscribeEvent
    public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event){
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        Registry.register(Registry.STRUCTURE_PIECE,"solar_forge_dungeon_one_piece_test",DUNGEON_ONE_PIECE_TEST);
        Registry.register(Registry.STRUCTURE_PIECE,"solar_forge_dungeon_maze_piece",DUNGEON_MAZE_PIECE);
        Registry.register(Registry.STRUCTURE_PIECE,"solar_forge_charging_piece",CHARGING_PIECE);
        Registry.register(Registry.STRUCTURE_PIECE,"magician_tower_piece",MAGICIAN_TOWER);
        Registry.register(Registry.STRUCTURE_PIECE,"dimensional_shard_structure",DIMENSIONAL_SHARD_STRUCTURE);
    }
}
