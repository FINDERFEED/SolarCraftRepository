package com.finderfeed.solarforge.registries.features.configured;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.custom_loot_conditions.SolarcraftNBTPredicate;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.advancements.critereon.ItemPredicate;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.HeightmapConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;




public class ConfiguredFeatures {

    public static final RandomPatchConfiguration DEFAULT_FLOWER_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlocksRegistry.SOLAR_FLOWER.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(2).build();

    public static final ConfiguredFeature<?,?> SOLAR_FLOWER_FEATURE = Feature.RANDOM_PATCH.configured(DEFAULT_FLOWER_CONFIG)
            .decorated(FeatureDecorator.HEIGHTMAP_SPREAD_DOUBLE.configured(new HeightmapConfiguration(Heightmap.Types.MOTION_BLOCKING)).squared());

    public static final ConfiguredFeature<?,?> DEAD_SPROUT_FEATURE = Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlocksRegistry.DEAD_SPROUT.get().defaultBlockState()),
            SimpleBlockPlacer.INSTANCE)).tries(7).build())
            .decorated(FeatureDecorator.HEIGHTMAP_SPREAD_DOUBLE.configured(new HeightmapConfiguration(Heightmap.Types.MOTION_BLOCKING)).squared());


    public static void registerConfiguredFeatures(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","solar_flower_feature"),SOLAR_FLOWER_FEATURE);
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","dead_sprout_feature"),DEAD_SPROUT_FEATURE);

            ItemPredicate.register(new ResourceLocation("solarcraft_predicate"), SolarcraftNBTPredicate::fromJson);
        });
    }
}
