package com.finderfeed.solarcraft.misc_things;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class PhantomInventory implements Container,Iterable<ItemStack>{

    public int size;
    public NonNullList<ItemStack> INVENTORY;

    public PhantomInventory(int size){
        this.size = size;
        INVENTORY = NonNullList.withSize(size,ItemStack.EMPTY);
    }
    public PhantomInventory(IItemHandler handler){
        this.size = handler.getSlots();
        this.INVENTORY = NonNullList.withSize(size,ItemStack.EMPTY);
        for (int i = 0;i < size;i++){
            this.INVENTORY.set(i,handler.getStackInSlot(i));
        }
    }

    public PhantomInventory(NonNullList<ItemStack> handler){
        this.size = handler.size();
        this.INVENTORY = handler;

    }


    public PhantomInventory set(IItemHandler handler){
        this.size = handler.getSlots();
        this.INVENTORY = NonNullList.withSize(size,ItemStack.EMPTY);
        for (int i = 0;i < size;i++){
            this.INVENTORY.set(i,handler.getStackInSlot(i));
        }
        return this;
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
    public boolean stillValid(Player p_70300_1_) {
        return false;
    }

    @Override
    public void clearContent() {
        INVENTORY.clear();
    }

    @Nonnull
    @Override
    public Iterator<ItemStack> iterator() {
        return INVENTORY.iterator();
    }
}
