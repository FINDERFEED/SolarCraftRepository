package com.finderfeed.solarforge.custom_slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InputSlot extends Slot {
    public final List<ItemStack> validStacks;
    public InputSlot(Container p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_,List<ItemStack> array) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.validStacks = array;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        for (ItemStack a : validStacks){
            if (stack.getItem() == a.getItem()){
                return true;
            }
        }
        return false;
    }
}
