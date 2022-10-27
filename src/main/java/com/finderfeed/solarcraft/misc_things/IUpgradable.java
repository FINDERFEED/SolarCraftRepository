package com.finderfeed.solarcraft.misc_things;

import net.minecraft.world.item.ItemStack;

public interface IUpgradable {
    void upgrade(ItemStack prev, ItemStack stack, String tag);
}
