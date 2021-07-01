package com.finderfeed.solarforge.recipe_types.solar_smelting;

import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class SolarSmeltingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SolarSmeltingRecipe>{
    public SolarSmeltingRecipeSerializer(){
        this.setRegistryName(new ResourceLocation("solarforge","solar_smelting"));
    }
//ShapedRecipe.itemFromJson(
    @Override
    public SolarSmeltingRecipe fromJson(ResourceLocation loc, JsonObject file) {
        NonNullList<Ingredient> nonnulllist = itemsFromJson(JSONUtils.getAsJsonArray(file, "ingredients"));
        ItemStack output = JSONUtils.getAsItem(file, "result").getDefaultInstance();
        int infusingTime = JSONUtils.getAsInt(file, "time", 20);
        String child = JSONUtils.getAsString(file,"requires");
        String category = JSONUtils.getAsString(file,"category");
        return new SolarSmeltingRecipe(loc,nonnulllist,output,infusingTime,child,category);
    }

    @Nullable
    @Override
    public SolarSmeltingRecipe fromNetwork(ResourceLocation loc, PacketBuffer buf) {
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
    public void toNetwork(PacketBuffer buf, SolarSmeltingRecipe recipeType) {
        for (Ingredient a : recipeType.list){
            a.toNetwork(buf);
        }
        buf.writeItemStack(recipeType.output, true);
        buf.writeVarInt(recipeType.smeltingTime);
        buf.writeUtf(recipeType.child);
        buf.writeUtf(recipeType.category);

    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray p_199568_0_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for(int i = 0; i < p_199568_0_.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(p_199568_0_.get(i));
            if (!ingredient.isEmpty()) {
                nonnulllist.add(ingredient);
            }
        }

        return nonnulllist;
    }
}
