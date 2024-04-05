package com.finderfeed.solarcraft.client.screens.custom_slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class OutputSlot extends Slot {
    public OutputSlot(Container p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }
    @Override
    public boolean mayPlace(ItemStack p_75214_1_) {
        return false;
    }
    public static class ItemHandler extends SlotItemHandler {

        public ItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
        @Override
        public boolean mayPlace(ItemStack p_75214_1_) {
            return false;
        }
    }
}
