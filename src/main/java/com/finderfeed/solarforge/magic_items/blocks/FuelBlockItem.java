package com.finderfeed.solarforge.magic_items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class FuelBlockItem extends BlockItem {

    private int time;

    public FuelBlockItem(Block p_i48527_1_, Properties p_i48527_2_,int time) {
        super(p_i48527_1_, p_i48527_2_) ;
        this.time = time;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return time;
    }
}
