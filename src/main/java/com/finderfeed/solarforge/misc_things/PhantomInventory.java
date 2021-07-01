package com.finderfeed.solarforge.misc_things;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class PhantomInventory implements IInventory {

    public final int size;
    public final NonNullList<ItemStack> INVENTORY;

    public PhantomInventory(int size){
        this.size = size;
        INVENTORY = NonNullList.withSize(size,ItemStack.EMPTY);
    }

    @Override
    public int getContainerSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return INVENTORY.isEmpty();
    }

    @Override
    public ItemStack getItem(int p_70301_1_) {
        return INVENTORY.get(p_70301_1_);
    }

    @Override
    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        return INVENTORY.set(p_70298_1_,new ItemStack(INVENTORY.get(p_70298_1_).getItem(),INVENTORY.get(p_70298_1_).getCount()-p_70298_2_));
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        return INVENTORY.remove(p_70304_1_);
    }

    @Override
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
        INVENTORY.set(p_70299_1_,p_70299_2_);
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(PlayerEntity p_70300_1_) {
        return false;
    }

    @Override
    public void clearContent() {
        INVENTORY.clear();
    }
}
