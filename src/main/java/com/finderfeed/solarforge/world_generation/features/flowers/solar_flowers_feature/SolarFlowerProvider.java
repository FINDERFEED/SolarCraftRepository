package com.finderfeed.solarforge.world_generation.features.flowers.solar_flowers_feature;

import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

import java.util.Random;

public class SolarFlowerProvider extends BlockStateProvider {

    public SolarFlowerProvider(){}

    @Override
    protected BlockStateProviderType<?> type() {
        return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
    }

    @Override
    public BlockState getState(Random p_225574_1_, BlockPos p_225574_2_) {
        return BlocksRegistry.SOLAR_STONE_BRICKS.get().defaultBlockState();
    }
}
