package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.RunicEnergyGiver;
import com.finderfeed.solarcraft.content.items.solar_wand.*;
import com.finderfeed.solarcraft.content.runic_network.algorithms.RunicEnergyPath;
import com.finderfeed.solarcraft.content.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
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
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Locale;

public class RENetworkConnectivityWandAction implements WandAction<EmptyWandData> {
    @Override
    public InteractionResult run(WandUseContext context, EmptyWandData data) {

        Level world = context.level();
        UseOnContext ctx = context.context();
        BlockPos pos = ctx.getClickedPos();
        Player player = context.player();
        if (!world.isClientSide){
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof AbstractRunicEnergyContainer container){

                List<BlockEntity> entities = container.findNearestRepeatersOrPylons(container.getBlockPos(),world);

                for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
                    boolean connected = false;
                    for (BlockEntity entity : entities) {
                        if (entity instanceof BaseRepeaterTile repeater && repeater.getAcceptedEnergyTypes().contains(type)) {
                           List<BlockPos> path = new RunicEnergyPath(type,pos).build(repeater);
                           if (path != null){
                               connected = true;
                               break;
                           }
                        } else if (entity instanceof RunicEnergyGiver giver && giver.getTypes().contains(type)) {
                            connected = true;
                            break;
                        }
                    }
                    if (connected){
                        player.sendSystemMessage(Component.literal(type.id.toUpperCase(Locale.ROOT) + ": CONNECTED")
                                .withStyle(ChatFormatting.GREEN));
                    }else{
                        player.sendSystemMessage(Component.literal(type.id.toUpperCase(Locale.ROOT) + ": NOT CONNECTED")
                                .withStyle(ChatFormatting.RED));
                    }
                }
            }else if (tile instanceof BaseRepeaterTile repeater){
                for (RunicEnergy.Type type : repeater.getAcceptedEnergyTypes()){
                    List<BlockPos> path = new RunicEnergyPath(type,repeater.getBlockPos()).build(repeater);
                    if (path != null){
                        player.sendSystemMessage(Component.literal(type.id.toUpperCase(Locale.ROOT) + ": CONNECTED")
                                .withStyle(ChatFormatting.GREEN));
                    }else{
                        player.sendSystemMessage(Component.literal(type.id.toUpperCase(Locale.ROOT) + ": NOT CONNECTED")
                                .withStyle(ChatFormatting.RED));
                    }
                }
            }

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
        return Component.translatable("solarcraft.wand_action.re_network");
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(SolarCraft.MOD_ID,"check_runic_network_connectivity");
    }

    @Override
    public ItemStack getIcon() {
        return SolarcraftItems.REPEATER.get().getDefaultInstance();
    }
}
