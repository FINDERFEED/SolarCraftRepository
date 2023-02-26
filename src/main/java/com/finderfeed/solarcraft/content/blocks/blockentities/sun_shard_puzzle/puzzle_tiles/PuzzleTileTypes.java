package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles;

import java.util.HashMap;
import java.util.Map;

public class PuzzleTileTypes {
    
    private static Map<String, PuzzleTileType> REGISTRY = new HashMap<>();
    
    
    public static final PuzzleTileType NULL_PUZZLE_TILE = register(new PuzzleTileType("null",360));
    
    
    public static PuzzleTileType getTileById(String id){
        if (!REGISTRY.containsKey(id)){
            throw new IllegalStateException("There is no such tile with id: " + id);
        }
        return REGISTRY.get(id);
    }
    
    private static PuzzleTileType register(PuzzleTileType tile){
        REGISTRY.put(tile.getName(),tile);
        return tile;
    }
    
}
