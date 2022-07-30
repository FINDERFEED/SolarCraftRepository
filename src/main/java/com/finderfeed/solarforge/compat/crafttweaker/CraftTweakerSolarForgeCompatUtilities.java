package com.finderfeed.solarforge.compat.crafttweaker;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class CraftTweakerSolarForgeCompatUtilities {

    private static final char[] CHAR_ARRAY = "ABCDEFGHIJKLM".toCharArray();

    public static final Map<String, Character> map = new HashMap<>();

    public static String[] getIngPattern(List<IIngredient> inputs, int maxLength, String errorMessage){
        List<String> pattern = new ArrayList<>();
        Map<Ingredient, String> map = new HashMap<>();
        if (inputs.size() != maxLength){
            throw new IllegalArgumentException(errorMessage);
        }
        String construct = "";
        for (int i = 0; i < inputs.size(); i++){
            final IIngredient stack = inputs.get(i);
            if (stack.asVanillaIngredient().isEmpty()){
                construct = construct.concat(" ");
            }
            else {
                if (map.containsKey(stack.asVanillaIngredient())){
                    construct = construct.concat(map.get(stack.asVanillaIngredient()));
                }
                else {
                    String s = (String.valueOf(CHAR_ARRAY[i]));
                    construct = construct.concat(s);
                    map.put(stack.asVanillaIngredient(), s);
                }

            }
            if (resetConstructor(i, maxLength)){
                pattern.add(construct);
                construct = "";
            }
        }
        return pattern.stream().toArray(String[]::new);
    }
    public static String[][] getIItemStackPattern(List<IItemStack> inputs, int maxLength, String errorMessage){
        List<String> pattern = new ArrayList<>();
        Map<Item, String> map = new HashMap<>();
        if (inputs.size() != maxLength){
            throw new IllegalArgumentException(errorMessage);
        }
        String construct = "";
        for (int i = 0; i < inputs.size(); i++){
            final ItemStack stack = inputs.get(i).getInternal();
            if (stack.isEmpty()){
                construct = construct.concat(" ");
            }
            else {
                if (map.containsKey(stack.getItem())){
                    construct = construct.concat(map.get(stack.getItem()));
                }
                else {
                    String s = (String.valueOf(CHAR_ARRAY[i]));
                    construct = construct.concat(s);
                    map.put(stack.getItem(), s);
                }

            }
            if (resetConstructor(i, maxLength)){
                pattern.add(construct);
                construct = "";
            }
        }
        String[] arr = pattern.stream().toArray(String[]::new);
        String[] cut = cutArray(arr);
        return new String[][]{arr,cut};
    }

    private static String[] cutArray(String[] array){
        int leftXBorder = -1;
        int rightXBorder = -1;
        int upBorder = -1;
        int downBorder = -1;
        List<String> strings = new ArrayList<>();
        for (int i = 0;i < array.length;i++){
            char r1 = array[0].charAt(i);
            char r2 = array[1].charAt(i);
            char r3 = array[2].charAt(i);
            if (!allCharsEmpty(r1,r2,r3)){
                if (leftXBorder == -1){
                    leftXBorder = i;
                }else{
                    rightXBorder = i;
                }
            }
            char u1 = array[i].charAt(0);
            char u2 = array[i].charAt(1);
            char u3 = array[i].charAt(2);
            if (!allCharsEmpty(u1,u2,u3)){
                if (upBorder == -1){
                    upBorder = i;
                }else{
                    downBorder = i;
                }
            }
        }
        if (rightXBorder == -1) rightXBorder = leftXBorder;
        if (downBorder == -1) downBorder = upBorder;
        for (int i = upBorder; i <= downBorder;i++){
            StringBuilder builder = new StringBuilder();
            for (int g = leftXBorder;g <= rightXBorder;g++){
                builder.append(array[i].charAt(g));
            }
            strings.add(builder.toString());
        }
        return strings.toArray(String[]::new);
    }

    private static boolean allCharsEmpty(char r1,char r2,char r3){
        return r1 == ' ' && r2 == ' ' && r3 == ' ';
    }

    public static Map<Character, Item> getInputItemMap(List<IItemStack> flattenedList, String[] patterns){
        Map<Character, Item> toReturn = new HashMap<>();
        if (flattenedList.size() != 9) {
            throw new IllegalArgumentException("Invalid Map configuration!");
        }
        for (int i = 0; i < flattenedList.size(); i++){
            Character toAnalyze = String.join("", patterns).toCharArray()[i];
            IItemStack stack = flattenedList.get(i);
            if (!toReturn.containsKey(toAnalyze)) {
                toReturn.put(toAnalyze, stack.getInternal().getItem());
            }
        }
        return toReturn;
    }
    public static Map<Character, Ingredient> getInputIngredientMap(List<IIngredient> flattenedList, String[] patterns){
        Map<Character, Ingredient> toReturn = new HashMap<>();
        if (flattenedList.size() != 13) {
            throw new IllegalArgumentException("Invalid Map configuration!");
        }
        for (int i = 0; i < flattenedList.size(); i++) {
            Character toAnalyze = String.join("", patterns).toCharArray()[i];
            IIngredient stack = flattenedList.get(i);
            if (!toReturn.containsKey(toAnalyze)) {
                toReturn.put(toAnalyze, stack.asVanillaIngredient());
            }
        }
        return toReturn;
    }
    public static <T> List<T> flatten (T[][] inputs){
        List<T> toReturn = new ArrayList<>();
        for (T[] arrayStack : inputs){
            toReturn.addAll(Arrays.asList(arrayStack));
        }
        return toReturn;
    }

    public static boolean resetConstructor(int i, int maxLength){
        return ((maxLength == 9 && (i + 1) % 3 == 0)) || ((maxLength == 13 && (i == 2 || i == 4 || i == 7 || i == 9 || i == 12)));
    }

    public static void setupCatalystsMap(){
        map.put("solarforge:tera_rune_block", 'T');
        map.put("solarforge:zeta_rune_block", 'Z');
        map.put("solarforge:kelda_rune_block", 'K');
        map.put("solarforge:urba_rune_block", 'R');
        map.put("solarforge:fira_rune_block", 'F');
        map.put("solarforge:ardo_rune_block", 'A');
        map.put("solarforge:ultima_rune_block", 'U');
        map.put("solarforge:giro_rune_block", 'G');
    }

    public static Map<String, Character> getMap(){
        return map;
    }

    public static String readCatalysts(Block[][] catalysts){
        if (catalysts[0].length != 3){
            throw new IllegalArgumentException("Catalysts must be a 4x3 Two dimensional Array with blocks! Valid blocks will be parsed and invalid ones will be read as nothing special!");
        }
        String toReturn = "";
        List<Block> flattened = (List<Block>) flatten(catalysts);
        for (Block block : flattened){
            if (map.containsKey(block.getRegistryName().toString())){
                toReturn = toReturn.concat(String.valueOf(map.get(block.getRegistryName().toString())));
            }
            else {
                toReturn += " ";
            }
        }
        return toReturn;
    }

    public static <T> Object[][] convertMap(Map<Character, T> thing, int maxLength){
        List<T> itemList = thing.values().stream().toList();
        if (maxLength == 9){
            return new Object[][] {
                     {itemList.get(0), itemList.get(1), itemList.get(2)},  {itemList.get(3), itemList.get(4), itemList.get(5)},  {itemList.get(6), itemList.get(7), itemList.get(8)}
            };
        }
        {
            return new Object[][]{
                    {itemList.get(0), itemList.get(1), itemList.get(2)}, {itemList.get(3), itemList.get(4)}, {itemList.get(5), itemList.get(6), itemList.get(7)}, {itemList.get(8), itemList.get(9)}, {itemList.get(10), itemList.get(11), itemList.get(12)},
            };
        }
    }
}
