package com.finderfeed.solarforge.world_generation.biomes.molten_forest;

import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import com.finderfeed.solarforge.world_generation.features.foliage_placers.BurntTreeFoliagePlacer;
import com.finderfeed.solarforge.world_generation.features.trunk_placers.BurntTreeTrunkPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class BurntTreeFeature {
    public static final ConfiguredFeature<?,?> BURNT_TREE_FEATURE_2 = Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlocksRegistry.BURNT_LOG.get().defaultBlockState()),
            new SimpleBlockStateProvider(BlocksRegistry.ASH_LEAVES.get().defaultBlockState()),
            new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3),
            new StraightTrunkPlacer(4, 2, 0),
            new TwoLayerFeature(1, 0, 1))
            .ignoreVines().build())
            .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(20,30,35)))
            .decorated( Placement.COUNT_NOISE_BIASED.configured(new TopSolidWithNoiseConfig(3,2,3)));


    public static final ConfiguredFeature<?,?> BURNT_TREE_FEATURE = Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlocksRegistry.BURNT_LOG.get().defaultBlockState()),
            new SimpleBlockStateProvider(BlocksRegistry.ASH_LEAVES.get().defaultBlockState()),
            new BurntTreeFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0)),
            new BurntTreeTrunkPlacer(5, 3, 0),
            new TwoLayerFeature(1, 0, 1))
            .ignoreVines().build())
            .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(20,30,35)));
}
