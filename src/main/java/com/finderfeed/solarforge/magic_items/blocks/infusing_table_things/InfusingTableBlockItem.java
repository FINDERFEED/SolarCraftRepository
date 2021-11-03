package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InfusingTableBlockItem extends BlockItem {
    public InfusingTableBlockItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }


    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player pe) {
        if (!world.isClientSide){
            Helpers.fireProgressionEvent(pe, Progression.CRAFT_SOLAR_INFUSER);
        }
        super.onCraftedBy(stack,world,pe);
    }
}
