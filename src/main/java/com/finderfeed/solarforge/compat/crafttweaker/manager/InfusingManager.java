package com.finderfeed.solarforge.compat.crafttweaker.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.compat.crafttweaker.CraftTweakerUtilities;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

/**
 * @docParam this <recipetype:solarforge:infusing_new>
 */
@ZenRegister
@ZenCodeType.Name("mods.solarforge.InfusingManager")
@Document("mods/SolarForge/SolarSmeltingManager")
public class InfusingManager implements IRecipeManager<InfusingRecipe> {

    /**
     * Gets the recipe type for the registry to remove from.
     *
     * @return IRecipeType of this registry.
     */
    @Override
    public RecipeType<InfusingRecipe> getRecipeType() {
        return SolarForge.INFUSING_RECIPE_TYPE;
    }

    /**
     * Adds a new recipe to the Infuser Multiblock Setup
     * @param name
     * @param output
     * @param ings
     * @param catalysts
     * @param processingTime
     * @param fragment
     * @param costs
     * @param tag
     */
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IItemStack[][] ings, Block[][] catalysts, int processingTime, String fragment, RunicEnergyCost costs, @ZenCodeType.OptionalString(value = "") String tag){
        name = fixRecipeName(name);
        ResourceLocation location = new ResourceLocation(CraftTweakerConstants.MOD_ID, name);
        List<IItemStack> listStack = CraftTweakerUtilities.flatten(ings);
        String[] patterns = CraftTweakerUtilities.getPattern(listStack, 13, "Inputs must be a special (3-2-3-2-3)x5 2D Array");
        InfusingRecipe recipe = new InfusingRecipe(location, CraftTweakerUtilities.getInputIngredientMap(listStack, patterns), patterns, CraftTweakerUtilities.readCatalysts(catalysts), output.getInternal(), processingTime, fragment, 0, tag, costs);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
}
