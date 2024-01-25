package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.RunePattern;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.minecraft.core.BlockPos;


import java.util.function.Supplier;

@Packet("runic_table_packet")
public class RunicTablePacket extends FDPacket {


    public int xPressPos;
    public int yPressPos;
    public BlockPos pos;

    public RunicTablePacket(int x,int y,BlockPos tilePos){
        this.xPressPos = x;
        this.yPressPos = y;
        this.pos = tilePos;
    }


    @Override
    public void read(FriendlyByteBuf buffer) {
        this.xPressPos = buffer.readInt();
        this.yPressPos = buffer.readInt();
        this.pos = buffer.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(xPressPos);
        buf.writeInt(yPressPos);
        buf.writeBlockPos(pos);
    }

    public void handle(PlayPayloadContext ctx) {
        
            ServerPlayer player = (ServerPlayer) ctx.player().get();
            RunePattern pattern = new RunePattern(player);
            if (player.level().getBlockEntity(pos) instanceof RunicTableTileEntity table) {
                IItemHandler h = table.getInventory();
                int runeInt = pattern.getRune(xPressPos, yPressPos);
                if (runeInt != RunePattern.OPENED) {
                    Item rune = AncientFragmentHelper.RUNES[runeInt];
                    boolean shouldLookInPlayerInventory = true;
                    for (int i = 1; i < 9; i++) {
                        ItemStack stack = h.getStackInSlot(i);
                        if (stack.getItem() == rune) {
                            stack.shrink(1);
                            pattern.setOpened(xPressPos, yPressPos);
                            shouldLookInPlayerInventory = false;
                            break;
                        }
                    }
                    if (shouldLookInPlayerInventory) {
                        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                            ItemStack stack = player.getInventory().getItem(i);
                            if (stack.getItem() == rune) {
                                stack.shrink(1);
                                pattern.setOpened(xPressPos, yPressPos);
                                break;
                            }
                        }
                    }

                    pattern.save(player);


                    if (pattern.isCompleted()) {
                        AncientFragment fragment = AncientFragmentHelper.getRandomUnlockableFragment(player);
                        if (fragment != null) {
                            AncientFragmentHelper.applyTagToFragment(h.getStackInSlot(0), fragment);
                            AncientFragmentHelper.givePlayerFragment(fragment, player);

                            RunePattern p = new RunePattern();
                            p.generate();
                            p.save(player);

                            Helpers.updateFragmentsOnClient(player);
                        } else {
                            player.sendSystemMessage(Component.literal("Couldn't find any unlockable fragment. Tell that to dev if you haven't done anything suspicious.").withStyle(ChatFormatting.RED));
                        }
                    }
                    FDPacketUtil.sendToPlayer(player,new UpdateRunePattern(player, AncientFragmentHelper.getAllUnlockableFragments(player) == null));
//                    SCPacketHandler.INSTANCE.sendTo(new UpdateRunePattern(player, AncientFragmentHelper.getAllUnlockableFragments(player) == null), player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
                }
            }

    }

    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {
        this.handle(ctx);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
