package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions;

import com.finderfeed.solarcraft.content.items.solar_wand.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/**
 * Only serverside!
 */
public class DefaultWandBlockUseAction implements WandAction<EmptyWandData> {

    @Override
    public InteractionResult run(WandUseContext context, WandData<EmptyWandData> data) {
        Player player = context.player();
        UseOnContext ctx = context.context();
        BlockPos pos = ctx.getClickedPos();
        Level world = context.level();
        if (!world.isClientSide && world.getBlockEntity(pos) instanceof IWandable wandable){
            wandable.onWandUse(pos,player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public WandDataSerializer<EmptyWandData> getWandDataSerializer() {
        return EmptyWandData.SERIALIZER;
    }

    @Override
    public WandActionType getActionType() {
        return WandActionType.BLOCK;
    }

    @Override
    public Component getActionName() {
        return Component.translatable("solarcraft.wand_action.default_block_use");
    }
}
