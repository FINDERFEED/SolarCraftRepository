package com.finderfeed.solarcraft.content.world_generation;


import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BiomesRegister {


//    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES,"solarcraft");
//    public static final RegistryObject<Biome> MOLTEN_FOREST_BIOME = BIOMES.register("incinerated_forest",OverworldBiomes::theVoid);
//    public static final RegistryObject<Biome> RADIANT_LAND_BIOME = BIOMES.register("radiant_land",OverworldBiomes::theVoid);
//    public static final RegistryObject<Biome> CRYSTAL_CAVES = BIOMES.register("crystal_caves",OverworldBiomes::theVoid);

    public static final ResourceKey<Biome> RADIANT_LAND_KEY = ResourceKey.create(Registries.BIOME,new ResourceLocation("solarcraft","radiant_land"));
    public static final ResourceKey<Biome> CRYSTAL_CAVES_KEY = ResourceKey.create(Registries.BIOME,new ResourceLocation("solarcraft","crystal_caves"));

}
