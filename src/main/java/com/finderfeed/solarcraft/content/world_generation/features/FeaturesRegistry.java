package com.finderfeed.solarcraft.content.world_generation.features;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.world_generation.biomes.molten_forest.MoltenForestAmbience;
import com.finderfeed.solarcraft.content.world_generation.dimension_related.radiant_land.CrystallizedOreVeinFeature;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarcraft.registries.worldgen.configured.LazyConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


//I hate you with every ounce of being mojang!
public class FeaturesRegistry {



    public static ConfiguredFeature<TreeConfiguration,?> BURNT_TREE_FEATURE_2_CONF;


    public static ConfiguredFeature<?,?> BURNT_TREE_FEATURE_CONF;




    public static final RuleTest END_STONE = new TagMatchTest(Tags.Blocks.END_STONES);

    public static final ResourceLocation BURNT_BIOME_BURNT_TREE = new ResourceLocation("solarcraft","burnt_biome_tree");
//    public static final ResourceLocation MOLTEN_FOREST_BIOME = new ResourceLocation("solarcraft","incinerated_forest");
//    public static final ResourceKey<Biome> MOLTEN_BIOME_KEY = ResourceKey.create(Registry.BIOME_REGISTRY,MOLTEN_FOREST_BIOME);

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, SolarCraft.MOD_ID);



    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BURNT_BIOME_AMBIENCE_1             = FEATURES.register("burnt_biome_tree",()->new MoltenForestAmbience(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FRAGMENT_RUINS = FEATURES.register("ruins_feature",()->new MoltenForestRuins(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ENERGY_PYLON                       = FEATURES.register("energy_pylon_feature",()->new EnergyPylonFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FLOATING_ISLANDS_RADIANT_LAND      = FEATURES.register("floating_islands",()->new RadiantLandFloatingIslands(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CRYSTALLIZED_ORE_VEIN_RADIANT_LAND = FEATURES.register("crystallized_ore_vein",()->new CrystallizedOreVeinFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CRYSTAL_CAVE_ORE_CRYSTAL           = FEATURES.register("crystal_cave_ore_crystal",()->new CrystalCaveOreCrystal(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CEILING_FLOOR_CRYSTALS             = FEATURES.register("ceiling_floor_crystals",()->new WallCrystalsCrystalCave(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<SimpleBlockConfiguration>> STONE_FLOWERS                      = FEATURES.register("stone_flowers",()->new StoneFlowersFeature(SimpleBlockConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CEILING_DRIPSTONE_LIKE_CRYSTALS    = FEATURES.register("ceiling_dripstonelike_crystals",()->new CeilingDripstoneLikeCrystals(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<SimpleBlockConfiguration>> CRYSTALS_ORE                       = FEATURES.register("crystallized_runic_energy",()->new CrystalsOreFeature(SimpleBlockConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ULDERA_OBELISK                     = FEATURES.register("uldera_obelisk",()->new UlderaObeliskFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ULDERA_PYLON                       = FEATURES.register("uldera_pylon",()->new UlderaPylonFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CLEARING_CRYSTAL                   = FEATURES.register("clearing_crystal",()->new ClearingCrystalFeature(NoneFeatureConfiguration.CODEC));



//
//    public static ConfiguredFeature<?,?> RADIANT_TREE_CONFIGURED_CONF;
//    public static ConfiguredFeature<?,?> RADIANT_SMALL_TREE_CONFIGURED_CONF;
//    public static ConfiguredFeature<?,?> ENERGY_PYLON_CONFIGURED_CONF;
//    public static ConfiguredFeature<?,?> RUINS_CONFIGURED;
//    public static ConfiguredFeature<?,?> RANDOM_PATCH_RADIANT_GRASS_CONF;
//    public static ConfiguredFeature<?,?> FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF;
//    public static ConfiguredFeature<?,?> CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF;
//    public static ConfiguredFeature<?,?> RADIANT_BERRY_BUSH_CONF;
//    public static ConfiguredFeature<?,?> ENDER_CRACKS_CONF;
//    public static ConfiguredFeature<?,?> LENSING_CRYSTAL_ORE_CONF;
//    public static ConfiguredFeature<?,?> CRYSTAL_CAVE_ORE_CRYSTAL_CONF;
//    public static ConfiguredFeature<?,?> CEILING_FLOOR_CRYSTALS_CONF;
//    public static ConfiguredFeature<?,?> CRYSTAL_FLOWER_CONF;
//    public static ConfiguredFeature<?,?> CEILING_DRIPSTONE_LIKE_CRYSTALS_CONF;
//    public static ConfiguredFeature<?,?> CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_CONF;
//    public static ConfiguredFeature<?,?> LUNAR_LILY_FEATURE_CONF;
//    public static ConfiguredFeature<?,?> EMPTY_CRYSTALS_CONF;
//    public static ConfiguredFeature<?,?> SOLAR_ORE_CONF;
//    public static ConfiguredFeature<?,?> SOLAR_STONE_CONF;
//    public static ConfiguredFeature<?,?> ULDERA_OBELISK_CONFIGURED;
//    public static ConfiguredFeature<?,?> ULDERA_PYLON_CONFIGURED;
//    public static ConfiguredFeature<?,?> CLEARING_CRYSTAL_CONFIGURED;
//    public static ConfiguredFeature<?,?> MAGISTONE_ORE;
//
//
//    public static Holder<PlacedFeature> ULDERA_OBELISK_PLACEMENT;
//    public static Holder<PlacedFeature> BURNT_TREE_2;
//    public static Holder<PlacedFeature> BURNT_TREE_1;
//    public static Holder<PlacedFeature> RADIANT_TREE_PLACEMENT;
//    public static Holder<PlacedFeature> RADIANT_SMALL_TREE_PLACEMENT;
//    public static Holder<PlacedFeature> ENERGY_PYLON_PLACEMENT;
//    public static Holder<PlacedFeature> RUINS_PLACEMENT;
//    public static Holder<PlacedFeature> RANDOM_PATCH_RADIANT_GRASS;
//    public static Holder<PlacedFeature> FLOATING_ISLANDS_RADIANT_LAND_PLACEMENT;
//    public static Holder<PlacedFeature> CRYSTALLIZED_ORE_VEIN_CONFIGURED;
//    public static Holder<PlacedFeature> RADIANT_BERRY_BUSH;
//    public static Holder<PlacedFeature> ENDER_CRACKS;
//    public static Holder<PlacedFeature> LENSING_CRYSTAL_ORE_PLACEMENT;
//    public static Holder<PlacedFeature> CRYSTAL_CAVE_ORE_CRYSTAL_PLACEMENT;
//    public static Holder<PlacedFeature> CEILING_FLOOR_CRYSTALS_PLACEMENT;
//    public static Holder<PlacedFeature> CRYSTAL_FLOWER_PLACEMENT;
//    public static Holder<PlacedFeature> CEILING_DRIPSTONE_LIKE_CRYSTALS_PLACEMENT;
//    public static Holder<PlacedFeature> CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_PLACEMENT;
//    public static Holder<PlacedFeature> LUNAR_LILY_FEATURE_PLACEMENT;
//    public static Holder<PlacedFeature> EMPTY_CRYSTALS_PLACEMENT;
//    public static Holder<PlacedFeature> RUNIC_TREE_FEATURE;
//    public static Holder<PlacedFeature> ULDORADIUM_ORE_PLACED_FEATURE;
//    public static Holder<PlacedFeature> BURNT_BIOME_AMBIENECE_PLACED_FEATURE;
//    public static Holder<PlacedFeature> SOLAR_ORE;
//    public static Holder<PlacedFeature> SOLAR_STONE;
//    public static Holder<PlacedFeature> ULDERA_PYLON_PLACEMENT;
//    public static Holder<PlacedFeature> CLEARING_CRYSTAL_PLACEMENT;
//    public static Holder<PlacedFeature> MAGISTONE_ORE_PLACEMENT;
//
//
//
//
//    public static  ConfiguredFeature<?,?> BURNT_BIOME_AMBIENCE_1_CONFIGURED;
//
//
//
//    public static ConfiguredFeature<OreConfiguration,?> ULDORADIUM_ORE;
//
//
//
//    public static ConfiguredFeature<TreeConfiguration,?> RUNIC_TREE_FEATURE_CONF;



    public static void registerConfiguredFeatures(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
//            BURNT_TREE_FEATURE_2_CONF = new ConfiguredFeature<>(Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
//                    BlockStateProvider.simple(SolarcraftBlocks.BURNT_LOG.get().defaultBlockState()),
//                    new StraightTrunkPlacer(4, 2, 0),
//                    BlockStateProvider.simple(SolarcraftBlocks.ASH_LEAVES.get().defaultBlockState()),
//                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
//                    new TwoLayersFeatureSize(1, 0, 1))
//                    .ignoreVines().build());
//
//
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation(SolarCraft.MOD_ID,"burnt_tree_feature2_configured"), BURNT_TREE_FEATURE_2_CONF);
//            BURNT_TREE_2 = registerPlacedFeature("burnt_tree_feature2",Holder.direct(BURNT_TREE_FEATURE_2_CONF),
//                    PlacementUtils.countExtra(10,0.1f,1),
//                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
//                    InSquarePlacement.spread(),
//                    NoiseBasedCountPlacement.of(3,2,3),
//                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(),
//                            BlockPos.ZERO)));
//
//
//            BURNT_TREE_FEATURE_CONF = new ConfiguredFeature<>(Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
//                    BlockStateProvider.simple(SolarcraftBlocks.BURNT_LOG.get().defaultBlockState()),
//                    new BurntTreeTrunkPlacer(5, 3, 0),
//                    BlockStateProvider.simple(SolarcraftBlocks.ASH_LEAVES.get().defaultBlockState()),
//                    new BurntTreeFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
//                    new TwoLayersFeatureSize(1, 0, 1))
//                    .ignoreVines().build());
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","burnt_tree_feature_configured"),BURNT_TREE_FEATURE_CONF);

            //            BURNT_TREE_FEATURE_CONF = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
//                    BlockStateProvider.simple(BlocksRegistry.BURNT_LOG.get().defaultBlockState()),
//                    new BurntTreeTrunkPlacer(5, 3, 0),
//                    BlockStateProvider.simple(BlocksRegistry.ASH_LEAVES.get().defaultBlockState()),
//                    new BurntTreeFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
//                    new TwoLayersFeatureSize(1, 0, 1))
//                    .ignoreVines().build());

//            BURNT_TREE_1 = BURNT_TREE_FEATURE_CONF.placed(HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
//                    InSquarePlacement.spread(),
//                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));
//            registerPlacedFeature(BURNT_TREE_1,"burnt_tree_feature");
//            BURNT_TREE_1 = registerPlacedFeature("burnt_tree_feature",Holder.direct(BURNT_TREE_FEATURE_CONF),HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
//                    InSquarePlacement.spread(),
//                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));

//            = BURNT_BIOME_AMBIENCE_1
//                    .configured(NoneFeatureConfiguration.INSTANCE);
//            BURNT_BIOME_AMBIENCE_1_CONFIGURED = new ConfiguredFeature<>(BURNT_BIOME_AMBIENCE_1.get(),NoneFeatureConfiguration.INSTANCE);
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","burnt_biome_ambience_1_configured"),BURNT_BIOME_AMBIENCE_1_CONFIGURED);
//
//            BURNT_BIOME_AMBIENECE_PLACED_FEATURE = registerPlacedFeature("burnt_biome_ambience_1",Holder.direct(BURNT_BIOME_AMBIENCE_1_CONFIGURED),
//                    RarityFilter.onAverageOnceEvery(4));




//
//            ULDORADIUM_ORE = new ConfiguredFeature<>(Feature.ORE,
//                    new OreConfiguration(OreFeatures.NATURAL_STONE,
//                            SolarcraftBlocks.BLUE_GEM_ORE.get().defaultBlockState(),6));
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","uldoradium_ore_configured"),ULDORADIUM_ORE);
//
//            ULDORADIUM_ORE_PLACED_FEATURE = registerPlacedFeature("blue_gem_ore",Holder.direct(ULDORADIUM_ORE),
//                    HeightRangePlacement.uniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(60)),InSquarePlacement.spread());

//            ENERGY_PYLON_CONFIGURED_CONF = new ConfiguredFeature<>(ENERGY_PYLON.get(),NoneFeatureConfiguration.INSTANCE);
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","energy_pylon_configured"),
//                    ENERGY_PYLON_CONFIGURED_CONF);
//
//            ENERGY_PYLON_PLACEMENT = registerPlacedFeature("energy_pylon",Holder.direct(ENERGY_PYLON_CONFIGURED_CONF));


//            RUNIC_TREE_FEATURE_CONF = new ConfiguredFeature<>(Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
//                    BlockStateProvider.simple(SolarcraftBlocks.RUNIC_LOG.get().defaultBlockState()),
//                    new StraightTrunkPlacer(4, 2, 0),
//                    BlockStateProvider.simple(SolarcraftBlocks.RUNIC_LEAVES.get().defaultBlockState()),
//                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
//                    new TwoLayersFeatureSize(1, 0, 1))
//                    .ignoreVines().build());
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","runic_tree_configured"),RUNIC_TREE_FEATURE_CONF);

//            RUNIC_TREE_FEATURE = registerPlacedFeature("runic_tree",Holder.direct(RUNIC_TREE_FEATURE_CONF),
//                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
//                    RarityFilter.onAverageOnceEvery(100),
//                    InSquarePlacement.spread(),
//                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));


//            RUINS_CONFIGURED = new ConfiguredFeature<>(FRAGMENT_RUINS.get(),NoneFeatureConfiguration.INSTANCE);

//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","configured_ruins_configured"),
//                    RUINS_CONFIGURED);

//            RUINS_PLACEMENT = registerPlacedFeature("configured_ruins",Holder.direct(RUINS_CONFIGURED),
//                    RarityFilter.onAverageOnceEvery(120));


//            RADIANT_TREE_CONFIGURED_CONF = new ConfiguredFeature<>(Feature.TREE,
//                    new TreeConfiguration.TreeConfigurationBuilder(
//                            BlockStateProvider.simple(SolarcraftBlocks.RADIANT_LOG.get().defaultBlockState()),
//                            new StraightTrunkPlacer(15, 1, 0),
//                            BlockStateProvider.simple(SolarcraftBlocks.RADIANT_LEAVES.get().defaultBlockState()),
//                            new RadiantTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
//                            new TwoLayersFeatureSize(1, 0, 1)
//                    ).build());
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","radiant_tree_configured"), RADIANT_TREE_CONFIGURED_CONF);

//            RADIANT_TREE_PLACEMENT = registerPlacedFeature("radiant_tree",Holder.direct(RADIANT_TREE_CONFIGURED_CONF),
//                    PlacementUtils.countExtra(3,0.1f,2),
//                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
//                    InSquarePlacement.spread(),
//                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));



//            RANDOM_PATCH_RADIANT_GRASS_CONF = Feature.RANDOM_PATCH.configured(
//                    FeatureUtils.simpleRandomPatchConfiguration(1,
//                            Feature.SIMPLE_BLOCK.configured(
//                            new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.RADIANT_GRASS_NOT_BLOCK.get()))).onlyWhenEmpty()));



//            RANDOM_PATCH_RADIANT_GRASS_CONF = new ConfiguredFeature<>(Feature.RANDOM_PATCH,
//                    FeatureUtils.simpleRandomPatchConfiguration(1,PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
//                            new SimpleBlockConfiguration(BlockStateProvider.simple(SolarcraftBlocks.RADIANT_GRASS_NOT_BLOCK.get())))));

//            JsonObject object = new JsonObject();
//            ConfiguredFeature.CODEC.encode(Holder.direct(RANDOM_PATCH_RADIANT_GRASS_CONF),
//                    JsonOps.INSTANCE,
//                    object
//            );


//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","radiant_grass_grass_configured"), RANDOM_PATCH_RADIANT_GRASS_CONF);

//            RANDOM_PATCH_RADIANT_GRASS = registerPlacedFeature("radiant_grass_grass",Holder.direct(RANDOM_PATCH_RADIANT_GRASS_CONF),
//                    CountPlacement.of(60), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);

//
//            FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF = new ConfiguredFeature<>(FLOATING_ISLANDS_RADIANT_LAND.get(),NoneFeatureConfiguration.INSTANCE);
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","floating_islands_configured"), FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF);
//
//            FLOATING_ISLANDS_RADIANT_LAND_PLACEMENT = registerPlacedFeature("floating_islands",Holder.direct(FLOATING_ISLANDS_RADIANT_LAND_CONFIGURED_CONF),
//                    RarityFilter.onAverageOnceEvery(30),
//                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
//                    InSquarePlacement.spread());
//


//            RADIANT_SMALL_TREE_CONFIGURED_CONF = new ConfiguredFeature<>(Feature.TREE,
//                    new TreeConfiguration.TreeConfigurationBuilder(
//                            BlockStateProvider.simple(SolarcraftBlocks.RADIANT_LOG.get().defaultBlockState()),
//                            new StraightTrunkPlacer(9, 1, 0),
//                            BlockStateProvider.simple(SolarcraftBlocks.RADIANT_LEAVES.get().defaultBlockState()),
//                            new RadiantSmallTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
//                            new TwoLayersFeatureSize(1, 0, 1))
//                            .ignoreVines().build());
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","radiant_land_ambient_tree_configured"), RADIANT_SMALL_TREE_CONFIGURED_CONF);

//            RADIANT_SMALL_TREE_PLACEMENT = registerPlacedFeature("radiant_land_ambient_tree",Holder.direct(RADIANT_SMALL_TREE_CONFIGURED_CONF),
//                    RarityFilter.onAverageOnceEvery(3),
//                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
//                    InSquarePlacement.spread(),
//                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));




//            CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF = new ConfiguredFeature<>(CRYSTALLIZED_ORE_VEIN_RADIANT_LAND.get(),NoneFeatureConfiguration.INSTANCE);
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","crystallized_ore_vein_configured"), CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF);

//            CRYSTALLIZED_ORE_VEIN_CONFIGURED = registerPlacedFeature("crystallized_ore_vein",Holder.direct(CRYSTALLIZED_ORE_VEIN_CONFIGURED_CONF),
//                    RarityFilter.onAverageOnceEvery(25),
//                    InSquarePlacement.spread());


//            RADIANT_BERRY_BUSH_CONF = new ConfiguredFeature<>(Feature.RANDOM_PATCH,FeatureUtils.simpleRandomPatchConfiguration(4,
//                    PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
//                            new SimpleBlockConfiguration(BlockStateProvider.simple(SolarcraftBlocks.RADIANT_BERRY_BUSH.get())))));
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","radiant_berry_bush_configured"), RADIANT_BERRY_BUSH_CONF);
//            RADIANT_BERRY_BUSH = registerPlacedFeature("radiant_berry_bush",Holder.direct(RADIANT_BERRY_BUSH_CONF),
//                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);

//
//            ENDER_CRACKS_CONF = new ConfiguredFeature<>(Feature.ORE,
//                    new OreConfiguration(END_STONE, SolarcraftBlocks.ENDER_CRACKS.get().defaultBlockState(),5));
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","ender_cracks_configured"), ENDER_CRACKS_CONF);
//
//            ENDER_CRACKS = registerPlacedFeature("ender_cracks",Holder.direct(ENDER_CRACKS_CONF),
//                    HeightRangePlacement.uniform(VerticalAnchor.absolute(30),VerticalAnchor.absolute(100)));

//            CRYSTAL_CAVE_ORE_CRYSTAL_CONF = new ConfiguredFeature<>(CRYSTAL_CAVE_ORE_CRYSTAL.get(),NoneFeatureConfiguration.INSTANCE);
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarcraft","crystal_cave_ore_crystal"), CRYSTAL_CAVE_ORE_CRYSTAL_CONF);
//
//            CRYSTAL_CAVE_ORE_CRYSTAL_PLACEMENT = registerPlacedFeature("crystal_cave_ore_crystal",Holder.direct(CRYSTAL_CAVE_ORE_CRYSTAL_CONF),
//                    RarityFilter.onAverageOnceEvery(3),
//                    CountPlacement.of(UniformInt.of(10,15)),InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());

//            CEILING_FLOOR_CRYSTALS_CONF = new ConfiguredFeature<>(CEILING_FLOOR_CRYSTALS.get(),NoneFeatureConfiguration.INSTANCE);
//            registerConfiguredFeature(CEILING_FLOOR_CRYSTALS_CONF,"ceiling_floor_crystals");

//            CEILING_FLOOR_CRYSTALS_PLACEMENT = registerPlacedFeature("ceiling_floor_crystals",Holder.direct(CEILING_FLOOR_CRYSTALS_CONF),
//                    CountPlacement.of(UniformInt.of(13,17)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
//                    BiomeFilter.biome());

//            CRYSTAL_FLOWER_CONF = new ConfiguredFeature<>(Feature.FLOWER,
//                    FeatureUtils.simpleRandomPatchConfiguration(7,
//                            PlacementUtils.onlyWhenEmpty(STONE_FLOWERS.get(),
//                                    new SimpleBlockConfiguration(BlockStateProvider.simple(SolarcraftBlocks.CRYSTAL_FLOWER.get())))));
//            registerConfiguredFeature(CRYSTAL_FLOWER_CONF,"crystal_flower");
//
//            CRYSTAL_FLOWER_PLACEMENT = registerPlacedFeature("crystal_flower",Holder.direct(CRYSTAL_FLOWER_CONF),
//                    CountPlacement.of(UniformInt.of(50,60)),PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,BiomeFilter.biome() );

//            CEILING_DRIPSTONE_LIKE_CRYSTALS_CONF = new ConfiguredFeature<>(CEILING_DRIPSTONE_LIKE_CRYSTALS.get(),NoneFeatureConfiguration.INSTANCE);
//            registerConfiguredFeature(CEILING_DRIPSTONE_LIKE_CRYSTALS_CONF,"ceiling_dripstonelike_crystals");
//
//            CEILING_DRIPSTONE_LIKE_CRYSTALS_PLACEMENT = registerPlacedFeature("ceiling_dripstonelike_crystals",
//                    Holder.direct(CEILING_DRIPSTONE_LIKE_CRYSTALS_CONF),
//                    CountPlacement.of(UniformInt.of(20,30)),InSquarePlacement.spread(),
//                    PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,BiomeFilter.biome());

//            CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_CONF = new ConfiguredFeature<>(CRYSTALS_ORE.get(),
//                    new SimpleBlockConfiguration(BlockStateProvider.simple(SolarcraftBlocks.CRYSTALLIZED_RUNIC_ENERGY.get())));
//            registerConfiguredFeature(CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_CONF,"crystallized_runic_energy");
//
//            CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_PLACEMENT = registerPlacedFeature("crystallized_runic_energy",
//                    Holder.direct(CRYSTALLIZED_RUNIC_ENERGY_CRYSTALS_CONF),
//                    CountPlacement.of(UniformInt.of(60,100)),InSquarePlacement.spread(),
//                    HeightRangePlacement.uniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(100)),BiomeFilter.biome());

//            LENSING_CRYSTAL_ORE_CONF = new ConfiguredFeature<>(Feature.ORE,
//                    new OreConfiguration(OreFeatures.NATURAL_STONE, SolarcraftBlocks.LENSING_CRYSTAL_ORE.get().defaultBlockState(),4));
//            registerConfiguredFeature(LENSING_CRYSTAL_ORE_CONF,"lensing_crystal_ore");
//
//            LENSING_CRYSTAL_ORE_PLACEMENT = registerPlacedFeature("lensing_crystal_ore",Holder.direct(LENSING_CRYSTAL_ORE_CONF),
//                    CountPlacement.of(UniformInt.of(4,6)),InSquarePlacement.spread(),
//                    HeightRangePlacement.uniform(VerticalAnchor.absolute(10),VerticalAnchor.absolute(50)));

//            LUNAR_LILY_FEATURE_CONF = new ConfiguredFeature<>(Feature.FLOWER,
//                    FeatureUtils.simpleRandomPatchConfiguration(3,PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
//                            new SimpleBlockConfiguration(BlockStateProvider.simple(SolarcraftBlocks.VOID_LILY.get())))));
//            registerConfiguredFeature(LUNAR_LILY_FEATURE_CONF,"lunar_lily");
//
//            LUNAR_LILY_FEATURE_PLACEMENT = registerPlacedFeature("lunar_lily",Holder.direct(LUNAR_LILY_FEATURE_CONF),
//                    NoiseThresholdCountPlacement.of(-0.8D, 15, 4),
//                    RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP );
//
//            EMPTY_CRYSTALS_CONF = new ConfiguredFeature<>(CRYSTALS_ORE.get(),
//                    new SimpleBlockConfiguration(BlockStateProvider.simple(SolarcraftBlocks.CRYSTAL.get())));
//            registerConfiguredFeature(EMPTY_CRYSTALS_CONF,"empty_crystals");
//
//            EMPTY_CRYSTALS_PLACEMENT = registerPlacedFeature("empty_crystals",Holder.direct(EMPTY_CRYSTALS_CONF),
//                    CountPlacement.of(UniformInt.of(15,30)),
//                    InSquarePlacement.spread(),HeightRangePlacement.uniform(VerticalAnchor.bottom(),
//                            VerticalAnchor.absolute(100)),BiomeFilter.biome() );
//            SOLAR_ORE_CONF = new ConfiguredFeature<>(Feature.ORE,new OreConfiguration(OreFeatures.NATURAL_STONE, SolarCraft.SOLAR_ORE.get().defaultBlockState(),4));
//            registerConfiguredFeature(SOLAR_ORE_CONF,"solar_ore");
//            SOLAR_ORE = registerPlacedFeature("solar_ore",Holder.direct(SOLAR_ORE_CONF),
//                    HeightRangePlacement.uniform(VerticalAnchor.absolute(5),VerticalAnchor.absolute(30)));



//            SOLAR_STONE_CONF = new ConfiguredFeature<>(Feature.ORE,new OreConfiguration(OreFeatures.NATURAL_STONE, SolarcraftBlocks.SOLAR_STONE.get().defaultBlockState(),13));
//            registerConfiguredFeature(SOLAR_STONE_CONF,"solar_stone");
//            SOLAR_STONE = registerPlacedFeature("solar_stone",Holder.direct(SOLAR_STONE_CONF),
//                    CountPlacement.of(UniformInt.of(3,4)), InSquarePlacement.spread(),
//                    HeightRangePlacement.uniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(80)));


//            MAGISTONE_ORE = new ConfiguredFeature<>(Feature.ORE,
//                    new OreConfiguration(OreFeatures.NATURAL_STONE,
//                            SolarcraftBlocks.MAGISTONE.get().defaultBlockState(),15));
//            registerConfiguredFeature(MAGISTONE_ORE,"magistone");
//            MAGISTONE_ORE_PLACEMENT = registerPlacedFeature("magistone",Holder.direct(MAGISTONE_ORE),
//                    CountPlacement.of(UniformInt.of(3,4)), InSquarePlacement.spread(),
//                    HeightRangePlacement.uniform(VerticalAnchor.bottom(),VerticalAnchor.aboveBottom(64)));



//            LazyConfiguredFeatures.SOLAR_FLOWER_FEATURE_CONF = new ConfiguredFeature<>(Feature.RANDOM_PATCH,FeatureUtils.simpleRandomPatchConfiguration(2,
//                    PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,new SimpleBlockConfiguration(BlockStateProvider.simple(SolarcraftBlocks.SOLAR_FLOWER.get())))));
//            registerConfiguredFeature(LazyConfiguredFeatures.SOLAR_FLOWER_FEATURE_CONF,"solar_flower_feature");
//            LazyConfiguredFeatures.SOLAR_FLOWER_FEATURE = registerPlacedFeature("solar_flower_feature",Holder.direct(LazyConfiguredFeatures.SOLAR_FLOWER_FEATURE_CONF),
//                    HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),InSquarePlacement.spread());


//            LazyConfiguredFeatures.DEAD_SPROUT_FEATURE_CONF = new ConfiguredFeature<>(Feature.RANDOM_PATCH,
//                    FeatureUtils.simpleRandomPatchConfiguration(7,PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
//                            new SimpleBlockConfiguration(BlockStateProvider.simple(SolarcraftBlocks.DEAD_SPROUT.get())))));
//            registerConfiguredFeature(LazyConfiguredFeatures.DEAD_SPROUT_FEATURE_CONF,"dead_sprout_feature");
//            LazyConfiguredFeatures.DEAD_SPROUT_FEATURE = registerPlacedFeature("dead_sprout_feature",Holder.direct(LazyConfiguredFeatures.DEAD_SPROUT_FEATURE_CONF),
//                    HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),InSquarePlacement.spread());

//            ULDERA_OBELISK_CONFIGURED = new ConfiguredFeature<>(ULDERA_OBELISK.get(),NoneFeatureConfiguration.INSTANCE);
//            registerConfiguredFeature(ULDERA_OBELISK_CONFIGURED,"uldera_obelisk");

//            ULDERA_OBELISK_PLACEMENT = registerPlacedFeature("uldera_obelisk",Holder.direct(ULDERA_OBELISK_CONFIGURED),
//                    RarityFilter.onAverageOnceEvery(100),
//                    InSquarePlacement.spread());
//

//            ULDERA_PYLON_CONFIGURED = new ConfiguredFeature<>(ULDERA_PYLON.get(),NoneFeatureConfiguration.INSTANCE);
//            registerConfiguredFeature(ULDERA_PYLON_CONFIGURED,"uldera_pylon");

//            ULDERA_PYLON_PLACEMENT = registerPlacedFeature("uldera_pylon",Holder.direct(ULDERA_PYLON_CONFIGURED),
//                    RarityFilter.onAverageOnceEvery(300));

//            CLEARING_CRYSTAL_CONFIGURED = new ConfiguredFeature<>(CLEARING_CRYSTAL.get(),NoneFeatureConfiguration.INSTANCE);
//            registerConfiguredFeature(CLEARING_CRYSTAL_CONFIGURED,"clearing_crystal");
//            CLEARING_CRYSTAL_PLACEMENT = registerPlacedFeature("clearing_crystal",Holder.direct(CLEARING_CRYSTAL_CONFIGURED),
//                    RarityFilter.onAverageOnceEvery(250),InSquarePlacement.spread(),HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING));
        });
    }



//    private static void registerConfiguredFeature(ConfiguredFeature<?,?> feature,String registryid){
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation(SolarCraft.MOD_ID,registryid),feature);
//    }
//
//    private static void registerPlacedFeature(PlacedFeature feature,String registryid){
//        Registry.register(BuiltinRegistries.PLACED_FEATURE,new ResourceLocation(SolarCraft.MOD_ID,registryid),feature);
//    }
//    public static Holder<PlacedFeature> registerPlacedFeature(String name, Holder<? extends ConfiguredFeature<?, ?>> cFeature, PlacementModifier... mod) {
//        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(SolarCraft.MOD_ID,name), new PlacedFeature(Holder.hackyErase(cFeature), List.of(mod)));
//    }



}
