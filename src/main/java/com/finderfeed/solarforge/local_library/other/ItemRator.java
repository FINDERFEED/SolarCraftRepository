package com.finderfeed.solarforge.local_library.other;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ItemRator {

    private ItemStack[] stacks;
    private int iterator = 0;

    public ItemRator(Ingredient ingr){
        if (!ingr.isEmpty()) {
            this.stacks = ingr.getItems();
        }
    }

    public void next(){
        if (stacks == null) return;
        if (iterator + 1 >= stacks.length){
            iterator = 0;
        }else{
            iterator++;
        }
    }

    public ItemStack getCurrentStack(){
        if (stacks == null) return ItemStack.EMPTY;
        return stacks[iterator];
    }


}
