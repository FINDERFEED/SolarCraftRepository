package com.finderfeed.solarforge.for_future_library.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FlammableLeavesBlock extends LeavesBlock {

    private int flammability;


    public FlammableLeavesBlock(Properties p_54422_,int flammability) {
        super(p_54422_);
        this.flammability = flammability;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return flammability;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }
}
