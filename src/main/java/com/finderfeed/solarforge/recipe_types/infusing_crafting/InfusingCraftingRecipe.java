package com.finderfeed.solarforge.recipe_types.infusing_crafting;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.recipe_types.InfusingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.lwjgl.system.CallbackI;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InfusingCraftingRecipe implements Recipe<Container> {
    public static final InfusingCraftingRecipeSerializer serializer = new InfusingCraftingRecipeSerializer();

    private final ItemStack output;
    private final String[] pattern;
    private final Map<Character, Item> DEFINITIONS;
    private final int time;
    public InfusingCraftingRecipe(String[] pattern,Map<Character, Item> defs,ItemStack out,int time){
        this.pattern = pattern;
        this.DEFINITIONS = defs;

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
        Item[][] arr = new Item[3][3];
        int iterator = 0;
        for (int i =0;i < 3;i++){
            for (int g = 0; g < 3;g++){
                arr[i][g] = c.getItem(iterator).getItem();
                iterator++;
            }
        }



        return patternEquals(arr);
    }

    private boolean patternEquals(Item[][] craftingPattern){
        int rows = pattern.length;
        int cols = pattern[0].length();
        Item[][] recipePattern = new Item[rows][cols];
        for (int i =0;i < rows;i++){
            for (int g = 0; g < cols;g++){
                char c = pattern[i].charAt(g);
                if (c != ' ') {
                    Item item = DEFINITIONS.get(c);
                    recipePattern[i][g] = item;
                }else{
                    recipePattern[i][g] = Items.AIR;
                }
            }
        }



        int raznRows = 3 - rows;
        int raznCols = 3 - cols;

        boolean c = false;
        int rowSaved = -1;
        int colSaved = -1;
        for (int row = 0;row <= raznRows;row++ ){
            for (int col = 0;col <= raznCols;col++ ){
                if (check(craftingPattern, recipePattern, row, col, rows, cols)) {
                    c = true;
                    rowSaved = row;
                    colSaved = col;
                    break;
                }
            }
            if (c){
                break;
            }
        }

        if (c){
            for (int i = 0; i < 3;i++){
                for (int g = 0; g < 3;g++){
                    if ((i < rowSaved) || (i > rowSaved-1+rows)){
                        if (craftingPattern[i][g] != Items.AIR){
                            c = false;
                            break;
                        }
                    }else{
                        if (g < colSaved || (g > colSaved-1+cols)){
                            if (craftingPattern[i][g] != Items.AIR){
                                c = false;
                                break;
                            }
                        }
                    }
                }
                if (!c){
                    break;
                }
            }
        }

        return c;
    }

    private boolean check(Item[][] craftingPattern,Item[][] recipePattern,int startRowPos,int startColPos,int sizeRows,int sizeCols){
        Item[][] arr = new Item[sizeRows][sizeCols];
        int itRows = 0;
        int itCols = 0;
        for (int row = startRowPos; row < startRowPos + sizeRows;row++){
            for (int col = startColPos; col < startColPos + sizeRows;col++){
                arr[itRows][itCols] = craftingPattern[row][col];
                itCols++;
            }
            itCols = 0;
            itRows++;
        }



        return Arrays.deepEquals(arr, recipePattern);
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
