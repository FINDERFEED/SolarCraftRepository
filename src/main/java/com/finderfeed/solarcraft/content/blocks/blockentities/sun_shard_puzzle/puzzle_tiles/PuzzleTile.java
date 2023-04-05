package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles;

import net.minecraft.nbt.CompoundTag;

public class PuzzleTile {

    private final PuzzleTileType tileType;
    private int rotation = 0;
    private boolean fixed = false;

    public PuzzleTile(PuzzleTileType tileType){
        this.tileType = tileType;
    }

    public PuzzleTile(PuzzleTile tile){
        this.tileType = tile.tileType;
        this.rotation = tile.rotation;
        this.fixed = tile.fixed;
    }

    public PuzzleTile(PuzzleTileType tileType,int rotation,boolean isFixed){
        this.tileType = tileType;
        this.rotation = rotation;
        this.fixed = isFixed;
    }

    public void serialize(CompoundTag tag){
        tag.putString("type",tileType.getName());
        tag.putBoolean("is_fixed",fixed);
        if (isValidRotation(this.tileType,this.rotation)) {
            tag.putInt("rotation", this.rotation);
        }else{
            tag.putInt("rotation", 0);
        }
    }

    public static PuzzleTile deserialize(CompoundTag tag){
        PuzzleTileType tileType = PuzzleTileTypes.getTileById(tag.getString("type"));
        int rotation = tag.getInt("rotation");
        if (!isValidRotation(tileType,rotation)){
            rotation = 0;
        }
        boolean fixed = tag.getBoolean("is_fixed");
        return new PuzzleTile(tileType,rotation,fixed);
    }


    public static boolean isValidRotation(PuzzleTileType tileType,int rot){
        if (rot == 0){
            return true;
        }
        return rot % tileType.getRotationValue() == 0;
    }

    public void rotate(){
        this.rotation = (this.rotation + tileType.getRotationValue()) % 360;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public PuzzleTileType getTileType() {
        return tileType;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof PuzzleTile other){
            var tileTestOverride = tileType.getTileTestOverride();
            if (tileTestOverride == null) {
                return tileType.equals(other.tileType) && this.rotation == other.rotation;
            }else{
                return tileTestOverride.test(this,other);
            }
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        String[] id = super.toString().replace(".",";").split(";");
        return id[id.length-1] + " " + tileType.getName() + " " + rotation;
    }
}
