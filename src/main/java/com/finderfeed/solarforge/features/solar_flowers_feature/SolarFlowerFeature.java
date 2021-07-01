package com.finderfeed.solarforge.features.solar_flowers_feature;

import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFlowersFeature;

import java.util.Random;

public class SolarFlowerFeature extends DefaultFlowersFeature {
    public SolarFlowerFeature(Codec<BlockClusterFeatureConfig> p_i231945_1_) {
        super(p_i231945_1_);
    }


    @Override
    public BlockState getRandomFlower(Random p_225562_1_, BlockPos p_225562_2_, BlockClusterFeatureConfig p_225562_3_) {
        return p_225562_3_.stateProvider.getState(p_225562_1_, p_225562_2_);
    }
}
