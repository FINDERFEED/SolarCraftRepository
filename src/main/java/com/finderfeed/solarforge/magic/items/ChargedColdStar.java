package com.finderfeed.solarforge.magic.items;

import com.finderfeed.solarforge.Helpers;

import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChargedColdStar extends Item {
    public ChargedColdStar(Properties p_i48487_1_) {
        super(p_i48487_1_);

    }

    @Override
    public void inventoryTick(ItemStack p_77663_1_, Level p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if (!p_77663_2_.isClientSide && p_77663_3_ instanceof Player){
            Helpers.fireProgressionEvent((Player) p_77663_3_, Progression.IMBUED_COLD_STAR);
        }
        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }

}
