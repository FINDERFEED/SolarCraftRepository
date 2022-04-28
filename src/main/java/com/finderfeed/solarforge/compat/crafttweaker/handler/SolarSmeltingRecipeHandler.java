package com.finderfeed.solarforge.compat.crafttweaker.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;

import java.util.stream.Collectors;

@IRecipeHandler.For(SolarSmeltingRecipe.class)
public class SolarSmeltingRecipeHandler implements IRecipeHandler<SolarSmeltingRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager manager, SolarSmeltingRecipe recipe) {
        String script = String.format("<recipetype:solarforge:solar_smelting>.addRecipe(\"%s\", %s, [%s], %s",
                recipe.getId().toString(),
                new MCItemStack(recipe.getResultItem()).getCommandString(),
                recipe.stacks.stream().map(MCItemStack::new).map(MCItemStack::getCommandString).collect(Collectors.joining(", ")),
                recipe.getInfusingTime()
                );
        script = script.concat(");");
        return script;
    }
}
