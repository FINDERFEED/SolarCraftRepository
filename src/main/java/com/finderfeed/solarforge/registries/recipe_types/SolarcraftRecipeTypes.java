package com.finderfeed.solarforge.registries.recipe_types;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarforge.content.recipe_types.infusing_crafting.InfusingCraftingRecipeSerializer;
import com.finderfeed.solarforge.content.recipe_types.infusing_crafting.InfusingCraftingRecipeType;
import com.finderfeed.solarforge.content.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarforge.content.recipe_types.infusing_new.InfusingRecipeSerializer;
import com.finderfeed.solarforge.content.recipe_types.infusing_new.InfusingRecipeType;
import com.finderfeed.solarforge.content.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarforge.content.recipe_types.solar_smelting.SolarSmeltingRecipeSerializer;
import com.finderfeed.solarforge.content.recipe_types.solar_smelting.SolarSmeltingRecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SolarcraftRecipeTypes {



    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SolarForge.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES,SolarForge.MOD_ID);


    public static final RegistryObject<RecipeSerializer<?>> INFUSING_CRAFTING_SERIALIZER = RECIPE_SERIALIZERS.register("infusing_crafting", InfusingCraftingRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SMELTING_SERIALIZER = RECIPE_SERIALIZERS.register("solar_smelting", SolarSmeltingRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>> INFUSING_SERIALIZER = RECIPE_SERIALIZERS.register("infusing_new", InfusingRecipeSerializer::new);


    public static final RegistryObject<RecipeType<SolarSmeltingRecipe>> SMELTING = RECIPE_TYPES.register("solar_smelting",SolarSmeltingRecipeType::new);
    public static final RegistryObject<RecipeType<InfusingRecipe>> INFUSING = RECIPE_TYPES.register("infusing_new",InfusingRecipeType::new);
    public static final RegistryObject<RecipeType<InfusingCraftingRecipe>> INFUSING_CRAFTING = RECIPE_TYPES.register("infusing_crafting",InfusingCraftingRecipeType::new);
}
