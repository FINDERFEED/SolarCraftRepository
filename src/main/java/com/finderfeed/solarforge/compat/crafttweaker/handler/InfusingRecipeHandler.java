package com.finderfeed.solarforge.compat.crafttweaker.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.finderfeed.solarforge.compat.crafttweaker.CraftTweakerSolarForgeCompatUtilities;
import com.finderfeed.solarforge.compat.crafttweaker.type.ExpandRunicEnergyCost;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@IRecipeHandler.For(InfusingRecipe.class)
public class InfusingRecipeHandler implements IRecipeHandler<InfusingRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager manager, InfusingRecipe recipe) {
        String first = String.format("<recipetype:solarforge:infusing_new>.addRecipe(\"%s\", %s, [%s], [%s], %s, \"%s\", %s",
                recipe.getId(),
                new MCItemStack(recipe.getResultItem()).getCommandString(),
                "Unknown Contents",
                /*Arrays.stream(CraftTweakerSolarForgeCompatUtilities.convertMap(recipe.INGR_MAP, 13))
                        .map(row -> Arrays.stream(row)
                                .map(blindCast -> (Ingredient) blindCast)
                                .map(IIngredient::fromIngredient)
                                .map(IIngredient::getCommandString)
                                .collect(Collectors.joining(", ", "[", "]")))
                        .collect(Collectors.joining(", ", "[", "]")), */
                Arrays.stream(convertCatalysts(recipe.getCatalysts(), recipe.getCatalystDeserializer()))
                        .map(row -> Arrays.stream(row)
                                .map(ExpandBlock::getCommandString)
                                .collect(Collectors.joining(", ", "[", "]")))
                        .collect(Collectors.joining(", ", "[", "]")),
                recipe.getInfusingTime(),
                recipe.fragID,
                ExpandRunicEnergyCost.getType(recipe.RUNIC_ENERGY_COST).toString()
                );

        return first.concat(");");
    }


    private Block[][] convertCatalysts(String catalyst, Map<Character, Block> mapper){
       char[] charArray = catalyst.toCharArray();
       return new Block[][] {
               {mapper.get(charArray[0]), mapper.get(charArray[1]), mapper.get(charArray[2])}, {mapper.get(charArray[3]), mapper.get(charArray[4]), mapper.get(charArray[5])}, {mapper.get(charArray[6]), mapper.get(charArray[7]), mapper.get(charArray[8])}, {mapper.get(charArray[9]), mapper.get(charArray[10]), mapper.get(charArray[11])}
       };
    }

}
