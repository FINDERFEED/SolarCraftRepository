package com.finderfeed.solarcraft.registries.recipe_types;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipeSerializer;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipeType;
import com.finderfeed.solarcraft.content.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarcraft.content.recipe_types.infusing_new.InfusingRecipeSerializer;
import com.finderfeed.solarcraft.content.recipe_types.infusing_new.InfusingRecipeType;
import com.finderfeed.solarcraft.content.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarcraft.content.recipe_types.solar_smelting.SolarSmeltingRecipeSerializer;
import com.finderfeed.solarcraft.content.recipe_types.solar_smelting.SolarSmeltingRecipeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SolarcraftRecipeTypes {



    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, SolarCraft.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, SolarCraft.MOD_ID);


    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<?>> INFUSING_CRAFTING_SERIALIZER = RECIPE_SERIALIZERS.register("infusing_crafting", InfusingCraftingRecipeSerializer::new);
    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<?>> SMELTING_SERIALIZER = RECIPE_SERIALIZERS.register("solar_smelting", SolarSmeltingRecipeSerializer::new);
    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<?>> INFUSING_SERIALIZER = RECIPE_SERIALIZERS.register("infusing_new", InfusingRecipeSerializer::new);


    public static final DeferredHolder<RecipeType<?>,RecipeType<SolarSmeltingRecipe>> SMELTING = RECIPE_TYPES.register("solar_smelting",SolarSmeltingRecipeType::new);
    public static final DeferredHolder<RecipeType<?>,RecipeType<InfusingRecipe>> INFUSING = RECIPE_TYPES.register("infusing_new",InfusingRecipeType::new);
    public static final DeferredHolder<RecipeType<?>,RecipeType<InfusingCraftingRecipe>> INFUSING_CRAFTING = RECIPE_TYPES.register("infusing_crafting",InfusingCraftingRecipeType::new);
}
