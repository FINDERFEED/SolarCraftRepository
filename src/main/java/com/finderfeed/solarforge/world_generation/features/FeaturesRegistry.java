package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.biomes.molten_forest.MoltenForestAmbience;
import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.CrystallizedOreVeinFeature;
import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.RadiantSmallTreeFoliagePlacer;
import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.RadiantTreeFoliagePlacer;

import com.finderfeed.solarforge.world_generation.features.foliage_placers.BurntTreeFoliagePlacer;
import com.finderfeed.solarforge.world_generation.features.trunk_placers.BurntTreeTrunkPlacer;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantInt;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;

import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;



import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;



//i hate you with every ounce of being mojang!
public class FeaturesRegistry {



    public static ConfiguredFeature<?,?> BURNT_TREE_FEATURE_2_CONF;


    public static ConfiguredFeature<?,?> BURNT_TREE_FEATURE_CONF;




    public static final RuleTest END_STONE = new TagMatchTest(Tags.Blocks.END_STONES);

    public static final ResourceLocation BURNT_BIOME_BURNT_TREE = new ResourceLocation("solarforge","burnt_biome_tree");
    public static final ResourceLocation MOLTEN_FOREST_BIOME = new ResourceLocation("solarforge","incinerated_forest");
    public static final ResourceKey<Biome> MOLTEN_BIOME_KEY = ResourceKey.create(Registry.BIOME_REGISTRY,MOLTEN_FOREST_BIOME);


