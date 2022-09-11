package com.finderfeed.solarforge.local_library.other;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ItemRator {

    private ItemStack[] stacks;
    private int iterator = 0;
    private int x = 0;
    private int y = 0;


    public ItemRator(Ingredient ingr){
        if (!ingr.isEmpty()) {
            this.stacks = ingr.getItems();
        }
    }

    public ItemRator(Ingredient ingr,int x,int y){
        if (!ingr.isEmpty()) {
            this.stacks = ingr.getItems();
        }
        this.x = x;
        this.y = y;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
