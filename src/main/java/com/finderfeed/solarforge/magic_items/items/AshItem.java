package com.finderfeed.solarforge.magic_items.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class AshItem extends Item {
    public AshItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }



    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 500;
    }


}
