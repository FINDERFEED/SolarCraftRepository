package com.finderfeed.solarforge.compat.crafttweaker.type;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.finderfeed.solarforge.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;

@ZenRegister
@Document("mods/SolarForge/Recipe/InfusingCraftingRecipe")
@NativeTypeRegistration(value = InfusingCraftingRecipe.class, zenCodeName = "mods.solarforge.recipe.InfusingCraftingRecipe")
public class ExpandInfusingCraftingRecipe {
}
