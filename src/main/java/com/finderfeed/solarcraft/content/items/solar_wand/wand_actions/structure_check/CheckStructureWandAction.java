package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_wand.*;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.WandStructureActionPacket;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;

import java.util.List;

public class CheckStructureWandAction implements WandAction<EmptyWandData> {
    @Override
    public InteractionResult run(WandUseContext context, EmptyWandData data) {
        Level level = context.level();
        UseOnContext ctx = context.context();
        BlockPos clickedPos;
        if (context.player() instanceof ServerPlayer serverPlayer && level.getBlockEntity(clickedPos = ctx.getClickedPos()) instanceof IStructureOwner owner){
            List<MultiblockStructure> structures = owner.getMultiblocks();
            SCPacketHandler.INSTANCE.sendTo(new WandStructureActionPacket(serverPlayer,structures,clickedPos),
                    serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
        return InteractionResult.SUCCESS;
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
        return Component.translatable("solarcraft.item.solar_wand.action.structure");
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(SolarCraft.MOD_ID,"check_structure");
    }

    @Override
    public ItemStack getIcon() {
        return SolarcraftItems.MAGISTONE_BRICKS.get().getDefaultInstance();
    }
}
