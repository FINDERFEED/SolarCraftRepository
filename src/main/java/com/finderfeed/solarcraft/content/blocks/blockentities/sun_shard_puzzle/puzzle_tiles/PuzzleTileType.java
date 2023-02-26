package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles;

public class PuzzleTileType {

    private final String name;
    private final int rotationValue;


    public PuzzleTileType(String name, int rotationValue){
        this.name = name;
        this.rotationValue = rotationValue;
    }


    public int getRotationValue() {
        return rotationValue;
    }

    public String getName() {
        return name;
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
