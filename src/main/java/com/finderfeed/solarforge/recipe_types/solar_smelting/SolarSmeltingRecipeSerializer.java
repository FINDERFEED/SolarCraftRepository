package com.finderfeed.solarforge.recipe_types.solar_smelting;

import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Arrays;

public class SolarSmeltingRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SolarSmeltingRecipe>{
    public SolarSmeltingRecipeSerializer(){
        this.setRegistryName(new ResourceLocation("solarforge","solar_smelting"));
    }
//ShapedRecipe.itemFromJson(
    @Override
    public SolarSmeltingRecipe fromJson(ResourceLocation loc, JsonObject file) {
        NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(file, "ingredients"));
        ItemStack output = GsonHelper.getAsItem(file, "result").getDefaultInstance();
        int infusingTime = GsonHelper.getAsInt(file, "time", 20);
        String child = GsonHelper.getAsString(file,"requires");
        String category = GsonHelper.getAsString(file,"category");
        return new SolarSmeltingRecipe(loc,nonnulllist,output,infusingTime,child,category);
    }

    @Nullable
    @Override
    public SolarSmeltingRecipe fromNetwork(ResourceLocation loc, FriendlyByteBuf buf) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(4, Ingredient.EMPTY);

        for(int j = 0; j < nonnulllist.size(); ++j) {
            nonnulllist.set(j, Ingredient.fromNetwork(buf));
        }
        ItemStack output = buf.readItem();
        int infusingTime = buf.readVarInt();
        String child = buf.readUtf();
        String category = buf.readUtf();
        return new SolarSmeltingRecipe(loc,nonnulllist,output,infusingTime,child,category);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, SolarSmeltingRecipe recipeType) {
        for (Ingredient a : recipeType.list){
            a.toNetwork(buf);
        }
        buf.writeItemStack(recipeType.output, true);
        buf.writeVarInt(recipeType.smeltingTime);
        buf.writeUtf(recipeType.child);
        buf.writeUtf(recipeType.category);

    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray arr) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for(int i = 0; i < arr.size(); ++i) {



            Ingredient ingredient = getIngredient(arr.get(i));

            if (!ingredient.isEmpty()) {
                nonnulllist.add(ingredient);
            }
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
