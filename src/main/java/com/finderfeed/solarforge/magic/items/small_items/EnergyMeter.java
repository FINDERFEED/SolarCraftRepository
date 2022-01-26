package com.finderfeed.solarforge.magic.items.small_items;

import com.finderfeed.solarforge.misc_things.ISolarEnergyContainer;

import net.minecraft.world.item.Item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

public class EnergyMeter extends Item {

    public EnergyMeter(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (!ctx.getLevel().isClientSide && (ctx.getLevel().getBlockEntity(ctx.getClickedPos()) instanceof ISolarEnergyContainer)){
            ctx.getPlayer().displayClientMessage(new TextComponent(String.format("%.0f",((ISolarEnergyContainer) ctx.getLevel().getBlockEntity(ctx.getClickedPos())).getEnergy())).withStyle(ChatFormatting.GOLD),true);
        }
        return InteractionResult.CONSUME;
    }
}
