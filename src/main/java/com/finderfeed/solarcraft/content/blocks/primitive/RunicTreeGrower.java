package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.world_generation.features.FeaturesRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class RunicTreeGrower extends AbstractTreeGrower {

    public static final ResourceKey<ConfiguredFeature<?, ?>> RUNIC_TREE_FEATURE_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(SolarCraft.MOD_ID,"runic_tree"));
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource src, boolean p_222911_) {
        return RUNIC_TREE_FEATURE_KEY;
    }
}
