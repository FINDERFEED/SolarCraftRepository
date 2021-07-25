package com.finderfeed.solarforge.world_generation.features.flowers.solar_flowers_feature;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacerType;

import java.util.Random;

public class SolarFlowerBlockPlacer extends BlockPlacer {

    public SolarFlowerBlockPlacer(){}

    @Override
    public void place(LevelAccessor p_225567_1_, BlockPos p_225567_2_, BlockState p_225567_3_, Random p_225567_4_) {

    }

    @Override
    protected BlockPlacerType<?> type() {
        return BlockPlacerType.SIMPLE_BLOCK_PLACER;
    }
}
