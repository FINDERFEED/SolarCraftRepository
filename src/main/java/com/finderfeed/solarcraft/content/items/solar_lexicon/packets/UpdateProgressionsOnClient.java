package com.finderfeed.solarcraft.content.items.solar_lexicon.packets;

import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("update_progressions_on_client")
public class UpdateProgressionsOnClient extends FDPacket {

    private CompoundTag progressionData;



    public UpdateProgressionsOnClient(Player player){
        progressionData = new CompoundTag();
        for (Progression progression : Progression.allProgressions){
            progressionData.putBoolean(progression.getProgressionCode(),Helpers.hasPlayerCompletedProgression(progression,player));
        }
    }


    public UpdateProgressionsOnClient(FriendlyByteBuf buf) {
        this.progressionData = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(progressionData);
    }
//    public void handle(PlayPayloadContext ctx){
//
//            ClientPacketHandles.handleProgressionUpdate(progressionData);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleProgressionUpdate(progressionData);
    }

    public static void send(ServerPlayer player){
        FDPacketUtil.sendToPlayer(player,new UpdateProgressionsOnClient(player));
//        SCPacketHandler.INSTANCE.sendTo(new UpdateProgressionsOnClient(player),player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