    public static final Feature<NoneFeatureConfiguration> BURNT_BIOME_AMBIENCE_1 = new MoltenForestAmbience(NoneFeatureConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> BURNT_BIOME_AMBIENCE_2 = new MoltenForestRuins(NoneFeatureConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> ENERGY_PYLON = new EnergyPylonFeature(NoneFeatureConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> FLOATING_ISLANDS_RADIANT_LAND = new RadiantLandFloatingIslands(NoneFeatureConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> CRYSTALLIZED_ORE_VEIN_RADIANT_LAND = new CrystallizedOreVeinFeature(NoneFeatureConfiguration.CODEC);

    public static ConfiguredFeature<?,?> RADIANT_TREE_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> RADIANT_SMALL_TREE_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> ENERGY_PYLON_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> MOLTEN_FOREST_RUINS_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> RANDOM_PATCH_RADIANT_GRASS_CONF;
    public static ConfiguredFeature<?,?> FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> RADIANT_BERRY_BUSH_CONF;
    public static ConfiguredFeature<?,?> ENDER_CRACKS_CONF;

    public static PlacedFeature BURNT_TREE_2;
    public static PlacedFeature BURNT_TREE_1;
    public static PlacedFeature RADIANT_TREE_CONFIGURED;
    public static PlacedFeature RADIANT_SMALL_TREE_CONFIGURED;
    public static PlacedFeature ENERGY_PYLON_CONFIGURED;
    public static PlacedFeature MOLTEN_FOREST_RUINS_CONFIGURED;
    public static PlacedFeature RANDOM_PATCH_RADIANT_GRASS;
    public static PlacedFeature FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED;
    public static PlacedFeature CRYSTALLIZED_ORE_VEIN_CONFIGURED;
    public static PlacedFeature RADIANT_BERRY_BUSH;
    public static PlacedFeature ENDER_CRACKS;
    //public static ConfiguredFeature<?,?> RADIANT_LAND_AMBIENT_TREE;



    public static final ConfiguredFeature<?,?> BURNT_BIOME_AMBIENCE_1_CONFIGURED = BURNT_BIOME_AMBIENCE_1
            .configured(NoneFeatureConfiguration.INSTANCE);
//            .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(4)



    public static final ConfiguredFeature<?,?> ULDORADIUM_ORE =
            Feature.ORE.configured(new OreConfiguration(new TagMatchTest(Tags.Blocks.STONE), BlocksRegistry.ULDORADIUM_ORE.get().defaultBlockState(),7));
//            .rangeUniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(60)).squared().count(6);

    public static ConfiguredFeature<?,?> RUNIC_TREE_FEATURE_CONF;

    public static PlacedFeature RUNIC_TREE_FEATURE;

    public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event){
        event.getRegistry().register(BURNT_BIOME_AMBIENCE_1.setRegistryName(BURNT_BIOME_BURNT_TREE));
        event.getRegistry().register(BURNT_BIOME_AMBIENCE_2.setRegistryName(new ResourceLocation("solarforge","ruins_feature")));
        event.getRegistry().register(ENERGY_PYLON.setRegistryName(new ResourceLocation("solarforge","energy_pylon_feature")));
        event.getRegistry().register(FLOATING_ISLANDS_RADIANT_LAND.setRegistryName(new ResourceLocation("solarforge","floating_islands")));
        event.getRegistry().register(CRYSTALLIZED_ORE_VEIN_RADIANT_LAND.setRegistryName(new ResourceLocation("solarforge","crystallized_ore_vein")));
    }


    public static void registerConfiguredFeatures(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            BURNT_TREE_FEATURE_2_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlocksRegistry.BURNT_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    BlockStateProvider.simple(BlocksRegistry.ASH_LEAVES.get().defaultBlockState()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines().build());
//                    .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.WORLD_SURFACE_WG)))
//                    .decorated(FeatureDecorator.SQUARE.configured(NoneDecoratorConfiguration.INSTANCE))
//                    .decorated( FeatureDecorator.COUNT_NOISE_BIASED.configured(new NoiseCountFactorDecoratorConfiguration(3,2,3)));

            BURNT_TREE_2 = BURNT_TREE_FEATURE_2_CONF.placed(HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    InSquarePlacement.spread(),
                    NoiseBasedCountPlacement.of(3,2,3));

            registerPlacedFeature(BURNT_TREE_2,"burnt_tree_feature2");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_tree_feature2"), BURNT_TREE_FEATURE_2_CONF);

            BURNT_TREE_FEATURE_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlocksRegistry.BURNT_LOG.get().defaultBlockState()),
                    new BurntTreeTrunkPlacer(5, 3, 0),
                    BlockStateProvider.simple(BlocksRegistry.ASH_LEAVES.get().defaultBlockState()),
                    new BurntTreeFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines().build());
//                    .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.WORLD_SURFACE_WG)))
//                    .decorated(FeatureDecorator.SQUARE.configured(NoneDecoratorConfiguration.INSTANCE));

            BURNT_TREE_1 = BURNT_TREE_FEATURE_CONF.placed(HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    InSquarePlacement.spread());
            registerPlacedFeature(BURNT_TREE_1,"burnt_tree_feature");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_tree_feature"),BURNT_TREE_FEATURE_CONF);

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_biome_ambience_1"),BURNT_BIOME_AMBIENCE_1_CONFIGURED);
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","uldoradium_ore"),ULDORADIUM_ORE);
            ENERGY_PYLON_CONFIGURED_CONF = ENERGY_PYLON.configured(NoneFeatureConfiguration.INSTANCE);
//            .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(200)));
            ENERGY_PYLON_CONFIGURED = ENERGY_PYLON_CONFIGURED_CONF.placed(RarityFilter.onAverageOnceEvery(200));

            registerPlacedFeature(ENERGY_PYLON_CONFIGURED,"energy_pylon");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","energy_pylon"), ENERGY_PYLON_CONFIGURED_CONF);
            RUNIC_TREE_FEATURE_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlocksRegistry.RUNIC_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    BlockStateProvider.simple(BlocksRegistry.RUNIC_LEAVES.get().defaultBlockState()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines().build());

//                    .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.WORLD_SURFACE_WG)))
//                    .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(100)));
            RUNIC_TREE_FEATURE = RUNIC_TREE_FEATURE_CONF.placed(HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),RarityFilter.onAverageOnceEvery(100));
            registerPlacedFeature(RUNIC_TREE_FEATURE,"runic_tree");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","runic_tree"),RUNIC_TREE_FEATURE_CONF);

            MOLTEN_FOREST_RUINS_CONFIGURED_CONF = BURNT_BIOME_AMBIENCE_2.configured(NoneFeatureConfiguration.INSTANCE);
//                    .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(60)));

            MOLTEN_FOREST_RUINS_CONFIGURED = MOLTEN_FOREST_RUINS_CONFIGURED_CONF.placed(RarityFilter.onAverageOnceEvery(60));
            registerPlacedFeature(MOLTEN_FOREST_RUINS_CONFIGURED,"configured_ruins");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","configured_ruins"), MOLTEN_FOREST_RUINS_CONFIGURED_CONF);

            RADIANT_TREE_CONFIGURED_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlocksRegistry.RADIANT_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(15, 1, 0),
                    BlockStateProvider.simple(BlocksRegistry.RADIANT_LEAVES.get().defaultBlockState()),
                    new RadiantTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 1)
            ).build());
//                    .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.WORLD_SURFACE_WG)))
//                    .decorated(FeatureDecorator.SQUARE.configured(NoneDecoratorConfiguration.INSTANCE));
            RADIANT_TREE_CONFIGURED = RADIANT_TREE_CONFIGURED_CONF.placed(HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), InSquarePlacement.spread());
            registerPlacedFeature(RADIANT_TREE_CONFIGURED,"radiant_tree");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","radiant_tree"), RADIANT_TREE_CONFIGURED_CONF);

            RANDOM_PATCH_RADIANT_GRASS_CONF = Feature.RANDOM_PATCH.configured(FeatureUtils.simpleRandomPatchConfiguration(1,Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.RADIANT_GRASS_NOT_BLOCK.get()))).onlyWhenEmpty()));
