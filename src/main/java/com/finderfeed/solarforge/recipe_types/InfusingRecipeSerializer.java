package com.finderfeed.solarforge.recipe_types;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class InfusingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<InfusingRecipe> {


    public InfusingRecipeSerializer(){
        this.setRegistryName(new ResourceLocation("solarforge","infusing"));
    }

    @Override
    public InfusingRecipe fromJson(ResourceLocation loc, JsonObject file) {
        JsonElement inputElement1 = JSONUtils.isArrayNode(file, "input1") ? JSONUtils.getAsJsonArray(file, "input1") : JSONUtils.getAsJsonObject(file, "input1");
        JsonElement inputElement2 = JSONUtils.isArrayNode(file, "input2") ? JSONUtils.getAsJsonArray(file, "input2") : JSONUtils.getAsJsonObject(file, "input2");
        JsonElement inputElement3 = JSONUtils.isArrayNode(file, "input3") ? JSONUtils.getAsJsonArray(file, "input3") : JSONUtils.getAsJsonObject(file, "input3");
        JsonElement inputElement4 = JSONUtils.isArrayNode(file, "input4") ? JSONUtils.getAsJsonArray(file, "input4") : JSONUtils.getAsJsonObject(file, "input4");
        JsonElement inputElement5 = JSONUtils.isArrayNode(file, "input5") ? JSONUtils.getAsJsonArray(file, "input5") : JSONUtils.getAsJsonObject(file, "input5");
        JsonElement inputElement6 = JSONUtils.isArrayNode(file, "input6") ? JSONUtils.getAsJsonArray(file, "input6") : JSONUtils.getAsJsonObject(file, "input6");
        JsonElement inputElement7 = JSONUtils.isArrayNode(file, "input7") ? JSONUtils.getAsJsonArray(file, "input7") : JSONUtils.getAsJsonObject(file, "input7");
        JsonElement inputElement8 = JSONUtils.isArrayNode(file, "input8") ? JSONUtils.getAsJsonArray(file, "input8") : JSONUtils.getAsJsonObject(file, "input8");
        JsonElement inputElement9 = JSONUtils.isArrayNode(file, "input9") ? JSONUtils.getAsJsonArray(file, "input9") : JSONUtils.getAsJsonObject(file, "input9");
        Ingredient input1 = Ingredient.fromJson(inputElement1);
        Ingredient input2 = Ingredient.fromJson(inputElement2);
        Ingredient input3 = Ingredient.fromJson(inputElement3);
        Ingredient input4 = Ingredient.fromJson(inputElement4);
        Ingredient input5 = Ingredient.fromJson(inputElement5);
        Ingredient input6 = Ingredient.fromJson(inputElement6);
        Ingredient input7 = Ingredient.fromJson(inputElement7);
        Ingredient input8 = Ingredient.fromJson(inputElement8);
        Ingredient input9 = Ingredient.fromJson(inputElement9);
        ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(file, "result"));
        int infusingTime = JSONUtils.getAsInt(file, "time", 20);
        String child = JSONUtils.getAsString(file,"fragment");
        int reqEnergy = JSONUtils.getAsInt(file, "energy", 0);
        String tag = JSONUtils.getAsString(file,"tag","");
        int count = JSONUtils.getAsInt(file, "count", 1);
        return new InfusingRecipe(loc,input1,input2,input3,input4,input5,input6,input7,input8,input9,output,infusingTime,child,reqEnergy,tag,count);
    }

    @Nullable
    @Override
    public InfusingRecipe fromNetwork(ResourceLocation loc, PacketBuffer buf) {
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
    public void toNetwork(PacketBuffer buf, InfusingRecipe recipeType) {
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
