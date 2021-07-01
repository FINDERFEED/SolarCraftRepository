package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.world_generation.biomes.molten_forest.BurntBiomeAmbienceSecond;
import com.finderfeed.solarforge.world_generation.biomes.molten_forest.BurntBiomeBurntTree;
import com.finderfeed.solarforge.world_generation.biomes.molten_forest.BurntTreeFeature;
import com.finderfeed.solarforge.world_generation.biomes.molten_forest.MoltenForestBiome;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;


public class FeaturesRegistry {
    public static final ResourceLocation BURNT_BIOME_BURNT_TREE = new ResourceLocation("solarforge","burnt_biome_tree");
    public static final ResourceLocation BURNT_BIOME_BURNT_TREE2 = new ResourceLocation("solarforge","burnt_biome_tree2");
    public static final ResourceLocation MOLTEN_FOREST_BIOME = new ResourceLocation("solarforge","incinerated_forest");

    public static final RegistryKey<Biome> MOLTEN_BIOME_KEY = RegistryKey.create(Registry.BIOME_REGISTRY,MOLTEN_FOREST_BIOME);

    public static final Feature<NoFeatureConfig> BURNT_BIOME_AMBIENCE_1 = new BurntBiomeBurntTree(NoFeatureConfig.CODEC);


    public static final ConfiguredFeature<?,?> BURNT_BIOME_AMBIENCE_1_CONFIGURED = BURNT_BIOME_AMBIENCE_1
            .configured(NoFeatureConfig.INSTANCE)
            .decorated(Placement.CHANCE.configured(new ChanceConfig(4)));

    public static final ConfiguredFeature<?,?> ULDORADIUM_ORE =
            Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlocksRegistry.ULDORADIUM_ORE.get().defaultBlockState(),7))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(0,0,50)).squared().count(6));

    public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event){
            event.getRegistry().register(BURNT_BIOME_AMBIENCE_1.setRegistryName(BURNT_BIOME_BURNT_TREE));

    }


    public static void registerConfiguredFeatures(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_tree_feature2"),BurntTreeFeature.BURNT_TREE_FEATURE_2);
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_tree_feature"),BurntTreeFeature.BURNT_TREE_FEATURE);
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","burnt_biome_ambience_1"),BURNT_BIOME_AMBIENCE_1_CONFIGURED);
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","uldoradium_ore"),ULDORADIUM_ORE);

        });
    }



}
