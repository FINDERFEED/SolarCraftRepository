package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.ClientHelpers;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.thread.EffectiveSide;

public class ProgressionBlockItem extends BlockItem {
    public ProgressionBlockItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }
    @Override
    public ITextComponent getName(ItemStack stack) {
        if (EffectiveSide.get().isClient()){
            return ClientHelpers.getNameBasedOnProgression(stack);
        }
        return super.getName(stack);
    }
}
