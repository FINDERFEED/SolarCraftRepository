package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class RunicNetworkRepeaterBlockItem extends BlockItem {
    public RunicNetworkRepeaterBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        if (!p_41405_.isClientSide){
            if (p_41406_ instanceof Player player) {
                Helpers.fireProgressionEvent(player, Achievement.RUNIC_ENERGY_REPEATER);
            }
        }
        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
    }
}
