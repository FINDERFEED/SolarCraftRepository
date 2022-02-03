package com.finderfeed.solarforge.recipe_types.infusing_new;

import com.finderfeed.solarforge.misc_things.RunicEnergy;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class InfusingRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<InfusingRecipe> {



    public InfusingRecipeSerializer(){
        this.setRegistryName(new ResourceLocation("solarforge","infusing"));
    }

    @Override
    public InfusingRecipe fromJson(ResourceLocation loc, JsonObject file) {
        JsonArray pattern = file.getAsJsonArray("pattern");
        JsonObject items = file.getAsJsonObject("items");

        Map<Character,Ingredient> ingredientMap = new HashMap<>();

        ingredientMap.put(' ',Ingredient.of(Items.AIR));
        for (Map.Entry<String,JsonElement> obj : items.entrySet()){
            ingredientMap.put(obj.getKey().charAt(0),Ingredient.fromJson(obj.getValue()));
        }

        String[] fiveRowPattern = {
                pattern.get(0).getAsString(),
                pattern.get(1).getAsString(),
                pattern.get(2).getAsString(),
                pattern.get(3).getAsString(),
                pattern.get(4).getAsString()
        };


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
        double GIRO = GsonHelper.getAsDouble(file, "giro", 0);
        double ULTIMA = GsonHelper.getAsDouble(file, "ultima", 0);
        Map<RunicEnergy.Type,Double> costs = Map.of(
                RunicEnergy.Type.URBA,URBA,
                RunicEnergy.Type.KELDA,KELDA,
                RunicEnergy.Type.ZETA,ZETA,
                RunicEnergy.Type.TERA,TERA,
                RunicEnergy.Type.ARDO,ARDO,
                RunicEnergy.Type.FIRA,FIRA,
                RunicEnergy.Type.GIRO,GIRO,
                RunicEnergy.Type.ULTIMA,ULTIMA
        );
        String catalysts = GsonHelper.getAsString(file,"catalysts","            ");
        if (catalysts.length() != 12){
            throw new IllegalStateException("Catalysts length cant be != 12, recipe: " + loc.toString());
        }
        return new InfusingRecipe(loc,ingredientMap,fiveRowPattern,catalysts,output,infusingTime,child,reqEnergy,tag,count,costs);
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
        Map<Character,Ingredient> ingredientMap = new HashMap<>();
        int ingrMapSize = buf.readInt();
        for (int i = 0; i < ingrMapSize;i++){
            ingredientMap.put(buf.readChar(),Ingredient.fromNetwork(buf));
        }
        String[] pattern = {
                buf.readUtf(),
                buf.readUtf(),
                buf.readUtf(),
                buf.readUtf(),
                buf.readUtf()
        };

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
        double GIRO =buf.readDouble();
        double ULTIMA =buf.readDouble();
        Map<RunicEnergy.Type,Double> costs = Map.of(
                RunicEnergy.Type.URBA,URBA,
                RunicEnergy.Type.KELDA,KELDA,
                RunicEnergy.Type.ZETA,ZETA,
                RunicEnergy.Type.TERA,TERA,
                RunicEnergy.Type.ARDO,ARDO,
                RunicEnergy.Type.FIRA,FIRA,
                RunicEnergy.Type.GIRO,GIRO,
                RunicEnergy.Type.ULTIMA,ULTIMA
        );
        String cat = buf.readUtf();
        return new InfusingRecipe(loc,ingredientMap,pattern,cat,output,infusingTime,child,reqEnergy,tag,count,costs);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, InfusingRecipe recipeType) {

        Map<Character,Ingredient> ingredientMap = recipeType.INGR_MAP;
        int size = ingredientMap.size();
        buf.writeInt(size);
        Object[] charArr =  ingredientMap.keySet().toArray();
        for (int i = 0;i < size;i++){
            buf.writeChar((Character) charArr[i]);
            ingredientMap.get((Character) charArr[i]).toNetwork(buf);
        }

        buf.writeUtf(recipeType.fiveRowPattern[0]);
        buf.writeUtf(recipeType.fiveRowPattern[1]);
        buf.writeUtf(recipeType.fiveRowPattern[2]);
        buf.writeUtf(recipeType.fiveRowPattern[3]);
        buf.writeUtf(recipeType.fiveRowPattern[4]);



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
        buf.writeDouble(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.GIRO));
        buf.writeDouble(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.ULTIMA));
        buf.writeUtf(recipeType.getCatalysts());
    }
}
