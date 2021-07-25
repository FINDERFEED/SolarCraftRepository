package com.finderfeed.solarforge.magic_items.blocks.blockitems;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.UldoradiumOre;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import net.minecraft.network.chat.Component;


import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class UldoradiumOreBlockitem extends BlockItem {



    public UldoradiumOreBlockitem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }

    @Override
    public Component getName(ItemStack stack) {
        if (EffectiveSide.get().isClient()){
            return ClientHelpers.getNameBasedOnProgression(stack);
        }
        return super.getName(stack);
    }



}
