package com.finderfeed.solarforge.content.items.primitive;

import com.finderfeed.solarforge.content.blocks.solar_energy.SolarEnergyContainer;
import com.finderfeed.solarforge.misc_things.ISolarEnergyContainer;

import net.minecraft.world.item.Item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class EnergyMeter extends Item {

    public EnergyMeter(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (!ctx.getLevel().isClientSide && (ctx.getLevel().getBlockEntity(ctx.getClickedPos()) instanceof SolarEnergyContainer container)){
            ctx.getPlayer().displayClientMessage(Component.literal("" + container.getSolarEnergy()).withStyle(ChatFormatting.GOLD),true);
        }
        return InteractionResult.CONSUME;
    }
}
