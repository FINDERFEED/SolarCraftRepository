package com.finderfeed.solarforge.registries.features.configured;

import com.finderfeed.solarforge.loot_modifiers.custom_loot_conditions.SolarcraftModulePredicate;
import com.finderfeed.solarforge.loot_modifiers.custom_loot_conditions.SolarcraftNBTPredicate;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.advancements.critereon.ItemPredicate;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.Heightmap;

import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;




public class ConfiguredFeatures {
//(new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlocksRegistry.SOLAR_FLOWER.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(2).build();
//    public static final RandomPatchConfiguration DEFAULT_FLOWER_CONFIG =
//        FeatureUtils.simpleRandomPatchConfiguration(2,
//                Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.SOLAR_FLOWER.get()))).onlyWhenEmpty());

    public static ConfiguredFeature<?,?> SOLAR_FLOWER_FEATURE_CONF;
//    Feature.RANDOM_PATCH.configured(DEFAULT_FLOWER_CONFIG);

    public static Holder<PlacedFeature> SOLAR_FLOWER_FEATURE;

//     = SOLAR_FLOWER_FEATURE_CONF.placed(HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
//            InSquarePlacement.spread());


    public static ConfiguredFeature<?,?> DEAD_SPROUT_FEATURE_CONF;

//
//     Feature.RANDOM_PATCH.configured(FeatureUtils.simpleRandomPatchConfiguration(7,Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.DEAD_SPROUT.get()))).onlyWhenEmpty()));

   public static Holder<PlacedFeature> DEAD_SPROUT_FEATURE;
//   DEAD_SPROUT_FEATURE_CONF.placed(HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),InSquarePlacement.spread());


    public static void registerConfiguredFeatures(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","solar_flower_feature"),SOLAR_FLOWER_FEATURE_CONF);
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","dead_sprout_feature"),DEAD_SPROUT_FEATURE_CONF);
//
//            Registry.register(BuiltinRegistries.PLACED_FEATURE,new ResourceLocation("solarforge","dead_sprout_feature"),DEAD_SPROUT_FEATURE);
//            Registry.register(BuiltinRegistries.PLACED_FEATURE,new ResourceLocation("solarforge","solar_flower_feature"),SOLAR_FLOWER_FEATURE);
            ItemPredicate.register(new ResourceLocation("solarcraft_predicate"), SolarcraftNBTPredicate::fromJson);
            ItemPredicate.register(new ResourceLocation("solarcraft_module_predicate"), SolarcraftModulePredicate::fromJson);
        });
    }
}
