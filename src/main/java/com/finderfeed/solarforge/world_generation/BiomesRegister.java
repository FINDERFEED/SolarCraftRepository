package com.finderfeed.solarforge.world_generation;


import net.minecraft.world.level.biome.Biome;
import net.minecraft.data.worldgen.biome.VanillaBiomes;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomesRegister {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES,"solarforge");

    public static final RegistryObject<Biome> MOLTEN_FOREST_BIOME = BIOMES.register("incinerated_forest",()-> VanillaBiomes.theVoidBiome());




}
