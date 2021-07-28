package com.finderfeed.solarforge.recipe_types;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;

public class InfusingRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<InfusingRecipe> {


    public InfusingRecipeSerializer(){
        this.setRegistryName(new ResourceLocation("solarforge","infusing"));
    }

    @Override
    public InfusingRecipe fromJson(ResourceLocation loc, JsonObject file) {
        JsonElement inputElement1 = GsonHelper.getAsJsonObject(file, "input1");
        JsonElement inputElement2 = GsonHelper.getAsJsonObject(file, "input2");
        JsonElement inputElement3 = GsonHelper.getAsJsonObject(file, "input3");
        JsonElement inputElement4 = GsonHelper.getAsJsonObject(file, "input4");
        JsonElement inputElement5 = GsonHelper.getAsJsonObject(file, "input5");
        JsonElement inputElement6 = GsonHelper.getAsJsonObject(file, "input6");
        JsonElement inputElement7 = GsonHelper.getAsJsonObject(file, "input7");
        JsonElement inputElement8 = GsonHelper.getAsJsonObject(file, "input8");
        JsonElement inputElement9 = GsonHelper.getAsJsonObject(file, "input9");
//        Ingredient input1 = Ingredient.fromJson(inputElement1);
//        Ingredient input2 = Ingredient.fromJson(inputElement2);
//        Ingredient input3 = Ingredient.fromJson(inputElement3);
//        Ingredient input4 = Ingredient.fromJson(inputElement4);
//        Ingredient input5 = Ingredient.fromJson(inputElement5);
//        Ingredient input6 = Ingredient.fromJson(inputElement6);
//        Ingredient input7 = Ingredient.fromJson(inputElement7);
//        Ingredient input8 = Ingredient.fromJson(inputElement8);
//        Ingredient input9 = Ingredient.fromJson(inputElement9);
        Ingredient input1 = getIngredient(inputElement1,1);
        Ingredient input2 = getIngredient(inputElement2,2);
        Ingredient input3 = getIngredient(inputElement3,3);
        Ingredient input4 = getIngredient(inputElement4,4);
        Ingredient input5 = getIngredient(inputElement5,5);
        Ingredient input6 = getIngredient(inputElement6,6);
        Ingredient input7 = getIngredient(inputElement7,7);
        Ingredient input8 = getIngredient(inputElement8,8);
        Ingredient input9 = getIngredient(inputElement9,9);
        ItemStack output = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(file, "result")).getDefaultInstance();
        int infusingTime = GsonHelper.getAsInt(file, "time", 20);
        String child = GsonHelper.getAsString(file,"fragment");
        int reqEnergy = GsonHelper.getAsInt(file, "energy", 0);
        String tag = GsonHelper.getAsString(file,"tag","");
        int count = GsonHelper.getAsInt(file, "count", 1);
        return new InfusingRecipe(loc,input1,input2,input3,input4,input5,input6,input7,input8,input9,output,infusingTime,child,reqEnergy,tag,count);
    }

    private Ingredient getIngredient(JsonElement element,int i){
        String ingr = GsonHelper.getAsString((JsonObject) element,"item");
        if (ingr.equals("minecraft:air")){
            return Ingredient.EMPTY;
        }else {
            return Ingredient.fromJson(element);
        }
    }

    @Nullable
    @Override
    public InfusingRecipe fromNetwork(ResourceLocation loc, FriendlyByteBuf buf) {
        Ingredient input1 = Ingredient.fromNetwork(buf);
        Ingredient input2 = Ingredient.fromNetwork(buf);
        Ingredient input3 = Ingredient.fromNetwork(buf);
        Ingredient input4 = Ingredient.fromNetwork(buf);
        Ingredient input5 = Ingredient.fromNetwork(buf);
        Ingredient input6 = Ingredient.fromNetwork(buf);
        Ingredient input7 = Ingredient.fromNetwork(buf);
        Ingredient input8 = Ingredient.fromNetwork(buf);
        Ingredient input9 = Ingredient.fromNetwork(buf);
        ItemStack output = buf.readItem();
        int infusingTime = buf.readInt();
        String child = buf.readUtf();
        int reqEnergy = buf.readInt();
        String tag = buf.readUtf();
        int count = buf.readInt();
        return new InfusingRecipe(loc,input1,input2,input3,input4,input5,input6,input7,input8,input9,output,infusingTime,child,reqEnergy,tag,count);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, InfusingRecipe recipeType) {
        recipeType.input1.toNetwork(buf);
        recipeType.input2.toNetwork(buf);
        recipeType.input3.toNetwork(buf);
        recipeType.input4.toNetwork(buf);
        recipeType.input5.toNetwork(buf);
        recipeType.input6.toNetwork(buf);
        recipeType.input7.toNetwork(buf);
        recipeType.input8.toNetwork(buf);
        recipeType.input9.toNetwork(buf);
        buf.writeItemStack(recipeType.output, true);
        buf.writeInt(recipeType.infusingTime);
        buf.writeUtf(recipeType.child);
        buf.writeInt(recipeType.requriedEnergy);
        buf.writeUtf(recipeType.tag);
        buf.writeInt(recipeType.count);
    }
}
