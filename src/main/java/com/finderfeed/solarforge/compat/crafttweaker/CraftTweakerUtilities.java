package com.finderfeed.solarforge.compat.crafttweaker;

import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftTweakerUtilities {

    private static final char[] CHAR_ARRAY = "ABCDEFGHIJ".toCharArray();

    public static String[] getPattern(List<IItemStack> inputs, int maxLength, String errorMessage){
        List<String> pattern = new ArrayList<>();
        Map<Item, String> map = new HashMap<>();
        if (inputs.size() != maxLength){
            throw new IllegalArgumentException(errorMessage);
        }
        String construct = "";
        for (int i = 0; i < inputs.size(); i++){
            final IItemStack stack = inputs.get(i);
            if (stack.isEmpty()){
                construct = construct.concat(" ");
            }
            else {
                if (map.containsKey(stack.getInternal().getItem())){
                    construct = construct.concat(map.get(stack.getInternal().getItem()));
                }
                else {
                    String s = (String.valueOf(CHAR_ARRAY[i]));
                    construct = construct.concat(s);
                    map.put(stack.getInternal().getItem(), s);
                }

            }
            if ((i + 1) % 3 == 0){
                pattern.add(construct);
                construct = "";
            }
        }
        return pattern.stream().toArray(String[]::new);
    }
    public static Map<Character, Item> getInputMaps(List<IItemStack> flattenedList, String[] patterns, int columns){
        Map<Character, Item> toReturn = new HashMap<>();
        if (flattenedList.size() != patterns.length * columns) {
            throw new IllegalArgumentException("Invalid Map configuration!");
        }
        for (int i = 0; i < flattenedList.size(); i++){
            Character toAnalyze = String.join("", patterns).toCharArray()[i];
            IItemStack stack = flattenedList.get(i);
            toReturn.put(toAnalyze, stack.getInternal().getItem());
        }
        return toReturn;
    }
    public static List<IItemStack> flatten (IItemStack[][] inputs){
        List<IItemStack> toReturn = new ArrayList<>();
        for (IItemStack[] arrayStack : inputs){
            for (IItemStack stack : arrayStack){
                toReturn.add(stack);
            }
        }
        return toReturn;
    }
    private boolean resetConstructor(int i, int length, String constructor){

    }
}
