package com.finderfeed.solarforge.recipe_types.infusing_crafting;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.recipe_types.InfusingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfusingCraftingRecipe implements Recipe<Container> {
    public static final InfusingCraftingRecipeSerializer serializer = new InfusingCraftingRecipeSerializer();

    private final ItemStack output;
    private final String[] pattern;
    private final Map<Character, Item> DEFINITIONS;
    private final Map<Item, Character> DEFINITIONS_REVERTED;
    private final int time;
    public InfusingCraftingRecipe(String[] pattern,Map<Character, Item> defs,ItemStack out,int time){
        this.pattern = pattern;
        this.DEFINITIONS = defs;
        this.DEFINITIONS_REVERTED = new HashMap<>();
        DEFINITIONS.forEach((c,item)->{
            DEFINITIONS_REVERTED.put(item,c);
        });
        this.output = out;
        this.time = time;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getTime() {
        return time;
    }

    public String[] getPattern() {
        return pattern;
    }

    public Map<Character, Item> getDefinitions() {
        return DEFINITIONS;
    }

    @Override
    public boolean matches(Container c, Level world) {
        ArrayList<String> s = new ArrayList<>();
        for (int row = 1;row <= 3;row++){
            StringBuilder builder = new StringBuilder();
            for (int collumn = 1;collumn <= 3;collumn++){
                char h = DEFINITIONS_REVERTED.get(c.getItem(collumn-1).getItem());
                builder.append(h);
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(Container c) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(SolarForge.MOD_ID,"infusing_crafting");
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
