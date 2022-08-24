package com.finderfeed.solarforge.registries.worldgen.configured;

import com.finderfeed.solarforge.content.loot_modifiers.custom_loot_conditions.SolarcraftModulePredicate;
import com.finderfeed.solarforge.content.loot_modifiers.custom_loot_conditions.SolarcraftNBTPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;




public class LazyConfiguredFeatures {

    public static ConfiguredFeature<?,?> SOLAR_FLOWER_FEATURE_CONF;

    public static Holder<PlacedFeature> SOLAR_FLOWER_FEATURE;


    public static ConfiguredFeature<?,?> DEAD_SPROUT_FEATURE_CONF;

   public static Holder<PlacedFeature> DEAD_SPROUT_FEATURE;



    public static void registerConfiguredFeatures(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            ItemPredicate.register(new ResourceLocation("solarcraft_predicate"), SolarcraftNBTPredicate::fromJson);
            ItemPredicate.register(new ResourceLocation("solarcraft_module_predicate"), SolarcraftModulePredicate::fromJson);
        });
    }
}
