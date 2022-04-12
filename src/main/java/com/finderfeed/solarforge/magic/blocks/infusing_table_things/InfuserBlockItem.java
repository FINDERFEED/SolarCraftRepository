package com.finderfeed.solarforge.magic.blocks.infusing_table_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.items.primitive.solacraft_item_classes.SolarcraftBlockItem;
import com.finderfeed.solarforge.magic.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class InfuserBlockItem extends SolarcraftBlockItem {
    public InfuserBlockItem(Block p_i48527_1_, Properties p_i48527_2_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48527_1_, p_i48527_2_,fragmentSupplier);
    }

//
//    @Override
//    public void onCraftedBy(ItemStack stack, Level world, Player pe) {
//        if (!world.isClientSide){
//            Helpers.fireProgressionEvent(pe, Progression.SOLAR_INFUSER);
//        }
//        super.onCraftedBy(stack,world,pe);
//    }
//
//    @Override
//    public void appendHoverText(ItemStack p_40572_, @Nullable Level p_40573_, List<Component> cmps, TooltipFlag p_40575_) {
//
//        super.appendHoverText(p_40572_, p_40573_, cmps, p_40575_);
//    }
}
