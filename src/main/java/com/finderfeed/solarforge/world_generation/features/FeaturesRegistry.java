package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.biomes.molten_forest.MoltenForestAmbience;
import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.CrystallizedOreVeinFeature;
import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.RadiantSmallTreeFoliagePlacer;
import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.RadiantTreeFoliagePlacer;

import com.finderfeed.solarforge.world_generation.features.foliage_placers.BurntTreeFoliagePlacer;
import com.finderfeed.solarforge.world_generation.features.foliage_placers.FoliagePlacerRegistry;
import com.finderfeed.solarforge.world_generation.features.trunk_placers.BurntTreeTrunkPlacer;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantInt;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;


import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;



import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraftforge.registries.ForgeRegistries;


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
    public static final Feature<NoneFeatureConfiguration> CRYSTAL_CAVE_ORE_CRYSTAL = new CrystalCaveOreCrystal(NoneFeatureConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> CEILING_FLOOR_CRYSTALS = new WallCrystalsCrystalCave(NoneFeatureConfiguration.CODEC);
    public static final Feature<SimpleBlockConfiguration> STONE_FLOWERS = new StoneFlowersFeature(SimpleBlockConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> CEILING_DRIPSTONE_LIKE_CRYSTALS = new CeilingDripstoneLikeCrystals(NoneFeatureConfiguration.CODEC);
    public static final Feature<SimpleBlockConfiguration> CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS = new CrystallizedRunicEnergyCrystalsFeature(SimpleBlockConfiguration.CODEC);

    public static ConfiguredFeature<?,?> RADIANT_TREE_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> RADIANT_SMALL_TREE_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> ENERGY_PYLON_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> MOLTEN_FOREST_RUINS_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> RANDOM_PATCH_RADIANT_GRASS_CONF;
    public static ConfiguredFeature<?,?> FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF;
    public static ConfiguredFeature<?,?> RADIANT_BERRY_BUSH_CONF;
    public static ConfiguredFeature<?,?> ENDER_CRACKS_CONF;
    public static ConfiguredFeature<?,?> LENSING_CRYSTAL_ORE_CONF;
    public static ConfiguredFeature<?,?> CRYSTAL_CAVE_ORE_CRYSTAL_CONF;
    public static ConfiguredFeature<?,?> CEILING_FLOOR_CRYSTALS_CONF;
    public static ConfiguredFeature<?,?> CRYSTAL_FLOWER_CONF;
    public static ConfiguredFeature<?,?> CEILING_DRIPSTONE_LIKE_CRYSTALS_CONF;
    public static ConfiguredFeature<?,?> CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_CONF;
    public static ConfiguredFeature<?,?> LUNAR_LILY_FEATURE_CONF;

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
    public static PlacedFeature LENSING_CRYSTAL_ORE_PLACEMENT;
    public static PlacedFeature CRYSTAL_CAVE_ORE_CRYSTAL_PLACEMENT;
    public static PlacedFeature CEILING_FLOOR_CRYSTALS_PLACEMENT;
    public static PlacedFeature CRYSTAL_FLOWER_PLACEMENT;
    public static PlacedFeature CEILING_DRIPSTONE_LIKE_CRYSTALS_PLACEMENT;
    public static PlacedFeature CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_PLACEMENT;
    public static PlacedFeature LUNAR_LILY_FEATURE_PLACEMENT;
    //public static ConfiguredFeature<?,?> RADIANT_LAND_AMBIENT_TREE;



    public static final ConfiguredFeature<?,?> BURNT_BIOME_AMBIENCE_1_CONFIGURED = BURNT_BIOME_AMBIENCE_1
            .configured(NoneFeatureConfiguration.INSTANCE);
//            .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(4)

    public static PlacedFeature BURNT_BIOME_AMBIENECE_PLACED_FEATURE;

    public static final ConfiguredFeature<?,?> ULDORADIUM_ORE =
            Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, BlocksRegistry.ULDORADIUM_ORE.get().defaultBlockState(),6));
