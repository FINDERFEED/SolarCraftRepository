package com.finderfeed.solarforge.misc_things;

import net.minecraft.world.item.ItemStack;

public interface ITagUser {
    void doThingsWithTag(ItemStack prev,ItemStack stack,String tag);
}