//                    .decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE)
//                    .decorated(FeatureDecorator.COUNT_NOISE.configured(new NoiseDependantDecoratorConfiguration(-0.8D, 5, 10)));

            RANDOM_PATCH_RADIANT_GRASS = RANDOM_PATCH_RADIANT_GRASS_CONF.placed(NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
            registerPlacedFeature(RANDOM_PATCH_RADIANT_GRASS,"radiant_grass_grass");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","radiant_grass_grass"), RANDOM_PATCH_RADIANT_GRASS_CONF);

            FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF = FLOATING_ISLANDS_RADIANT_LAND.configured(NoneFeatureConfiguration.INSTANCE);
//                    .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(10)))
//                    .decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.absolute(100),VerticalAnchor.absolute(130)))))
//                    .decorated(FeatureDecorator.SQUARE.configured(NoneDecoratorConfiguration.INSTANCE));

            FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED = FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF.placed(RarityFilter.onAverageOnceEvery(10),
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(100),VerticalAnchor.absolute(130)),
                    InSquarePlacement.spread());
            registerPlacedFeature(FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED,"floating_islands");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","floating_islands"), FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF);

            RADIANT_SMALL_TREE_CONFIGURED_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlocksRegistry.RADIANT_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(9, 1, 0),
                    BlockStateProvider.simple(BlocksRegistry.RADIANT_LEAVES.get().defaultBlockState()),
                    new RadiantSmallTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines().build());
//                    .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(8)))
//                    .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.WORLD_SURFACE_WG)))
//                    .decorated(FeatureDecorator.SQUARE.configured(NoneDecoratorConfiguration.INSTANCE))
//                    .decorated( FeatureDecorator.COUNT_NOISE_BIASED.configured(new NoiseCountFactorDecoratorConfiguration(14,5,2)));



            RADIANT_SMALL_TREE_CONFIGURED = RADIANT_SMALL_TREE_CONFIGURED_CONF.placed(RarityFilter.onAverageOnceEvery(8),
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    InSquarePlacement.spread(),
                    NoiseBasedCountPlacement.of(14,5,2));
            registerPlacedFeature(RADIANT_SMALL_TREE_CONFIGURED,"radiant_land_ambient_tree");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","radiant_land_ambient_tree"), RADIANT_SMALL_TREE_CONFIGURED_CONF);

            CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF = CRYSTALLIZED_ORE_VEIN_RADIANT_LAND.configured(NoneFeatureConfiguration.INSTANCE);
//                    .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(25)))
//                    .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.WORLD_SURFACE_WG)))
//                    .decorated(FeatureDecorator.SQUARE.configured(NoneDecoratorConfiguration.INSTANCE));
            CRYSTALLIZED_ORE_VEIN_CONFIGURED = CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF.placed(
                    RarityFilter.onAverageOnceEvery(25),
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    InSquarePlacement.spread()
            );
            registerPlacedFeature(CRYSTALLIZED_ORE_VEIN_CONFIGURED,"crystallized_ore_vein");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","crystallized_ore_vein"), CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF);

//            (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlocksRegistry.RADIANT_BERRY_BUSH.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(4).build()
            RADIANT_BERRY_BUSH_CONF =  Feature.RANDOM_PATCH.configured(FeatureUtils.simpleRandomPatchConfiguration(4,Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.RADIANT_BERRY_BUSH.get()))).onlyWhenEmpty()));
            RADIANT_BERRY_BUSH = RADIANT_BERRY_BUSH_CONF.placed();
            registerPlacedFeature(RADIANT_BERRY_BUSH,"radiant_berry_bush");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","radiant_berry_bush"), RADIANT_BERRY_BUSH_CONF);


            ENDER_CRACKS_CONF = Feature.ORE.configured(new OreConfiguration(END_STONE, BlocksRegistry.ENDER_CRACKS.get().defaultBlockState(),4));
//                    .rangeUniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(200)).squared().count(15);
            ENDER_CRACKS = ENDER_CRACKS_CONF.placed(HeightRangePlacement.uniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(200)));
            registerPlacedFeature(ENDER_CRACKS,"ender_cracks");
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","ender_cracks"), ENDER_CRACKS_CONF);
        });
    }
    private static void registerConfiguredFeature(ConfiguredFeature<?,?> feature,String registryid){
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation(SolarForge.MOD_ID,registryid),feature);
    }

    private static void registerPlacedFeature(PlacedFeature feature,String registryid){
        Registry.register(BuiltinRegistries.PLACED_FEATURE,new ResourceLocation(SolarForge.MOD_ID,registryid),feature);
    }

}
