package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class PuzzleTileType {

    private final String name;
    private final int rotationValue;
    private final BiPredicate<PuzzleTile,PuzzleTile> tileTestOverride;


    public PuzzleTileType(String name, int rotationValue){
        this.name = name;
        this.rotationValue = rotationValue;
        this.tileTestOverride = null;
    }

    public PuzzleTileType(String name, int rotationValue,BiPredicate<PuzzleTile,PuzzleTile> tileTestOverride){
        this.name = name;
        this.rotationValue = rotationValue;
        this.tileTestOverride = tileTestOverride;
    }


    public int getRotationValue() {
        return rotationValue;
    }

    public String getName() {
        return name;
    }

    public BiPredicate<PuzzleTile,PuzzleTile> getTileTestOverride() {
        return tileTestOverride;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PuzzleTileType other){
            return (other.getName().equals(this.getName()));
        }else{
            return false;
        }
    }

}
