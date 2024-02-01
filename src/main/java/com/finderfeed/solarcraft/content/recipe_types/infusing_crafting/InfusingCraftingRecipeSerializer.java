package com.finderfeed.solarcraft.content.recipe_types.infusing_crafting;

import com.google.gson.Gson;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

import javax.annotation.Nullable;
import java.util.*;

public class InfusingCraftingRecipeSerializer  implements RecipeSerializer<InfusingCraftingRecipe> {



    public InfusingCraftingRecipeSerializer(){
    }


    @Override
    public Codec<InfusingCraftingRecipe> codec() {
        return CODEC;
    }

    public static final Codec<Character> SYMBOL_CODEC = Codec.STRING.comapFlatMap((p_312250_) -> {
        if (p_312250_.length() != 1) {
            return DataResult.error(() -> {
                return "Invalid key entry: '" + p_312250_ + "' is an invalid symbol (must be 1 character only).";
            });
        } else {
            return " ".equals(p_312250_) ? DataResult.error(() -> {
                return "Invalid key entry: ' ' is a reserved symbol.";
            }) : DataResult.success(p_312250_.charAt(0));
        }
    }, String::valueOf);


    public static Codec<String[]> STRING_ARRAY = Codec.STRING.listOf().xmap((i)->{
        return i.toArray(new String[0]);
    },(i)->{
        return new ArrayList<>(List.of(i));
    });

    public static final Codec<InfusingCraftingRecipe> CODEC = RecordCodecBuilder.create(shit-> {
        return shit.group(
                STRING_ARRAY.fieldOf("pattern").forGetter(InfusingCraftingRecipe::getPattern),
                ExtraCodecs.strictUnboundedMap(SYMBOL_CODEC,Ingredient.CODEC_NONEMPTY).fieldOf("keys").forGetter(InfusingCraftingRecipe::getDefinitions),
                ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("output").forGetter(InfusingCraftingRecipe::getOutput),
                Codec.INT.fieldOf("time").forGetter(InfusingCraftingRecipe::getTime),
                Codec.INT.fieldOf("count").forGetter(InfusingCraftingRecipe::getOutputCount),
                Codec.STRING.fieldOf("fragment").forGetter(InfusingCraftingRecipe::getFragmentID)
        ).apply(shit,InfusingCraftingRecipe::new);
    });


    @Nullable
    @Override
    public InfusingCraftingRecipe fromNetwork(FriendlyByteBuf buf) {

        int size = buf.readInt();
        List<Ingredient> ingrs = new ArrayList<>();
        List<Character> chars = new ArrayList<>();
        for (int i = 0;i < size;i++){
            Ingredient ir = Ingredient.fromNetwork(buf);
//            ingrs.add(buf.readItem().getItem());
            ingrs.add(ir);
        }
        for (int i = 0;i < size;i++){
            chars.add(buf.readChar());
        }
        Map<Character, Ingredient> ingredientMap = new HashMap<>();
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
        return new InfusingCraftingRecipe(pattern,ingredientMap,output,time,count,s);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, InfusingCraftingRecipe recipe) {
        Map<Character, Ingredient> ingredientMap = recipe.getDefinitions();
        String[] pattern = recipe.getPattern();

        buf.writeInt(ingredientMap.values().size());
        ingredientMap.values().forEach((ingre)->ingre.toNetwork(buf));
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
