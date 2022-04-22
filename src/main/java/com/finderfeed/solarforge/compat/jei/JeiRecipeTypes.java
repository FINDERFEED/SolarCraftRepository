package com.finderfeed.solarforge.compat.jei;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import mezz.jei.api.recipe.RecipeType;


public class JeiRecipeTypes {

    public static final RecipeType<InfusingRecipe> INFUSING_RECIPE = RecipeType.create(SolarForge.MOD_ID,"infusing",InfusingRecipe.class);
    public static final RecipeType<InfusingCraftingRecipe> INFUSING_CRAFTING_RECIPE = RecipeType.create(SolarForge.MOD_ID,"infusing_crafting",InfusingCraftingRecipe.class);
    public static final RecipeType<SolarSmeltingRecipe> SMELTING = RecipeType.create(SolarForge.MOD_ID,"smelting", SolarSmeltingRecipe.class);

}
