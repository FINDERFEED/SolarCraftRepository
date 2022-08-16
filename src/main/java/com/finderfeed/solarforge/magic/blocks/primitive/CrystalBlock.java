package com.finderfeed.solarforge.magic.blocks.primitive;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrystalBlock extends DirectionBlock{
    private VoxelShape shape_up = Block.box(5,0,5,11,12,11);
    private VoxelShape shape_down = Block.box(5,4,5,11,16,11);
    private VoxelShape shape_south = Block.box(5,5,0,11,11,12);
    private VoxelShape shape_north = Block.box(5,5,4,11,11,16);
    private VoxelShape shape_east = Block.box(0,5,5,12,11,11);
    private VoxelShape shape_west = Block.box(4,5,5,16,11,11);

    public CrystalBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return switch (p_60555_.getValue(DirectionBlock.PROP)){
            case UP -> shape_up;
            case DOWN -> shape_down;
            case SOUTH -> shape_south;
            case NORTH -> shape_north;
            case EAST -> shape_east;
            case WEST -> shape_west;
        };
    }

}
