package com.finderfeed.solarforge.compat.crafttweaker.handler;

import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;

import java.util.stream.Collectors;

@IRecipeHandler.For(InfusingCraftingRecipe.class)
public class InfusingCraftingRecipeHandler implements IRecipeHandler<InfusingCraftingRecipe> {
    /**
     * Creates a String representation of a valid {@code addRecipe} (or alternative) call for the given subclass of
     * {@link Recipe}.
     *
     * <p>Recipe dumps are triggered by the {@code /ct recipes} or {@code /ct recipes hand} commands.</p>
     *
     * <p>All newlines added to either the start or the end of the string will be automatically trimmed.</p>
     *
     * @param manager The recipe manager responsible for this kind of recipes.
     * @param recipe  The recipe that is currently being dumped.
     * @return A String representing a {@code addRecipe} (or similar) call.
     */
    @Override
    public String dumpToCommandString(IRecipeManager manager, InfusingCraftingRecipe recipe) {
        String[] inputsString = recipe.getIngredients().stream().map(ingredient -> ingredient.getItems()[0]).map(itemStack -> new MCItemStack(itemStack).getCommandString()).collect(Collectors.joining(",")).split(",");
        String firstPart = String.format("<recipetype:solarforge:infusing_crafting>.addRecipe(\"%s\", %s, [[%s], [%s], [%s]], %s, \"%s\"",
                recipe.getId(),
                new MCItemStack(recipe.getResultItem()).getCommandString(),
                inputsString[0] + ", " + inputsString[1] + ", " + inputsString[2],
                inputsString[3] + ", " + inputsString[4] + ", " + inputsString[5],
                inputsString[6] + ", " + inputsString[7] + ", " + inputsString[8],
                recipe.getTime(),
                recipe.getFragmentID()
                );
        return firstPart + ");";
    }
}
