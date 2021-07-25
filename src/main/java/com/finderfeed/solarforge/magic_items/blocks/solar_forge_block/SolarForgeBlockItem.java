package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class SolarForgeBlockItem extends BlockItem {
    public SolarForgeBlockItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }



    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player pe) {
        if (!world.isClientSide){
            Helpers.fireProgressionEvent(pe,Achievement.CRAFT_SOLAR_FORGE);
        }

        super.onCraftedBy(stack,world,pe);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> list, TooltipFlag p_77624_4_) {

        list.add(new TextComponent("ALL ENERGY IS LOST WHEN BROKEN!").withStyle(ChatFormatting.RED));
        super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
    }
}
