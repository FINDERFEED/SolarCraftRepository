package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.solar_network;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.solar_energy.Bindable;
import com.finderfeed.solarcraft.content.items.solar_wand.WandAction;
import com.finderfeed.solarcraft.content.items.solar_wand.WandActionType;
import com.finderfeed.solarcraft.content.items.solar_wand.WandDataSerializer;
import com.finderfeed.solarcraft.content.items.solar_wand.WandUseContext;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SolarNetworkBinderWandAction implements WandAction<SolarNetworkBinderWAData> {
    @Override
    public InteractionResult run(WandUseContext context, SolarNetworkBinderWAData data) {

        Level level = context.level();
        Player player = context.player();
        UseOnContext ctx = context.context();
        BlockPos clickPos = ctx.getClickedPos();

        if (!level.isClientSide){
            if (!player.isCrouching()){
                if (level.getBlockEntity(clickPos) instanceof Bindable){
                    if (data.firstPos == null) {
                        data.firstPos = clickPos;
                    } else {
                        if (!clickPos.equals(data.firstPos) && level.getBlockEntity(data.firstPos) instanceof Bindable tile1) {
                            if (!tile1.bind(clickPos)) {
                                player.displayClientMessage(Component.translatable("solarcraft.failed_to_bind")
                                        .withStyle(ChatFormatting.RED), false);
                            }
                        }
                        data.firstPos = null;
                    }
                }
            }else{
                data.firstPos = null;
                player.displayClientMessage(Component.literal("Position cleared"),true);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public WandDataSerializer<SolarNetworkBinderWAData> getWandDataSerializer() {
        return SolarNetworkBinderWASerializer.SERIALIZER;
    }

    @Override
    public WandActionType getActionType(Player player) {
        return WandActionType.BLOCK;
    }

    @Override
    public Component getActionDescription() {
        return Component.translatable("solarcraft.wand_action.solar_network_binder_wa");
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(SolarCraft.MOD_ID,"solarcraft_solar_network_binder_wa");
    }

    @Override
    public ItemStack getIcon() {
        return SolarcraftItems.SOLAR_NETWORK_BINDER.get().getDefaultInstance();
    }
}
