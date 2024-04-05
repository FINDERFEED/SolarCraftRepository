package com.finderfeed.solarcraft.content.recipe_types.infusing_new;

import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipeSerializer;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;


import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class InfusingRecipeSerializer implements RecipeSerializer<InfusingRecipe> {



    public InfusingRecipeSerializer(){

    }


    @Override
    public Codec<InfusingRecipe> codec() {
        return CODEC;
    }

//    public static final Codec<InfusingRecipe> CODEC = ExtraCodecs.FLAT_JSON.flatXmap(json->{
//        InfusingRecipe recipe = fromJson(json.getAsJsonObject());
//        return DataResult.success(recipe);
//    },(res)->{
//        throw new RuntimeException("Serialization for infusing recipe is not implemented");
//    });

    public static final Codec<String> STRICT_12_CHARACTERS_STRING_CODEC = Codec.STRING.comapFlatMap(str->{
        if (str.length() != 12){
            return DataResult.error(()->"Catalysts string length should be equal to 12");
        }
        return DataResult.success(str);
    },f->f);

    public static final Codec<InfusingRecipe> CODEC = RecordCodecBuilder.create(p->p.group(
            ExtraCodecs.strictUnboundedMap(InfusingCraftingRecipeSerializer.SYMBOL_CODEC,Ingredient.CODEC_NONEMPTY).fieldOf("items").forGetter(recipe->recipe.INGR_MAP),
            InfusingCraftingRecipeSerializer.STRING_ARRAY.fieldOf("pattern").forGetter(recipe->recipe.fiveRowPattern),
            ExtraCodecs.strictOptionalField(STRICT_12_CHARACTERS_STRING_CODEC,"catalysts","            ").forGetter(recipe->recipe.getCatalysts()),
            ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(recipe->recipe.output),
            Codec.INT.fieldOf("time").forGetter(recipe->recipe.infusingTime),
            Codec.STRING.fieldOf("fragment").forGetter(recipe->recipe.fragID),
            ExtraCodecs.strictOptionalField(Codec.INT,"energy",0).forGetter(recipe->recipe.requriedSolarEnergy),
            ExtraCodecs.strictOptionalField(Codec.STRING,"tag","").forGetter(recipe->recipe.tag),
            ExtraCodecs.strictOptionalField(RunicEnergyCost.CODEC,"re_cost",RunicEnergyCost.EMPTY).forGetter(recipe->recipe.RUNIC_ENERGY_COST)
    ).apply(p,InfusingRecipe::new));

    //@Override
    public static InfusingRecipe fromJson(JsonObject file) {
        JsonArray pattern = file.getAsJsonArray("pattern");
        JsonObject items = file.getAsJsonObject("items");


        Map<Character,Ingredient> ingredientMap = new HashMap<>();

        ingredientMap.put(' ',Ingredient.of(Items.AIR));
        for (Map.Entry<String,JsonElement> obj : items.entrySet()){
            ingredientMap.put(obj.getKey().charAt(0),Ingredient.fromJson(obj.getValue(),false));
        }

        String[] fiveRowPattern = {
                pattern.get(0).getAsString(),
                pattern.get(1).getAsString(),
                pattern.get(2).getAsString(),
                pattern.get(3).getAsString(),
                pattern.get(4).getAsString()
        };

        ItemStack output = GsonHelper.getAsItem(file.get("result").getAsJsonObject(),
                "item"
                ).value().getDefaultInstance();
        int infusingTime = GsonHelper.getAsInt(file, "time", 20);
        String child = GsonHelper.getAsString(file,"fragment");
        int reqEnergy = GsonHelper.getAsInt(file, "energy", 0);
        String tag = GsonHelper.getAsString(file,"tag","");
        int count = GsonHelper.getAsInt(file, "count", 1);

        float ARDO = GsonHelper.getAsFloat(file, "ardo", 0);
        float KELDA = GsonHelper.getAsFloat(file, "kelda", 0);
        float FIRA = GsonHelper.getAsFloat(file, "fira", 0);
        float TERA = GsonHelper.getAsFloat(file, "tera", 0);
        float ZETA = GsonHelper.getAsFloat(file, "zeta", 0);
        float URBA = GsonHelper.getAsFloat(file, "urba", 0);
        float GIRO = GsonHelper.getAsFloat(file, "giro", 0);
        float ULTIMA = GsonHelper.getAsFloat(file, "ultima", 0);

        RunicEnergyCost costs = new RunicEnergyCost().set(RunicEnergy.Type.URBA,URBA)
                .set(RunicEnergy.Type.KELDA,KELDA)
                .set(RunicEnergy.Type.TERA,TERA)
                .set(RunicEnergy.Type.ZETA,ZETA)
                .set(RunicEnergy.Type.FIRA,FIRA)
                .set(RunicEnergy.Type.ULTIMA,ULTIMA)
                .set(RunicEnergy.Type.GIRO,GIRO)
                .set(RunicEnergy.Type.ARDO,ARDO);
        costs.nullifyUnusedTypes();
        String catalysts = GsonHelper.getAsString(file,"catalysts","            ");
        if (catalysts.length() != 12){
            throw new IllegalStateException("Catalysts length cant be != 12, recipe: " + file);
        }
        output.setCount(count);
        return new InfusingRecipe(ingredientMap,fiveRowPattern,catalysts,output,infusingTime,child,reqEnergy,tag,costs);
    }

    private Ingredient getIngredient(JsonElement element,int i){
        String ingr = GsonHelper.getAsString((JsonObject) element,"item");
        if (ingr.equals("minecraft:air")){
            return Ingredient.EMPTY;
        }else {
            return Ingredient.fromJson(element,true);
        }
    }

    @Nullable
    @Override
    public InfusingRecipe fromNetwork(FriendlyByteBuf buf) {
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
        output.setCount(count);
        float ARDO =buf.readFloat();
        float KELDA = buf.readFloat();
        float FIRA =buf.readFloat();
        float TERA =buf.readFloat();
        float ZETA =buf.readFloat();
        float URBA =buf.readFloat();
        float GIRO =buf.readFloat();
        float ULTIMA =buf.readFloat();
//        Map<RunicEnergy.Type,Double> costs = Map.of(
//                RunicEnergy.Type.URBA,URBA,
//                RunicEnergy.Type.KELDA,KELDA,
//                RunicEnergy.Type.ZETA,ZETA,
//                RunicEnergy.Type.TERA,TERA,
//                RunicEnergy.Type.ARDO,ARDO,
//                RunicEnergy.Type.FIRA,FIRA,
//                RunicEnergy.Type.GIRO,GIRO,
//                RunicEnergy.Type.ULTIMA,ULTIMA
//        );
        RunicEnergyCost costs = new RunicEnergyCost().set(RunicEnergy.Type.URBA,URBA)
                .set(RunicEnergy.Type.KELDA,KELDA)
                .set(RunicEnergy.Type.TERA,TERA)
                .set(RunicEnergy.Type.ZETA,ZETA)
                .set(RunicEnergy.Type.FIRA,FIRA)
                .set(RunicEnergy.Type.ULTIMA,ULTIMA)
                .set(RunicEnergy.Type.GIRO,GIRO)
                .set(RunicEnergy.Type.ARDO,ARDO);
        String cat = buf.readUtf();
        return new InfusingRecipe(ingredientMap,pattern,cat,output,infusingTime,child,reqEnergy,tag,costs);
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




        buf.writeItem(recipeType.output);
        buf.writeInt(recipeType.infusingTime);
        buf.writeUtf(recipeType.fragID);
        buf.writeInt(recipeType.requriedSolarEnergy);
        buf.writeUtf(recipeType.tag);
        buf.writeInt(recipeType.count);
        buf.writeFloat(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.ARDO));
        buf.writeFloat(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.KELDA));
        buf.writeFloat(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.FIRA));
        buf.writeFloat(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.TERA));
        buf.writeFloat(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.ZETA));
        buf.writeFloat(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.URBA));
        buf.writeFloat(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.GIRO));
        buf.writeFloat(recipeType.RUNIC_ENERGY_COST.get(RunicEnergy.Type.ULTIMA));
        buf.writeUtf(recipeType.getCatalysts());
    }
}
