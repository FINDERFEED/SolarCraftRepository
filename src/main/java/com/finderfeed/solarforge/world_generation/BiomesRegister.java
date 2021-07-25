package com.finderfeed.solarforge.world_generation;

import com.finderfeed.solarforge.world_generation.biomes.molten_forest.MoltenForestBiome;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.data.worldgen.biome.VanillaBiomes;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomesRegister {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES,"solarforge");

    public static final RegistryObject<Biome> MOLTEN_FOREST_BIOME = BIOMES.register("incinerated_forest",()-> VanillaBiomes.theVoidBiome());




}
