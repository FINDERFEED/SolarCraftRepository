package com.finderfeed.solarforge.content.recipe_types.infusing_crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfusingCraftingRecipeSerializer  implements RecipeSerializer<InfusingCraftingRecipe> {



    public InfusingCraftingRecipeSerializer(){
//        this.setRegistryName(new ResourceLocation("solarforge","infusing_crafting"));

    }

    @Override
    public InfusingCraftingRecipe fromJson(ResourceLocation rl, JsonObject json) {

        JsonArray arr = json.getAsJsonArray("pattern");
        int arrsize = arr.size();
        String[] pattern = new String[arrsize];
        for (int i = 0; i < arrsize;i++){
            pattern[i] = arr.get(i).getAsString();
        }


        JsonObject keys = json.getAsJsonObject("keys");
        Map<Character, Item> ingredientMap = new HashMap<>();
        keys.entrySet().forEach((entry)->{
            ingredientMap.put(entry.getKey().charAt(0),GsonHelper.getAsItem(entry.getValue().getAsJsonObject(),"item"));
        });

        ItemStack output = GsonHelper.getAsItem(json.getAsJsonObject("output"),"item").getDefaultInstance();
        int time = json.getAsJsonPrimitive("time").getAsInt();
        int c = GsonHelper.getAsInt(json,"count",1);
        String s = json.getAsJsonPrimitive("fragment").getAsString();


        return new InfusingCraftingRecipe(rl,pattern,ingredientMap,output,time,c,s);
    }

    @Nullable
    @Override
    public InfusingCraftingRecipe fromNetwork(ResourceLocation rl, FriendlyByteBuf buf) {

        int size = buf.readInt();
        List<Item> ingrs = new ArrayList<>();
        List<Character> chars = new ArrayList<>();
        for (int i = 0;i < size;i++){
            ingrs.add(buf.readItem().getItem());
        }
        for (int i = 0;i < size;i++){
            chars.add(buf.readChar());
        }
        Map<Character, Item> ingredientMap = new HashMap<>();
        for (int i = 0;i < ingrs.size();i++){
            ingredientMap.put(chars.get(i),ingrs.get(i));
        }
        int patternlength = buf.readInt();
        String[] pattern;
        if (patternlength == 1) {
            pattern = new String[]{
                    buf.readUtf()
            };
        }else if (patternlength == 2){
            pattern = new String[]{
                    buf.readUtf(),
                    buf.readUtf()
            };
        }else{
            pattern = new String[]{
                    buf.readUtf(),
                    buf.readUtf(),
                    buf.readUtf()
            };
        }

        ItemStack output = buf.readItem();
        int time = buf.readInt();
        int count = buf.readInt();
        String s = buf.readUtf();
        return new InfusingCraftingRecipe(rl,pattern,ingredientMap,output,time,count,s);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, InfusingCraftingRecipe recipe) {
        Map<Character, Item> ingredientMap = recipe.getDefinitions();
        String[] pattern = recipe.getPattern();

        buf.writeInt(ingredientMap.values().size());
        ingredientMap.values().forEach((ingre)->buf.writeItem(ingre.getDefaultInstance()));
        ingredientMap.keySet().forEach(buf::writeChar);

        buf.writeInt(pattern.length);
        for (String s : pattern){
            buf.writeUtf(s);
        }

        buf.writeItem(recipe.getOutput());
        buf.writeInt(recipe.getTime());
        buf.writeInt(recipe.getOutputCount());
        buf.writeUtf(recipe.getFragmentID());
    }
}
