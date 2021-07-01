package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.misc_things.ISolarEnergyContainer;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class EnergyMeter extends Item {

    public EnergyMeter(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public ActionResultType useOn(ItemUseContext ctx) {
        if (!ctx.getLevel().isClientSide && (ctx.getLevel().getBlockEntity(ctx.getClickedPos()) instanceof ISolarEnergyContainer)){
            ctx.getPlayer().displayClientMessage(new StringTextComponent(String.format("%.0f",((ISolarEnergyContainer) ctx.getLevel().getBlockEntity(ctx.getClickedPos())).getEnergy())).withStyle(TextFormatting.GOLD),true);
        }
        return ActionResultType.CONSUME;
    }
}
