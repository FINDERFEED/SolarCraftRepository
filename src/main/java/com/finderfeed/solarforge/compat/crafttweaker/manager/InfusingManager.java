package com.finderfeed.solarforge.compat.crafttweaker.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this <recipetype:solarforge:infusing>
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

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IItemStack[][] ings){
        name = fixRecipeName(name);
        ResourceLocation location = new ResourceLocation(CraftTweakerConstants.MOD_ID, name);
        InfusingRecipe recipe = new InfusingRecipe(location, null, null, null, null, 0, null, 0, null, 0, null);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
}
