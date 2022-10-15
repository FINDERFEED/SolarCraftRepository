package com.finderfeed.solarcraft.local_library.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RotatedPillarFlammableBlock extends RotatedPillarBlock {
    private int flamability;
    public RotatedPillarFlammableBlock(Properties p_55926_,int flamability) {
        super(p_55926_);
        this.flamability = flamability;
    }


    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return flamability;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }
}
