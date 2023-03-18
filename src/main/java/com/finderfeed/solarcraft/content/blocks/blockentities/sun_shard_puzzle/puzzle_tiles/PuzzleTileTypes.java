package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PuzzleTileTypes {
    
    private static Map<String, PuzzleTileType> REGISTRY = new HashMap<>();
    
    
    public static final PuzzleTileType NULL_PUZZLE_TILE = register(new PuzzleTileType("null",360));
    public static final PuzzleTileType CORNER = register(new PuzzleTileType("corner",90));
    public static final PuzzleTileType LINE = register(new PuzzleTileType("line",90,(t,other)->{
        return t.getTileType() == other.getTileType() &&
                ((t.getRotation() == 90 && other.getRotation() == 90) || (t.getRotation() == 180 && other.getRotation() == 90) || (t.getRotation() == 90 && other.getRotation() == 180) || (t.getRotation() == 180 && other.getRotation() == 180) ||
                        (t.getRotation() == 0 && other.getRotation() == 0) || (t.getRotation() == 270 && other.getRotation() == 270) || (t.getRotation() == 180 && other.getRotation() == 270) || (t.getRotation() == 270 && other.getRotation() == 180));
    }));
    public static final PuzzleTileType CROSSROAD = register(new PuzzleTileType("crossroad",0));
    public static final PuzzleTileType TROAD = register(new PuzzleTileType("troad",90));
    public static final PuzzleTileType DOT = register(new PuzzleTileType("dot",360));


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

    public static Collection<PuzzleTileType> getAllTypes(){
        return REGISTRY.values();
    }
    
}
