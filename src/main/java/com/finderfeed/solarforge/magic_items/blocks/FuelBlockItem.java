package com.finderfeed.solarforge.magic_items.blocks;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

import javax.annotation.Nullable;

public class FuelBlockItem extends BlockItem {

    private int time;

    public FuelBlockItem(Block p_i48527_1_, Properties p_i48527_2_,int time) {
        super(p_i48527_1_, p_i48527_2_);
        this.time = time;
    }


    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return time;
    }

}
