package com.finderfeed.solarforge.magic.blocks.primitive;

import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RunicTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random p_204307_, boolean p_204308_) {
        return Holder.direct(FeaturesRegistry.RUNIC_TREE_FEATURE_CONF);
    }
}
