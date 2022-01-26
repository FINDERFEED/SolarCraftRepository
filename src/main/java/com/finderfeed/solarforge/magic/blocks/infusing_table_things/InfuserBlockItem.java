package com.finderfeed.solarforge.magic.blocks.infusing_table_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.items.primitive.solacraft_item_classes.SolarcraftBlockItem;
import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class InfuserBlockItem extends SolarcraftBlockItem {
    public InfuserBlockItem(Block p_i48527_1_, Properties p_i48527_2_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48527_1_, p_i48527_2_,fragmentSupplier);
    }


    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player pe) {
        if (!world.isClientSide){
            Helpers.fireProgressionEvent(pe, Progression.SOLAR_INFUSER);
        }
        super.onCraftedBy(stack,world,pe);
    }
}
