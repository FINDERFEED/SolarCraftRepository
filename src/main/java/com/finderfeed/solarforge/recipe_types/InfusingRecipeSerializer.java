package com.finderfeed.solarforge.recipe_types;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
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
import java.util.Map;

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

        double ARDO = GsonHelper.getAsDouble(file, "ardo", 0);
        double KELDA = GsonHelper.getAsDouble(file, "kelda", 0);
        double FIRA = GsonHelper.getAsDouble(file, "fira", 0);
        double TERA = GsonHelper.getAsDouble(file, "tera", 0);
        double ZETA = GsonHelper.getAsDouble(file, "zeta", 0);
        double URBA = GsonHelper.getAsDouble(file, "urba", 0);
        Map<RunicEnergy.Type,Double> costs = Map.of(
                RunicEnergy.Type.URBA,URBA,
                RunicEnergy.Type.KELDA,KELDA,
                RunicEnergy.Type.ZETA,ZETA,
                RunicEnergy.Type.TERA,TERA,
                RunicEnergy.Type.ARDO,ARDO,
                RunicEnergy.Type.FIRA,FIRA
        );
        return new InfusingRecipe(loc,input1,input2,input3,input4,input5,input6,input7,input8,input9,output,infusingTime,child,reqEnergy,tag,count,costs);
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
        double ARDO =buf.readDouble();
        double KELDA = buf.readDouble();
        double FIRA =buf.readDouble();
        double TERA =buf.readDouble();
        double ZETA =buf.readDouble();
        double URBA =buf.readDouble();
        Map<RunicEnergy.Type,Double> costs = Map.of(
                RunicEnergy.Type.URBA,URBA,
                RunicEnergy.Type.KELDA,KELDA,
                RunicEnergy.Type.ZETA,ZETA,
                RunicEnergy.Type.TERA,TERA,
                RunicEnergy.Type.ARDO,ARDO,
                RunicEnergy.Type.FIRA,FIRA
        );

        return new InfusingRecipe(loc,input1,input2,input3,input4,input5,input6,input7,input8,input9,output,infusingTime,child,reqEnergy,tag,count,costs);
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
        buf.writeDouble(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.ARDO));
        buf.writeDouble(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.KELDA));
        buf.writeDouble(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.FIRA));
        buf.writeDouble(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.TERA));
        buf.writeDouble(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.ZETA));
        buf.writeDouble(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.URBA));
    }
}