//            .rangeUniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(60)).squared().count(6);

    public static PlacedFeature ULDORADIUM_ORE_PLACED_FEATURE;

    public static ConfiguredFeature<?,?> RUNIC_TREE_FEATURE_CONF;

    public static PlacedFeature RUNIC_TREE_FEATURE;

    public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event){
        event.getRegistry().register(BURNT_BIOME_AMBIENCE_1.setRegistryName(BURNT_BIOME_BURNT_TREE));
        event.getRegistry().register(BURNT_BIOME_AMBIENCE_2.setRegistryName(new ResourceLocation(SolarForge.MOD_ID,"ruins_feature")));
        event.getRegistry().register(ENERGY_PYLON.setRegistryName(new ResourceLocation(SolarForge.MOD_ID,"energy_pylon_feature")));
        event.getRegistry().register(FLOATING_ISLANDS_RADIANT_LAND.setRegistryName(new ResourceLocation(SolarForge.MOD_ID,"floating_islands")));
        event.getRegistry().register(CRYSTALLIZED_ORE_VEIN_RADIANT_LAND.setRegistryName(new ResourceLocation(SolarForge.MOD_ID,"crystallized_ore_vein")));
        event.getRegistry().register(CRYSTAL_CAVE_ORE_CRYSTAL.setRegistryName(new ResourceLocation(SolarForge.MOD_ID,"crystal_cave_ore_crystal")));
        event.getRegistry().register(CEILING_FLOOR_CRYSTALS.setRegistryName(new ResourceLocation(SolarForge.MOD_ID,"ceiling_floor_crystals")));
        event.getRegistry().register(STONE_FLOWERS.setRegistryName(new ResourceLocation(SolarForge.MOD_ID,"stone_flowers")));
        event.getRegistry().register(CEILING_DRIPSTONE_LIKE_CRYSTALS.setRegistryName(new ResourceLocation(SolarForge.MOD_ID,"ceiling_dripstonelike_crystals")));
        registerFeature(event,CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS,"crystallized_runic_energy");
    }
    private static void registerFeature(RegistryEvent.Register<Feature<?>> event,Feature<?> f,String name){
        event.getRegistry().register(f.setRegistryName(new ResourceLocation(SolarForge.MOD_ID,name)));

    }


    public static void addCarvableBlocks(FMLCommonSetupEvent event){
        event.enqueueWork(()-> {
            WorldCarver<?> carver = ForgeRegistries.WORLD_CARVERS.getValue(new ResourceLocation("minecraft", "cave"));
            if (carver != null) {
                ImmutableSet.Builder<Block> builder = new ImmutableSet.Builder<>();
                builder.addAll(carver.replaceableBlocks);
                builder.add(BlocksRegistry.RADIANT_GRASS.get());
                carver.replaceableBlocks = builder.build();
            }
        });
    }

    public static void registerConfiguredFeatures(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{

            Registry.register(Registry.FOLIAGE_PLACER_TYPES,new ResourceLocation("solarforge","burnt_tree_foliage"), FoliagePlacerRegistry.BURNT_TREE_PLACER);
            Registry.register(Registry.FOLIAGE_PLACER_TYPES,new ResourceLocation("solarforge","radiant_tree_foliage"), FoliagePlacerRegistry.RADIANT_PLACER);
            Registry.register(Registry.FOLIAGE_PLACER_TYPES,new ResourceLocation("solarforge","radiant_tree_small_foliage"), FoliagePlacerRegistry.RADIANT_SMALL_PLACER);
            BURNT_TREE_FEATURE_2_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlocksRegistry.BURNT_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    BlockStateProvider.simple(BlocksRegistry.ASH_LEAVES.get().defaultBlockState()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines().build());

            BURNT_TREE_2 = BURNT_TREE_FEATURE_2_CONF.placed(
                    PlacementUtils.countExtra(10,0.1f,1),
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    InSquarePlacement.spread(),
                    NoiseBasedCountPlacement.of(3,2,3),
                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));


            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_tree_feature2_configured"), BURNT_TREE_FEATURE_2_CONF);
            registerPlacedFeature(BURNT_TREE_2,"burnt_tree_feature2");

            BURNT_TREE_FEATURE_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlocksRegistry.BURNT_LOG.get().defaultBlockState()),
                    new BurntTreeTrunkPlacer(5, 3, 0),
                    BlockStateProvider.simple(BlocksRegistry.ASH_LEAVES.get().defaultBlockState()),
                    new BurntTreeFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines().build());

            BURNT_TREE_1 = BURNT_TREE_FEATURE_CONF.placed(HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    InSquarePlacement.spread(),
                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_tree_feature_configured"),BURNT_TREE_FEATURE_CONF);
            registerPlacedFeature(BURNT_TREE_1,"burnt_tree_feature");


            BURNT_BIOME_AMBIENECE_PLACED_FEATURE = BURNT_BIOME_AMBIENCE_1_CONFIGURED.placed(RarityFilter.onAverageOnceEvery(4));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_biome_ambience_1_configured"),BURNT_BIOME_AMBIENCE_1_CONFIGURED);
            registerPlacedFeature(BURNT_BIOME_AMBIENECE_PLACED_FEATURE,"burnt_biome_ambience_1");

            ULDORADIUM_ORE_PLACED_FEATURE = ULDORADIUM_ORE.placed(HeightRangePlacement.uniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(60)),InSquarePlacement.spread());


            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","uldoradium_ore_configured"),ULDORADIUM_ORE);
            registerPlacedFeature(ULDORADIUM_ORE_PLACED_FEATURE,"uldoradium_ore");

            ENERGY_PYLON_CONFIGURED_CONF = ENERGY_PYLON.configured(NoneFeatureConfiguration.INSTANCE);
            ENERGY_PYLON_CONFIGURED = ENERGY_PYLON_CONFIGURED_CONF.placed(RarityFilter.onAverageOnceEvery(200));


            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","energy_pylon_configured"), ENERGY_PYLON_CONFIGURED_CONF);
            registerPlacedFeature(ENERGY_PYLON_CONFIGURED,"energy_pylon");

            RUNIC_TREE_FEATURE_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlocksRegistry.RUNIC_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    BlockStateProvider.simple(BlocksRegistry.RUNIC_LEAVES.get().defaultBlockState()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines().build());

            RUNIC_TREE_FEATURE = RUNIC_TREE_FEATURE_CONF.placed(HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    RarityFilter.onAverageOnceEvery(100),
                    InSquarePlacement.spread(),
                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","runic_tree_configured"),RUNIC_TREE_FEATURE_CONF);
            registerPlacedFeature(RUNIC_TREE_FEATURE,"runic_tree");

            MOLTEN_FOREST_RUINS_CONFIGURED_CONF = BURNT_BIOME_AMBIENCE_2.configured(NoneFeatureConfiguration.INSTANCE);

            //TODO:switch back to 60 when incinerated forest returns
            MOLTEN_FOREST_RUINS_CONFIGURED = MOLTEN_FOREST_RUINS_CONFIGURED_CONF.placed(RarityFilter.onAverageOnceEvery(120));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","configured_ruins_configured"), MOLTEN_FOREST_RUINS_CONFIGURED_CONF);
            registerPlacedFeature(MOLTEN_FOREST_RUINS_CONFIGURED,"configured_ruins");

            RADIANT_TREE_CONFIGURED_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlocksRegistry.RADIANT_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(15, 1, 0),
                    BlockStateProvider.simple(BlocksRegistry.RADIANT_LEAVES.get().defaultBlockState()),
                    new RadiantTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 1)
            ).build());

            RADIANT_TREE_CONFIGURED = RADIANT_TREE_CONFIGURED_CONF.placed(
                    PlacementUtils.countExtra(3,0.1f,2),
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    InSquarePlacement.spread(),
                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","radiant_tree_configured"), RADIANT_TREE_CONFIGURED_CONF);
            registerPlacedFeature(RADIANT_TREE_CONFIGURED,"radiant_tree");

            RANDOM_PATCH_RADIANT_GRASS_CONF = Feature.RANDOM_PATCH.configured(
                    FeatureUtils.simpleRandomPatchConfiguration(1,
                            Feature.SIMPLE_BLOCK.configured(
                            new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.RADIANT_GRASS_NOT_BLOCK.get()))).onlyWhenEmpty()));

            RANDOM_PATCH_RADIANT_GRASS = RANDOM_PATCH_RADIANT_GRASS_CONF.placed(
                    CountPlacement.of(60), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","radiant_grass_grass_configured"), RANDOM_PATCH_RADIANT_GRASS_CONF);
            registerPlacedFeature(RANDOM_PATCH_RADIANT_GRASS,"radiant_grass_grass");

            FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF = FLOATING_ISLANDS_RADIANT_LAND.configured(NoneFeatureConfiguration.INSTANCE);

            FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED = FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF.placed(RarityFilter.onAverageOnceEvery(10),
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(100),VerticalAnchor.absolute(130)),
                    InSquarePlacement.spread());

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","floating_islands_configured"), FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF);
            registerPlacedFeature(FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED,"floating_islands");
            RADIANT_SMALL_TREE_CONFIGURED_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BlocksRegistry.RADIANT_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(9, 1, 0),
                    BlockStateProvider.simple(BlocksRegistry.RADIANT_LEAVES.get().defaultBlockState()),
                    new RadiantSmallTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines().build());



            RADIANT_SMALL_TREE_CONFIGURED = RADIANT_SMALL_TREE_CONFIGURED_CONF.placed(
                    RarityFilter.onAverageOnceEvery(3),
                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)),
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    InSquarePlacement.spread());

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","radiant_land_ambient_tree_configured"), RADIANT_SMALL_TREE_CONFIGURED_CONF);
            registerPlacedFeature(RADIANT_SMALL_TREE_CONFIGURED,"radiant_land_ambient_tree");
            CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF = CRYSTALLIZED_ORE_VEIN_RADIANT_LAND.configured(NoneFeatureConfiguration.INSTANCE);

            CRYSTALLIZED_ORE_VEIN_CONFIGURED = CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF.placed(
                    RarityFilter.onAverageOnceEvery(25),
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    InSquarePlacement.spread()
            );

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","crystallized_ore_vein_configured"), CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF);
            registerPlacedFeature(CRYSTALLIZED_ORE_VEIN_CONFIGURED,"crystallized_ore_vein");
