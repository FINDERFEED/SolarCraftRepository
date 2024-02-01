package com.finderfeed.solarcraft.content.recipe_types.solar_smelting;

import com.finderfeed.solarcraft.SolarCraft;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Level;


import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SolarSmeltingRecipeSerializer  implements RecipeSerializer<SolarSmeltingRecipe>{
    public SolarSmeltingRecipeSerializer(){
//        this.setRegistryName(new ResourceLocation("solarcraft","solar_smelting"));
    }
    //ShapedRecipe.itemFromJson(


    @Override
    public Codec<SolarSmeltingRecipe> codec() {
        return CODEC;
    }

    public static final Codec<SolarSmeltingRecipe> CODEC = ExtraCodecs.FLAT_JSON.flatXmap(json->{
        SolarSmeltingRecipe recipe = fromJson(json.getAsJsonObject());
        return DataResult.success(recipe);
    },res->{
        throw new RuntimeException("Serialization for Solar Smelting recipe is not implemented");
    });

    //@Override
    public static SolarSmeltingRecipe fromJson(JsonObject file) {

        List<ItemStack> stacks = new ArrayList<>();
        JsonArray array = file.getAsJsonArray("ingredients");

        for (JsonElement element : array){
            JsonObject obj = element.getAsJsonObject();
            String s = obj.get("smelting_item").getAsString();

            Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(s));

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


        ItemStack output = GsonHelper.getAsItem(file, "result").value().getDefaultInstance();
        int infusingTime = GsonHelper.getAsInt(file, "time", 20);
        return new SolarSmeltingRecipe(stacks,output,infusingTime);
    }


    @Nullable
    @Override
    public SolarSmeltingRecipe fromNetwork( FriendlyByteBuf buf) {

        int size = buf.readInt();
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < size;i++){
            stacks.add(buf.readItem());
        }

        ItemStack output = buf.readItem();
        int infusingTime = buf.readVarInt();
//        String child = buf.readUtf();
//        String category = buf.readUtf();
        return new SolarSmeltingRecipe(stacks,output,infusingTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, SolarSmeltingRecipe recipeType) {
        buf.writeInt(recipeType.getStacks().size());
        for (ItemStack stack : recipeType.getStacks()){
            buf.writeItem(stack);
        }

        buf.writeItem(recipeType.output);
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
            return Ingredient.fromJson(element,true);
        }
    }
}