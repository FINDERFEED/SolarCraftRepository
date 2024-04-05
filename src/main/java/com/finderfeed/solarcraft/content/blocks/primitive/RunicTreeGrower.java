package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.world_generation.features.FeaturesRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class RunicTreeGrower {

    public static final ResourceKey<ConfiguredFeature<?, ?>> RUNIC_TREE_FEATURE_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(SolarCraft.MOD_ID,"runic_tree"));

    public static final TreeGrower GROWER = new TreeGrower("runic_tree", Optional.empty(), Optional.of(RUNIC_TREE_FEATURE_KEY), Optional.of(RUNIC_TREE_FEATURE_KEY));

    //    @Override
//    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource src, boolean p_222911_) {
//        return RUNIC_TREE_FEATURE_KEY;
//    }
}
