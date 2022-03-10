package com.finderfeed.solarforge.misc_things;

import net.minecraft.core.Direction;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class StateAndTag {

    private final BlockState state;
    private TagKey<Block> tag;
    private final boolean ignoreFacing;

    public StateAndTag(BlockState state, TagKey<Block> tag,boolean ignoreFacing){
            this.state = state;
            this.tag = tag;
            this.ignoreFacing = ignoreFacing;
    }

    public BlockState getState() {
        return state;
    }

    public boolean isIgnoreFacing() {
        return ignoreFacing;
    }

    public TagKey<Block> getTag() {
        return tag;
    }
    public static boolean checkBlockState(BlockState worldState,BlockState toCheck,boolean ignoreFacing){
        if (worldState.getBlock() == toCheck.getBlock()){
            if (worldState.hasProperty(HorizontalDirectionalBlock.FACING) && toCheck.hasProperty(HorizontalDirectionalBlock.FACING) && ignoreFacing){
                Direction dir1 = worldState.getValue(HorizontalDirectionalBlock.FACING);
                Direction dir2 = toCheck.getValue(HorizontalDirectionalBlock.FACING);
                return facingEqual(dir1,dir2);
            }else{
                return worldState == toCheck;
            }
        }
        return false;
    }

    private static boolean facingEqual(Direction first,Direction second){
        if ((first == second) || (first.getOpposite() == second)){
            return true;
        }
        return false;
    }
}
