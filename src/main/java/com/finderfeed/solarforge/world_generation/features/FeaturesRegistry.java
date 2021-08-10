package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.biomes.molten_forest.MoltenForestAmbience;
import com.finderfeed.solarforge.world_generation.biomes.molten_forest.BurntTreeFeature;
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
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;

import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;



import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;


public class FeaturesRegistry {
    public static final ResourceLocation BURNT_BIOME_BURNT_TREE = new ResourceLocation("solarforge","burnt_biome_tree");
    public static final ResourceLocation MOLTEN_FOREST_BIOME = new ResourceLocation("solarforge","incinerated_forest");
    public static final ResourceKey<Biome> MOLTEN_BIOME_KEY = ResourceKey.create(Registry.BIOME_REGISTRY,MOLTEN_FOREST_BIOME);


    public static final Feature<NoneFeatureConfiguration> BURNT_BIOME_AMBIENCE_1 = new MoltenForestAmbience(NoneFeatureConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> BURNT_BIOME_AMBIENCE_2 = new MoltenForestRuins(NoneFeatureConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> ENERGY_PYLON = new EnergyPylonFeature(NoneFeatureConfiguration.CODEC);



    public static ConfiguredFeature<?,?> ENERGY_PYLON_CONFIGURED;
    public static ConfiguredFeature<?,?> MOLTEN_FOREST_RUINS_CONFIGURED;


    public static final ConfiguredFeature<?,?> BURNT_BIOME_AMBIENCE_1_CONFIGURED = BURNT_BIOME_AMBIENCE_1
            .configured(NoneFeatureConfiguration.INSTANCE)
            .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(4)));

    public static final ConfiguredFeature<?,?> ULDORADIUM_ORE =
            Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, BlocksRegistry.ULDORADIUM_ORE.get().defaultBlockState(),7))
            .rangeUniform(VerticalAnchor.bottom(),VerticalAnchor.absolute(60)).squared().count(6);

    public static ConfiguredFeature<?,?> RUNIC_TREE_FEATURE;


    public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event){
        event.getRegistry().register(BURNT_BIOME_AMBIENCE_1.setRegistryName(BURNT_BIOME_BURNT_TREE));
        event.getRegistry().register(BURNT_BIOME_AMBIENCE_2.setRegistryName(new ResourceLocation("solarforge","ruins_feature")));
        event.getRegistry().register(ENERGY_PYLON.setRegistryName(new ResourceLocation("solarforge","energy_pylon_feature")));
    }


    public static void registerConfiguredFeatures(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_tree_feature2"),BurntTreeFeature.BURNT_TREE_FEATURE_2);
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_tree_feature"),BurntTreeFeature.BURNT_TREE_FEATURE);
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_biome_ambience_1"),BURNT_BIOME_AMBIENCE_1_CONFIGURED);
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","uldoradium_ore"),ULDORADIUM_ORE);
            ENERGY_PYLON_CONFIGURED = ENERGY_PYLON.configured(NoneFeatureConfiguration.INSTANCE).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(200)));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","energy_pylon"),ENERGY_PYLON_CONFIGURED);
            RUNIC_TREE_FEATURE = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                    new SimpleStateProvider(BlocksRegistry.RUNIC_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    new SimpleStateProvider(BlocksRegistry.RUNIC_LEAVES.get().defaultBlockState()),
                    new SimpleStateProvider(Blocks.OAK_SAPLING.defaultBlockState()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines().build())
                    .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.WORLD_SURFACE_WG)))
                    .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(100)));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","runic_tree"),RUNIC_TREE_FEATURE);

            MOLTEN_FOREST_RUINS_CONFIGURED = BURNT_BIOME_AMBIENCE_2.configured(NoneFeatureConfiguration.INSTANCE).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(60)));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","configured_ruins"),MOLTEN_FOREST_RUINS_CONFIGURED);
        });
    }



}
