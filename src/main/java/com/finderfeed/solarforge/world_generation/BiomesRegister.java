package com.finderfeed.solarforge.world_generation;


import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.data.worldgen.biome.VanillaBiomes;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomesRegister {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES,"solarforge");

    public static final RegistryObject<Biome> MOLTEN_FOREST_BIOME = BIOMES.register("incinerated_forest",()-> VanillaBiomes.theVoidBiome());

    public static final ResourceKey<Biome> RADIANT_LAND_KEY = ResourceKey.create(Registry.BIOME_REGISTRY,new ResourceLocation("solarforge","radiant_land"));
    public static final RegistryObject<Biome> RADIANT_LAND_BIOME = BIOMES.register("radiant_land",()-> VanillaBiomes.theVoidBiome());


}
