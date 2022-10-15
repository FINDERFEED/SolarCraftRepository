package com.finderfeed.solarcraft.content.blocks.solar_forge_block;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftBlockItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;
import java.util.List;

public class SolarForgeBlockItem extends SolarcraftBlockItem {
    public SolarForgeBlockItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_,()-> AncientFragment.SOLAR_FORGE);
    }



    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player pe) {
        if (!world.isClientSide){
            Helpers.fireProgressionEvent(pe, Progression.CRAFT_SOLAR_FORGE);
        }

        super.onCraftedBy(stack,world,pe);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> list, TooltipFlag p_77624_4_) {

        list.add(Component.literal("ALL ENERGY IS LOST WHEN BROKEN!").withStyle(ChatFormatting.RED));
        super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
    }
}
