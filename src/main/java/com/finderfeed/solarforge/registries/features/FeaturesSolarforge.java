package com.finderfeed.solarforge.registries.features;

import com.finderfeed.solarforge.features.solar_flowers_feature.SolarFlowerFeature;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeaturesSolarforge {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES,"solarforge");
    public static final RegistryObject<SolarFlowerFeature> SOLAR_FLOWER_FEATURE = FEATURES.register("solar_flower",()->new SolarFlowerFeature(BlockClusterFeatureConfig.CODEC));


}
