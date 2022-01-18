package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;


import java.util.function.Supplier;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class RunicTablePacket extends AbstractPacket {

    private final BlockPos pos;

    public RunicTablePacket(BlockPos pos){
        this.pos = pos;
    }

    public RunicTablePacket(FriendlyByteBuf buffer){
        pos = buffer.readBlockPos();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ServerPlayer playerEntity = ctx.get().getSender();
            Level world = playerEntity.getLevel();
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof RunicTableTileEntity){
                RunicTableTileEntity tile = (RunicTableTileEntity) tileEntity;
                boolean a = true;
                if (ProgressionHelper.doPlayerAlreadyHasPattern(playerEntity)){
                    for (int i = 0;i< 6;i++){
                        if (tile.getItem(i).getItem() != ProgressionHelper.RUNES[ProgressionHelper.getPlayerPattern(playerEntity)[i]]){
                            a = false;

                        }
                    }
                    if (!a){
                        playerEntity.sendMessage(new TextComponent("Pattern invalid.").withStyle(ChatFormatting.RED),playerEntity.getUUID());
                    }
                }else{
                    a = false;
                    playerEntity.sendMessage(new TextComponent("Player doesnt have a pattern in its data.").withStyle(ChatFormatting.RED),playerEntity.getUUID());
                }


                if (a){
                    if (ProgressionHelper.getAllUnlockableFragments(playerEntity) != null) {
                        ProgressionHelper.applyTagToFragment(tile.getItem(6),playerEntity);
                        AncientFragment fragment = AncientFragment.getFragmentByID(tile.getItem(6).getTagElement(ProgressionHelper.TAG_ELEMENT).getString(ProgressionHelper.FRAG_ID));
                        ProgressionHelper.givePlayerFragment(fragment,playerEntity);
                        ProgressionHelper.resetPattern(playerEntity);
                        ProgressionHelper.generateRandomPatternForPlayer(playerEntity);
                        for (int i = 0; i < 6; i++) {
                            tile.getItem(i).grow(-1);
                        }
                        SolarForgePacketHandler.INSTANCE.sendTo(new UpdatePatternOnScreen(ProgressionHelper.getPlayerPattern(playerEntity)),playerEntity.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                        Helpers.updateFragmentsOnClient(playerEntity);
                    }
                }
            }else{
                playerEntity.sendMessage(new TextComponent("Failed to locate runic table during packet handling").withStyle(ChatFormatting.RED),playerEntity.getUUID());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
