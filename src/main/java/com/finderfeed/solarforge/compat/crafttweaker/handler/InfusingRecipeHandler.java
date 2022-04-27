package com.finderfeed.solarforge.compat.crafttweaker.handler;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;

@IRecipeHandler.For(InfusingRecipe.class)
public class InfusingRecipeHandler implements IRecipeHandler<InfusingRecipe> {

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
    public String dumpToCommandString(IRecipeManager manager, InfusingRecipe recipe) {
        return null;
    }
}
