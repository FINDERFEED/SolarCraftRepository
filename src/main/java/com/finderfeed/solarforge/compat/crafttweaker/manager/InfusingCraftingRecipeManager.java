package com.finderfeed.solarforge.compat.crafttweaker.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.compat.crafttweaker.CraftTweakerSolarForgeCompatUtilities;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;

/**
 * @docParam this <recipetype:solarforge:infusing_crafting>
 */
@ZenRegister
@ZenCodeType.Name("mods.solarforge.InfusingCraftingManager")
@Document("mods/SolarForge/InfusingTableManager")
public class InfusingCraftingRecipeManager implements IRecipeManager<InfusingCraftingRecipe> {


    /**
     * Gets the recipe type for the registry to remove from.
     *
     * @return IRecipeType of this registry.
     */
    @Override
    public RecipeType<InfusingCraftingRecipe> getRecipeType() {
        return SolarForge.INFUSING_CRAFTING_RECIPE_TYPE;
    }


    /**
     * Adds a recipe to the Infusing crafting table.
     *
     * The inputs array must be a 3x3 Two dimensional array, just like the craftingTable one you are probably used to.
     * No IIngredients are supported currently. This can change in the future.
     *
     * You can get a list of usable fragment id's by running the `/ct dump solarforge_fragments` command.
     *
     * @param name The recipe name
     * @param output The {@link IItemStack} the recipe should output
     * @param inputs The inputs necessary for the recipe to craft
     * @param processingTime The amount of time the recipe should process for
     * @param fragment The fragment used in the recipe.
     *
     * @docParam name "infusing_crafting_table_recipe_test"
     * @docParam output <item:minecraft:lapis_lazuli>
     * @docParam inputs [
     * [<item:minecraft:blue_dye>, <item:minecraft:glass>,<item:minecraft:blue_dye>],
     * [<item:minecraft:blue_dye>, <item:minecraft:golden_ingot>, <item:minecraft:glass>],
     * [<item:minecraft:glass>, <item:minecraft:blue_dye>, <item:minecraft:glass>],
     * ]
     * @docParam processingTime 300
     * @docParam fragment "energy_dust"
     */
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IItemStack[][] inputs, int processingTime, String fragment){
        name = fixRecipeName(name);
        ResourceLocation location = new ResourceLocation(CraftTweakerConstants.MOD_ID, name);
        List<IItemStack> stackList =  CraftTweakerSolarForgeCompatUtilities.flatten(inputs);
        String[][] patterns = CraftTweakerSolarForgeCompatUtilities.getIItemStackPattern(stackList, 9, "Inputs must be a 3x3 Two-Dimensional Array!");
        InfusingCraftingRecipe recipe = new InfusingCraftingRecipe(location, patterns[1], CraftTweakerSolarForgeCompatUtilities.getInputItemMap(stackList, patterns[0]), output.getInternal(), processingTime, output.getAmount(), fragment);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }


}
