package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.RuneEnergyPylonTile;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SolarcraftDebugStick extends Item {
    public SolarcraftDebugStick(Properties p_41383_) {
        super(p_41383_);
    }




    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        if (!ctx.getLevel().isClientSide && (ctx.getLevel().getBlockEntity(ctx.getClickedPos()) instanceof DebugTarget target) && ctx.getPlayer() != null && !ctx.getPlayer().isCrouching()){
            target.getDebugStrings().forEach((string)->{
                ctx.getPlayer().sendMessage(new TextComponent(string),ctx.getPlayer().getUUID());
            });
        }

        if (!ctx.getLevel().isClientSide && ctx.getPlayer() != null && ctx.getPlayer().isCrouching()){
            if (ctx.getLevel().getBlockEntity(ctx.getClickedPos()) instanceof RuneEnergyPylonTile pylon){
                pylon.addEnergy(pylon.getEnergyType(),200);
                ctx.getPlayer().sendMessage(new TextComponent(Float.toString(pylon.getCurrentEnergy())),ctx.getPlayer().getUUID());
            }
        }
        return InteractionResult.SUCCESS;
    }
}
