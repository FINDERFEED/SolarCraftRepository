package com.finderfeed.solarforge.content.blocks.primitive;

import com.finderfeed.solarforge.content.world_generation.features.FeaturesRegistry;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RunicTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_222910_, boolean p_222911_) {
        return Holder.direct(FeaturesRegistry.RUNIC_TREE_FEATURE_CONF);
    }
}
