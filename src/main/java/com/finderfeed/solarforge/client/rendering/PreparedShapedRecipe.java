package com.finderfeed.solarforge.client.rendering;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PreparedShapedRecipe {

    private List<Item> pattern;
    private ItemStack output;

    private PreparedShapedRecipe(){}

    public static PreparedShapedRecipe create(){
        return new PreparedShapedRecipe();
    }

    public PreparedShapedRecipe withPattern(List<Item> pat){
        this.pattern = pat;
        return this;
    }

    public PreparedShapedRecipe withOutput(Item i,int count){
        this.output = i.getDefaultInstance();
        this.output.setCount(count);
        return this;
    }

    public ItemStack getOutput() {
        return output;
    }

    public List<Item> getPattern() {
        return pattern;
    }
}
