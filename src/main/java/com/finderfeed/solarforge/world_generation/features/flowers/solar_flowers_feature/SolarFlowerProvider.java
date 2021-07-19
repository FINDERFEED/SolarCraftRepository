package com.finderfeed.solarforge.world_generation.features.flowers.solar_flowers_feature;

import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;

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
