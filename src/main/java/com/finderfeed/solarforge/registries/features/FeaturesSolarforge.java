package com.finderfeed.solarforge.registries.features;

import com.finderfeed.solarforge.world_generation.features.flowers.solar_flowers_feature.SolarFlowerFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeaturesSolarforge {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES,"solarforge");
    public static final RegistryObject<SolarFlowerFeature> SOLAR_FLOWER_FEATURE = FEATURES.register("solar_flower",()->new SolarFlowerFeature(RandomPatchConfiguration.CODEC));


}
