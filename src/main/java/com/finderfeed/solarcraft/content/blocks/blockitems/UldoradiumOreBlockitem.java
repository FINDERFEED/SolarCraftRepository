package com.finderfeed.solarcraft.content.blocks.blockitems;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import net.minecraft.network.chat.Component;

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
