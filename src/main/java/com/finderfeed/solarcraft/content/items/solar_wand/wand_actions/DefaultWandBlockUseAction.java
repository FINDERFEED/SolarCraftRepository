package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_wand.*;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/**
 * Only serverside!
 */
public class DefaultWandBlockUseAction implements WandAction<EmptyWandData> {

    @Override
    public InteractionResult run(WandUseContext context, EmptyWandData data) {
        Player player = context.player();
        UseOnContext ctx = context.context();
        BlockPos pos = ctx.getClickedPos();
        Level world = context.level();
        if (!world.isClientSide){
            IWandable wandable = null;
            if (world.getBlockEntity(pos) instanceof IWandable w) {
                wandable = w;
            }else if (world.getBlockState(pos).getBlock() instanceof IWandable w){
                wandable = w;
            }
            if (wandable != null){
                wandable.onWandUse(pos,player);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public WandDataSerializer<EmptyWandData> getWandDataSerializer() {
        return EmptyWandData.SERIALIZER;
    }

    @Override
    public WandActionType getActionType(Player player) {
        return WandActionType.BLOCK;
    }


    @Override
    public Component getActionDescription() {
        return Component.translatable("solarcraft.wand_action.default_block_use");
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(SolarCraft.MOD_ID,"on_block_use");
    }

    @Override
    public ItemStack getIcon() {
        return SCItems.INFUSER_ITEM.get().getDefaultInstance();
    }
}
