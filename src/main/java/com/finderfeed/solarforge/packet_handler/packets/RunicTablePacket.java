package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.content.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.RunePattern;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;


import java.util.function.Supplier;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class RunicTablePacket extends AbstractPacket {


    public int xPressPos;
    public int yPressPos;
    public BlockPos pos;

    public RunicTablePacket(int x,int y,BlockPos tilePos){
        this.xPressPos = x;
        this.yPressPos = y;
        this.pos = tilePos;
    }

    public RunicTablePacket(FriendlyByteBuf buffer){
        this.xPressPos = buffer.readInt();
        this.yPressPos = buffer.readInt();
        this.pos = buffer.readBlockPos();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(xPressPos);
        buf.writeInt(yPressPos);
        buf.writeBlockPos(pos);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ServerPlayer player = ctx.get().getSender();
            RunePattern pattern = new RunePattern(player);
            if (player.level.getBlockEntity(pos) instanceof RunicTableTileEntity table) {
                IItemHandler h = table.getInventory();
                int runeInt = pattern.getRune(xPressPos, yPressPos);
                if (runeInt != RunePattern.OPENED) {
                    Item rune = ProgressionHelper.RUNES[runeInt];
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
                        AncientFragment fragment = ProgressionHelper.getRandomUnlockableFragment(player);
                        if (fragment != null) {
                            ProgressionHelper.applyTagToFragment(h.getStackInSlot(0), fragment);
                            ProgressionHelper.givePlayerFragment(fragment, player);

                            RunePattern p = new RunePattern();
                            p.generate();
                            p.save(player);

                            Helpers.updateFragmentsOnClient(player);
                        } else {
                            player.sendMessage(Component.literal("Couldnt find any unlockable fragment. Tell that to dev if you havent done anything suspicious.").withStyle(ChatFormatting.RED), player.getUUID());
                        }
                    }

                    SolarForgePacketHandler.INSTANCE.sendTo(new UpdateRunePattern(player, ProgressionHelper.getAllUnlockableFragments(player) == null), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                }
            }

        });
        ctx.get().setPacketHandled(true);
    }
}
