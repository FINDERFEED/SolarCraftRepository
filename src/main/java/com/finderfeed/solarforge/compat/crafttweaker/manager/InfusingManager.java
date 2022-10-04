package com.finderfeed.solarforge.compat.crafttweaker.manager;

//import com.blamejared.crafttweaker.api.CraftTweakerAPI;
//import com.blamejared.crafttweaker.api.CraftTweakerConstants;
//import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
//import com.blamejared.crafttweaker.api.annotation.ZenRegister;
//import com.blamejared.crafttweaker.api.ingredient.IIngredient;
//import com.blamejared.crafttweaker.api.item.IItemStack;
//import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
//import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.compat.crafttweaker.CraftTweakerSolarForgeCompatUtilities;
import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.content.recipe_types.infusing_new.InfusingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
//import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

/**
 * @docParam this <recipetype:solarforge:infusing_new>
 */
//@ZenRegister
//@ZenCodeType.Name("mods.solarforge.InfuserManager")
//@Document("mods/SolarForge/InfuserManager")
public class InfusingManager /*implements IRecipeManager<InfusingRecipe>*/ {

//    /**
//     * Gets the recipe type for the registry to remove from.
//     *
//     * @return IRecipeType of this registry.
//     */
//    @Override
//    public RecipeType<InfusingRecipe> getRecipeType() {
//        return SolarForge.INFUSING_RECIPE_TYPE;
//    }
//
//    /**
//     * Adds a new recipe to the Infuser Multiblock Setup. Some parameters have some constraints:
//     *
//     * The input ingredients two dimensional array must have a structure like this:
//     *```
//     *[
//     * [input1, input2, input3],
//     *
//     * [input4, input5],
//     *
//     * [input6, input7, input8],
//     *
//     * [input9, input10],
//     *
//     * [input11, input12, input13]
//     *
//     * ]
//     * ```
//     *
//     * So that it matches the Infuser Screen. Air can and should be used to pad out the recipe but the Array structure must match that or it will error.
//     *
//     * The catalysts two dimensional array must have a structure like this:
//     *```
//     * [
//     *
//     * [block1, block2, block3],
//     *
//     * [block4, block5, block6],
//     *
//     * [block7, block8, block9],
//     *
//     * [block10, block11, block12]
//     *
//     * ]
//     * ```
//     *
//     * The catalysts array is transposed like this:
//     * ```
//     * [
//     *
//     * [North],
//     *
//     * [East],
//     *
//     * [South],
//     *
//     * [West]
//     *
//     * ]
//     * ```
//     *
//     * You can find out the list of usable catalyst blocks by using the `/ct dump solarforge_catalysts` command.
//     * Any other blocks that are present in the array but aren't considered a catalyst will be ignored.
//     *
//     * You can find out possible fragment ids by using the `/ct dump solarforge_fragments` command.
//     * These will be required by the player in order to be able to craft the recipe.
//     *
//     * @param name The name of the recipe to add
//     * @param output The IItemStack to be outputted from the recipe.
//     * @param ings The input IItemStacks[]. Does not support tags.
//     * @param catalysts The required Block catalysts that should be placed in columns in order for the recipe to craft.
//     * @param solarEnergy Required solar energy for recipe.
//     * @param processingTime The amount of time it should take the recipe to craft.
//     * @param fragment The id of the fragment to use.
//     * @param costs The RunicEnergyCost this recipe has.
//     *
//     * Recipe will automatically assign itself the structure tier it needs. (If Runic energy cost > 0 tier will be Runic Energy, if solar energy > 0 tier will be Solar Energy.
//     * Solar Energy tier overrides Runic Energy tier. If both of the costs are 0, the tier will be First.).
//     *
//     * @docParam name "infuser_multiblock_test_recipe"
//     * @docParam output <item:minecraft:egg>
//     * @docParam ings [
//     * [<item:minecraft:iron_ingot>,<item:minecraft:air>, <item:minecraft:stick>],
//     * [<item:minecraft:iron_ingot>, <item:minecraft:stick>],
//     * [<item:minecraft:air>, <item:minecraft:iron_ingot>, <item:minecraft:air>],
//     * [<item:minecraft:stick>, <item:minecraft:iron_ingot>],
//     * [<item:minecraft:stick>, <item:minecraft:air>, <item:minecraft:iron_ingot>]
//     * ]
//     *
//     * @docParam catalysts [
//     *  [<block:minecraft:air>, <block:solarforge:urba_rune_block>, <block:minecraft:air>],
//     *  [<block:minecraft:air>, <block:minecraft:air>, <block:minecraft:air>],
//     *  [<block:minecraft:air>, <block:solarforge:ultima_rune_block>, <block:minecraft:air>],
//     *  [<block:minecraft:air>, <block:minecraft:air>, <block:minecraft:air>],
//     * ]
//     * @docParam solarEnergy 0
//     * @docParam processingTime 600
//     * @docParam fragment "crystals"
//     * @docParam costs RunicEnergyCost.EMPTY()
//     */
//    @ZenCodeType.Method
//    public void addRecipe(String name, IItemStack output, IIngredient[][] ings, Block[][] catalysts, int solarEnergy,int processingTime, String fragment, RunicEnergyCost costs){
//        name = fixRecipeName(name);
//        ResourceLocation location = new ResourceLocation(CraftTweakerConstants.MOD_ID, name);
//        List<IIngredient> listStack = CraftTweakerSolarForgeCompatUtilities.flatten(ings);
//        String[] patterns = CraftTweakerSolarForgeCompatUtilities.getIngPattern(listStack, 13, "Inputs must be a special (3-2-3-2-3)x5 2D Array");
//        InfusingRecipe recipe = new InfusingRecipe(location, CraftTweakerSolarForgeCompatUtilities.getInputIngredientMap(listStack, patterns), patterns, CraftTweakerSolarForgeCompatUtilities.readCatalysts(catalysts), output.getInternal(), processingTime, fragment, solarEnergy, "", costs);
//        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
//    }
}
