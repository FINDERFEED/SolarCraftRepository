package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.biomes.molten_forest.MoltenForestAmbience;
import com.finderfeed.solarforge.world_generation.biomes.molten_forest.BurntTreeFeature;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


public class FeaturesRegistry {
    public static final ResourceLocation BURNT_BIOME_BURNT_TREE = new ResourceLocation("solarforge","burnt_biome_tree");
    public static final ResourceLocation MOLTEN_FOREST_BIOME = new ResourceLocation("solarforge","incinerated_forest");
    public static final RegistryKey<Biome> MOLTEN_BIOME_KEY = RegistryKey.create(Registry.BIOME_REGISTRY,MOLTEN_FOREST_BIOME);


    public static final Feature<NoFeatureConfig> BURNT_BIOME_AMBIENCE_1 = new MoltenForestAmbience(NoFeatureConfig.CODEC);
    public static final Feature<NoFeatureConfig> ENERGY_PYLON = new EnergyPylonFeature(NoFeatureConfig.CODEC);



    public static ConfiguredFeature<?,?> ENERGY_PYLON_CONFIGURED;

    public static final ConfiguredFeature<?,?> BURNT_BIOME_AMBIENCE_1_CONFIGURED = BURNT_BIOME_AMBIENCE_1
            .configured(NoFeatureConfig.INSTANCE)
            .decorated(Placement.CHANCE.configured(new ChanceConfig(4)));

    public static final ConfiguredFeature<?,?> ULDORADIUM_ORE =
            Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlocksRegistry.ULDORADIUM_ORE.get().defaultBlockState(),7))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(0,0,50)).squared().count(6));

    public static ConfiguredFeature<?,?> RUNIC_TREE_FEATURE;


    public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event){
            event.getRegistry().register(BURNT_BIOME_AMBIENCE_1.setRegistryName(BURNT_BIOME_BURNT_TREE));
            event.getRegistry().register(ENERGY_PYLON.setRegistryName(new ResourceLocation("solarforge","energy_pylon_feature")));
    }


    public static void registerConfiguredFeatures(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_tree_feature2"),BurntTreeFeature.BURNT_TREE_FEATURE_2);
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_tree_feature"),BurntTreeFeature.BURNT_TREE_FEATURE);
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_biome_ambience_1"),BURNT_BIOME_AMBIENCE_1_CONFIGURED);
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","uldoradium_ore"),ULDORADIUM_ORE);
            ENERGY_PYLON_CONFIGURED = ENERGY_PYLON.configured(NoFeatureConfig.INSTANCE).decorated(Placement.CHANCE.configured(new ChanceConfig(200)));
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","energy_pylon"),ENERGY_PYLON_CONFIGURED);
            RUNIC_TREE_FEATURE = Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlocksRegistry.RUNIC_LOG.get().defaultBlockState()),
                    new SimpleBlockStateProvider(BlocksRegistry.RUNIC_LEAVES.get().defaultBlockState()),
                    new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3),
                    new StraightTrunkPlacer(4, 2, 0),
                    new TwoLayerFeature(1, 0, 1))
                    .ignoreVines().build())
                    .decorated(Placement.CHANCE.configured(new ChanceConfig(100)));
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","runic_tree"),RUNIC_TREE_FEATURE);

        });
    }



}