//            (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlocksRegistry.RADIANT_BERRY_BUSH.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(4).build()
            RADIANT_BERRY_BUSH_CONF =  Feature.RANDOM_PATCH.configured(FeatureUtils.simpleRandomPatchConfiguration(4,Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.RADIANT_BERRY_BUSH.get()))).onlyWhenEmpty()));
            RADIANT_BERRY_BUSH = RADIANT_BERRY_BUSH_CONF.placed(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","radiant_berry_bush_configured"), RADIANT_BERRY_BUSH_CONF);
            registerPlacedFeature(RADIANT_BERRY_BUSH,"radiant_berry_bush");

            ENDER_CRACKS_CONF = Feature.ORE.configured(new OreConfiguration(END_STONE, BlocksRegistry.ENDER_CRACKS.get().defaultBlockState(),5));
            ENDER_CRACKS = ENDER_CRACKS_CONF.placed(HeightRangePlacement.uniform(VerticalAnchor.absolute(30),VerticalAnchor.absolute(100)));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","ender_cracks_configured"), ENDER_CRACKS_CONF);
            registerPlacedFeature(ENDER_CRACKS,"ender_cracks");


            CRYSTAL_CAVE_ORE_CRYSTAL_CONF = CRYSTAL_CAVE_ORE_CRYSTAL.configured(NoneFeatureConfiguration.INSTANCE);
            CRYSTAL_CAVE_ORE_CRYSTAL_PLACEMENT = CRYSTAL_CAVE_ORE_CRYSTAL_CONF.placed(
                    RarityFilter.onAverageOnceEvery(2),
                    CountPlacement.of(UniformInt.of(10,15)),InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
            );
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","crystal_cave_ore_crystal"), CRYSTAL_CAVE_ORE_CRYSTAL_CONF);
            registerPlacedFeature(CRYSTAL_CAVE_ORE_CRYSTAL_PLACEMENT,"crystal_cave_ore_crystal");

            CEILING_FLOOR_CRYSTALS_CONF = CEILING_FLOOR_CRYSTALS.configured(NoneFeatureConfiguration.INSTANCE);
            CEILING_FLOOR_CRYSTALS_PLACEMENT = CEILING_FLOOR_CRYSTALS_CONF.placed(
                    CountPlacement.of(UniformInt.of(13,17)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
              BiomeFilter.biome()
            );

            registerConfiguredFeature(CEILING_FLOOR_CRYSTALS_CONF,"ceiling_floor_crystals");
            registerPlacedFeature(CEILING_FLOOR_CRYSTALS_PLACEMENT,"ceiling_floor_crystals");


            CRYSTAL_FLOWER_CONF =
                    Feature.FLOWER.configured(FeatureUtils.simpleRandomPatchConfiguration(7,STONE_FLOWERS.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.CRYSTAL_FLOWER.get()))).onlyWhenEmpty()));
            CRYSTAL_FLOWER_PLACEMENT = CRYSTAL_FLOWER_CONF.placed(
              CountPlacement.of(UniformInt.of(50,60)),PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,BiomeFilter.biome()
            );
            registerConfiguredFeature(CRYSTAL_FLOWER_CONF,"crystal_flower");
            registerPlacedFeature(CRYSTAL_FLOWER_PLACEMENT,"crystal_flower");

            CEILING_DRIPSTONE_LIKE_CRYSTALS_CONF = CEILING_DRIPSTONE_LIKE_CRYSTALS.configured(NoneFeatureConfiguration.INSTANCE);
            CEILING_DRIPSTONE_LIKE_CRYSTALS_PLACEMENT = CEILING_DRIPSTONE_LIKE_CRYSTALS_CONF.placed(
                    CountPlacement.of(UniformInt.of(20,30)),InSquarePlacement.spread(),PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,BiomeFilter.biome()
            );

            registerPlacedFeature(CEILING_DRIPSTONE_LIKE_CRYSTALS_PLACEMENT,"ceiling_dripstonelike_crystals");
            registerConfiguredFeature(CEILING_DRIPSTONE_LIKE_CRYSTALS_CONF,"ceiling_dripstonelike_crystals");

            CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_CONF = CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.CRYSTALLIZED_RUNIC_ENERGY.get())));
            CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_PLACEMENT = CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_CONF.placed(
                    CountPlacement.of(UniformInt.of(60,100)),InSquarePlacement.spread(),HeightRangePlacement.uniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(100)),BiomeFilter.biome()
            );

            registerConfiguredFeature(CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_CONF,"crystallized_runic_energy");
            registerPlacedFeature(CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_PLACEMENT,"crystallized_runic_energy");

            LENSING_CRYSTAL_ORE_CONF = Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE,BlocksRegistry.LENSING_CRYSTAL_ORE.get().defaultBlockState(),4));
            LENSING_CRYSTAL_ORE_PLACEMENT = LENSING_CRYSTAL_ORE_CONF.placed(
              CountPlacement.of(UniformInt.of(4,6)),InSquarePlacement.spread(),HeightRangePlacement.uniform(VerticalAnchor.absolute(10),VerticalAnchor.absolute(50))
            );

            registerConfiguredFeature(LENSING_CRYSTAL_ORE_CONF,"lensing_crystal_ore");
            registerPlacedFeature(LENSING_CRYSTAL_ORE_PLACEMENT,"lensing_crystal_ore");

            LUNAR_LILY_FEATURE_CONF = Feature.FLOWER.configured(FeatureUtils.simpleRandomPatchConfiguration(3,Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.LUNAR_LILY.get()))).onlyWhenEmpty()));
            LUNAR_LILY_FEATURE_PLACEMENT = LUNAR_LILY_FEATURE_CONF.placed(NoiseThresholdCountPlacement.of(-0.8D, 15, 4), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);

            registerConfiguredFeature(LUNAR_LILY_FEATURE_CONF,"lunar_lily");
            registerPlacedFeature(LUNAR_LILY_FEATURE_PLACEMENT,"lunar_lily");
        });
    }



    private static void registerConfiguredFeature(ConfiguredFeature<?,?> feature,String registryid){
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation(SolarForge.MOD_ID,registryid),feature);
    }

    private static void registerPlacedFeature(PlacedFeature feature,String registryid){
        Registry.register(BuiltinRegistries.PLACED_FEATURE,new ResourceLocation(SolarForge.MOD_ID,registryid),feature);
    }

}
