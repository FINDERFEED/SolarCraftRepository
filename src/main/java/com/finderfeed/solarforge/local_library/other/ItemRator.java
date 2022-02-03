package com.finderfeed.solarforge.local_library.other;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ItemRator {

    private ItemStack[] stacks;
    private int iterator = 0;

    public ItemRator(Ingredient ingr){
        this.stacks = ingr.getItems();
    }

    public void next(){
        if (iterator + 1 >= stacks.length){
            iterator = 0;
        }else{
            iterator++;
        }
    }

    public ItemStack getCurrentStack(){
        return stacks[iterator];
    }


}
