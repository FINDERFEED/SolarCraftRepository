package com.finderfeed.solarcraft.content.recipe_types.solar_smelting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;


import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SolarSmeltingRecipeSerializer  implements RecipeSerializer<SolarSmeltingRecipe>{
    public SolarSmeltingRecipeSerializer(){
//        this.setRegistryName(new ResourceLocation("solarcraft","solar_smelting"));
    }
    //ShapedRecipe.itemFromJson(
    @Override
    public SolarSmeltingRecipe fromJson(ResourceLocation loc, JsonObject file) {
//        NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(file, "ingredients"));

        List<ItemStack> stacks = new ArrayList<>();
        JsonArray array = file.getAsJsonArray("ingredients");

        for (JsonElement element : array){
            JsonObject obj = element.getAsJsonObject();
            Item item = GsonHelper.getAsItem(obj,"item");
            int count;
            JsonElement e = obj.get("count");
            if (e != null){
                count = e.getAsInt();
            }else{
                count = 1;
            }
            stacks.add(new ItemStack(item,count));
        }
        if (stacks.size() == 0){
            throw new RuntimeException("There cannot be zero items in recipe.");
        }


        ItemStack output = GsonHelper.getAsItem(file, "result").getDefaultInstance();
        int infusingTime = GsonHelper.getAsInt(file, "time", 20);
//        String child = GsonHelper.getAsString(file,"requires");
//        String category = GsonHelper.getAsString(file,"category");
        return new SolarSmeltingRecipe(loc,stacks,output,infusingTime);
    }

    @Nullable
    @Override
    public SolarSmeltingRecipe fromNetwork(ResourceLocation loc, FriendlyByteBuf buf) {

        int size = buf.readInt();
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < size;i++){
            stacks.add(buf.readItem());
        }

        ItemStack output = buf.readItem();
        int infusingTime = buf.readVarInt();
//        String child = buf.readUtf();
//        String category = buf.readUtf();
        return new SolarSmeltingRecipe(loc,stacks,output,infusingTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, SolarSmeltingRecipe recipeType) {
        buf.writeInt(recipeType.getStacks().size());
        for (ItemStack stack : recipeType.getStacks()){
            buf.writeItem(stack);
        }

        buf.writeItemStack(recipeType.output, true);
        buf.writeVarInt(recipeType.smeltingTime);
//        buf.writeUtf(recipeType.child);
//        buf.writeUtf(recipeType.category);

    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray arr) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for(int i = 0; i < arr.size(); ++i) {

            Ingredient ingredient = getIngredient(arr.get(i));
            nonnulllist.add(ingredient);
        }

        return nonnulllist;
    }
    private static Ingredient getIngredient(JsonElement element){
        String ingr = GsonHelper.getAsString((JsonObject) element,"item");
        if (ingr.equals("minecraft:air")){
            return Ingredient.EMPTY;
        }else {
            return Ingredient.fromJson(element);
        }
    }
}