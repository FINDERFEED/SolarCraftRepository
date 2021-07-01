package com.finderfeed.solarforge.registries.features.configured;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.custom_loot_conditions.SolarcraftNBTPredicate;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


public class ConfiguredFeatures {

    public static final BlockClusterFeatureConfig DEFAULT_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
            .add(BlocksRegistry.SOLAR_FLOWER.get().defaultBlockState(), 1), SimpleBlockPlacer.INSTANCE)).tries(2).build();

    public static final ConfiguredFeature<?,?> SOLAR_FLOWER_FEATURE = Feature.RANDOM_PATCH.configured(DEFAULT_FLOWER_CONFIG)
            .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);

    public static final ConfiguredFeature<?,?> DEAD_SPROUT_FEATURE = Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
            .add(BlocksRegistry.DEAD_SPROUT.get().defaultBlockState(), 1), SimpleBlockPlacer.INSTANCE)).tries(7).build())
            .decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE);


    public static void registerConfiguredFeatures(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","solar_flower_feature"),SOLAR_FLOWER_FEATURE);
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,new ResourceLocation("solarforge","dead_sprout_feature"),DEAD_SPROUT_FEATURE);

            ItemPredicate.register(new ResourceLocation("solarcraft_predicate"), SolarcraftNBTPredicate::fromJson);
        });
    }
}
