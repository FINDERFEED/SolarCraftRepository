package com.finderfeed.solarforge.infusing_table_things;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

public class InputSlot extends Slot {
    public final int stackSize;
    public InputSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_,int stackSize) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.stackSize = stackSize;
    }

    @Override
    public int getMaxStackSize() {
        return stackSize;
    }
}
