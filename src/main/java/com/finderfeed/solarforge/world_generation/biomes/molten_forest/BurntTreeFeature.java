package com.finderfeed.solarforge.world_generation.biomes.molten_forest;

import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import com.finderfeed.solarforge.world_generation.features.foliage_placers.BurntTreeFoliagePlacer;
import com.finderfeed.solarforge.world_generation.features.trunk_placers.BurntTreeTrunkPlacer;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;

import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.NoiseCountFactorDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;

public class BurntTreeFeature {
    public static final ConfiguredFeature<?,?> BURNT_TREE_FEATURE_2 = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlocksRegistry.BURNT_LOG.get().defaultBlockState()),
            new StraightTrunkPlacer(4, 2, 0),
            new SimpleStateProvider(BlocksRegistry.ASH_LEAVES.get().defaultBlockState()),
            new SimpleStateProvider(Blocks.AIR.defaultBlockState()),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
            new TwoLayersFeatureSize(1, 0, 1))
            .ignoreVines().build())
            .decorated(FeatureDecorator.SQUARE.configured(NoneDecoratorConfiguration.INSTANCE))
            .decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(20,30,35)))
            .decorated( FeatureDecorator.COUNT_NOISE_BIASED.configured(new NoiseCountFactorDecoratorConfiguration(3,2,3)));


    public static final ConfiguredFeature<?,?> BURNT_TREE_FEATURE = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlocksRegistry.BURNT_LOG.get().defaultBlockState()),
            new SimpleStateProvider(BlocksRegistry.ASH_LEAVES.get().defaultBlockState()),
            new BurntTreeFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
            new BurntTreeTrunkPlacer(5, 3, 0),
            new TwoLayersFeatureSize(1, 0, 1))
            .ignoreVines().build())
            .decorated(FeatureDecorator.SQUARE.configured(NoneDecoratorConfiguration.INSTANCE))
            .decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(20,30,35)));
}
