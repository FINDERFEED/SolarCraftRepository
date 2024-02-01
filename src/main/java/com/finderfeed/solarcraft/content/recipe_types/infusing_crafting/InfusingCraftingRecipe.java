package com.finderfeed.solarcraft.content.recipe_types.infusing_crafting;

import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.registries.recipe_types.SCRecipeTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.Map;

public class InfusingCraftingRecipe implements Recipe<Container> {
//    public static final InfusingCraftingRecipeSerializer serializer = new InfusingCraftingRecipeSerializer();

//    private final ResourceLocation id;
    private final ItemStack output;
    private final String[] pattern;
    private final Map<Character, Ingredient> DEFINITIONS;
    private final int time;
    private final int outputCount;
    private AncientFragment desFragment;
    private final String fragment;
    public InfusingCraftingRecipe(String[] pattern,Map<Character, Ingredient> defs,ItemStack out,int time,int outputCount,String fragment){
        this.pattern = pattern;
        this.DEFINITIONS = defs;
        this.outputCount = outputCount;
        this.output = out;
        this.time = time;
//        this.id = loc;
        this.fragment = fragment;
    }

    public String getFragmentID(){
        return fragment;
    }

    public AncientFragment getFragment() {
        if (desFragment == null) {
            desFragment = AncientFragment.getFragmentByID(fragment);
        }

        return desFragment;
    }

    public int getOutputCount() {
        return outputCount;
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

    public Map<Character, Ingredient> getDefinitions() {
        return DEFINITIONS;
    }

    @Override
    public boolean matches(Container c, Level world) {
//        Item[][] arr = new Item[3][3];
//        int iterator = 0;
//        for (int i =0;i < 3;i++){
//            for (int g = 0; g < 3;g++){
//                arr[i][g] = c.getItem(iterator).getItem();
//                iterator++;
//            }
//        }
//
//
//
//        return patternEquals(arr);
        return false;
    }

//    private boolean patternEquals(Item[][] craftingPattern){
//        int rows = pattern.length;
//        int cols = pattern[0].length();
//        Item[][] recipePattern = new Item[rows][cols];
//        for (int i =0;i < rows;i++){
//            for (int g = 0; g < cols;g++){
//                char c = pattern[i].charAt(g);
//                if (c != ' ') {
//                    Item item = DEFINITIONS.get(c);
//                    recipePattern[i][g] = item;
//                }else{
//                    recipePattern[i][g] = Items.AIR;
//                }
//            }
//        }
//
//
//
//        int raznRows = 3 - rows;
//        int raznCols = 3 - cols;
//
//        boolean c = false;
//        int rowSaved = -1;
//        int colSaved = -1;
//        for (int row = 0;row <= raznRows;row++ ){
//            for (int col = 0;col <= raznCols;col++ ){
//                if (check(craftingPattern, recipePattern, row, col, rows, cols)) {
//                    c = true;
//                    rowSaved = row;
//                    colSaved = col;
//                    break;
//                }
//            }
//            if (c){
//                break;
//            }
//        }
//
//
//
//        if (c){
//            int[][] savedArray = new int[rows*cols][2];
//            fillArray(savedArray,rowSaved,colSaved,rows,cols);
//            for (int i = 0 ; i < 3;i++){
//                for(int g = 0; g < 3;g++){
//                    if (!arrayContains(savedArray,i,true) || !arrayContains(savedArray,g,false)){
//                        if (craftingPattern[i][g] != Items.AIR){
//                            return false;
//                        }
//                    }
//
//                }
//            }
//        }
//
//        return c;
//    }

    private boolean arrayContains(int[][] arr,int num,boolean left){
        for (int i = 0; i < arr.length;i++){
            if (left){
                if (arr[i][0] == num){
                    return true;
                }
            }else{
                if (arr[i][1] == num){
                    return true;
                }
            }
        }
        return false;
    }

    private void fillArray(int[][] savedArray,int rowSaved,int colSaved,int rows,int cols){
        int iterator = 0;
        for (int i = rowSaved; i < rowSaved+rows;i++){
            for (int g = colSaved;g < colSaved+cols;g++){
                savedArray[iterator] = new int[]{i,g};
                iterator++;
            }
        }
    }

    private boolean check(Item[][] craftingPattern,Item[][] recipePattern,int startRowPos,int startColPos,int sizeRows,int sizeCols){
        Item[][] arr = new Item[sizeRows][sizeCols];
        int itRows = 0;
        int itCols = 0;
        for (int row = startRowPos; row < startRowPos + sizeRows;row++){
            for (int col = startColPos; col < startColPos + sizeCols;col++){
                arr[itRows][itCols] = craftingPattern[row][col];
                itCols++;
            }
            itCols = 0;
            itRows++;
        }



        return Arrays.deepEquals(arr, recipePattern);
    }



//    @Override
//    public ItemStack assemble(Container c) {
//        return output;
//    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

//    @Override
//    public ItemStack getResultItem() {
//        return output;
//    }


    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return output;
    }

//    @Override
//    public ResourceLocation getId() {
//        return id;
//    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SCRecipeTypes.INFUSING_CRAFTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return SCRecipeTypes.INFUSING_CRAFTING.get();
    }
}
//            for (int i = 0; i < 3;i++){
//                for (int g = 0; g < 3;g++){
//                    if ((i < rowSaved) || (i > rowSaved-1+rows)){
//                        if (craftingPattern[i][g] != Items.AIR){
//                            c = false;
//                            break;
//                        }
//                    }else{
//                        if (g < colSaved || (g > colSaved-1+cols)){
//                            if (craftingPattern[i][g] != Items.AIR){
//                                c = false;
//                                break;
//                            }
//                        }
//                    }
//                }
//                if (!c){
//                    break;
//                }
//            }