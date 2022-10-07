package com.finderfeed.solarforge.compat.crafttweaker.manager;

//import com.blamejared.crafttweaker.api.CraftTweakerAPI;
//import com.blamejared.crafttweaker.api.CraftTweakerConstants;
//import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
//import com.blamejared.crafttweaker.api.annotation.ZenRegister;
//import com.blamejared.crafttweaker.api.item.IItemStack;
//import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
//import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.recipe_types.solar_smelting.SolarSmeltingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
//import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @docParam this <recipetype:solarforge:solar_smelting>
 */
//@ZenRegister
//@ZenCodeType.Name("mods.solarforge.SolarSmeltingManager")
//@Document("mods/SolarForge/SolarSmeltingManager")
public class SolarSmeltingRecipeManager /*implements IRecipeManager<SolarSmeltingRecipe>*/ {

//    /**
//     * Gets the recipe type for the registry to remove from.
//     *
//     * @return IRecipeType of this registry.
//     */
//    @Override
//    public RecipeType<SolarSmeltingRecipe> getRecipeType() {
//        return SolarcraftRecipeTypes.SMELTING.get();
//    }
//
//    /**
//     * Uses the Solar Lens and the power of the Sun to Smelt items
//     * The sun is a deadly lazer.
//     * No IIngredients are supported currently. This can change in the future.
//     *
//     * @param name The recipe name
//     * @param output The output of the recipe
//     * @param inputs The inputs of the recipe
//     * @param time The processing time of the recipe
//     *
//     * @docParam name "test_solar_smelting_recipe"
//     * @docParam output <item:minecraft:diamond_ingot>
//     * @docParam inputs [<item:minecraft:coal>, <item:minecraft:coal>, <item:minecraft:coal>, <item:minecraft:coal>, <item:minecraft:gold_ingot>]
//     * @docParam time 200
//     */
//    @ZenCodeType.Method
//    public void addRecipe(String name, IItemStack output, IItemStack[] inputs, int time){
//        name = fixRecipeName(name);
//        ResourceLocation location = new ResourceLocation(CraftTweakerConstants.MOD_ID, name);
//        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new SolarSmeltingRecipe(location, Arrays.stream(inputs).map(IItemStack::getInternal).collect(Collectors.toList()), output.getInternal(), time)));
//    }

}
