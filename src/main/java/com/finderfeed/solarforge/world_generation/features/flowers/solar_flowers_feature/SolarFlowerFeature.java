package com.finderfeed.solarforge.world_generation.features.flowers.solar_flowers_feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.DefaultFlowerFeature;

import java.util.Random;

public class SolarFlowerFeature extends DefaultFlowerFeature {
    public SolarFlowerFeature(Codec<RandomPatchConfiguration> p_i231945_1_) {
        super(p_i231945_1_);
    }


    @Override
    public BlockState getRandomFlower(Random p_225562_1_, BlockPos p_225562_2_, RandomPatchConfiguration p_225562_3_) {
        return p_225562_3_.stateProvider.getState(p_225562_1_, p_225562_2_);
    }
}
