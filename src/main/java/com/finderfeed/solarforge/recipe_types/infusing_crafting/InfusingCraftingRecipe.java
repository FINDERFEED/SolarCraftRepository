package com.finderfeed.solarforge.recipe_types.infusing_crafting;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.recipe_types.InfusingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class InfusingCraftingRecipe implements Recipe<Container> {
    public static final InfusingCraftingRecipeSerializer serializer = new InfusingCraftingRecipeSerializer();

    private final ItemStack output;
    private final String[] pattern;
    private final Map<Character, Ingredient> DEFINITIONS = new HashMap<>();

    public InfusingCraftingRecipe(String[] pattern,Map<Character, Ingredient> defs,ItemStack out){
        this.pattern = pattern;
        this.output = out;
    }

    public ItemStack getOutput() {
        return output;
    }

    public String[] getPattern() {
        return pattern;
    }

    public Map<Character, Ingredient> getDefinitions() {
        return DEFINITIONS;
    }

    @Override
    public boolean matches(Container c, Level world) {

        return false;
    }

    @Override
    public ItemStack assemble(Container c) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return serializer;
    }

    @Override
    public RecipeType<?> getType() {
        return SolarForge.INFUSING_CRAFTING_RECIPE_TYPE;
    }
}
