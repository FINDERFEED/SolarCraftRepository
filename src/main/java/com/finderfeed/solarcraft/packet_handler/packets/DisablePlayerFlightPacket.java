package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("disable_player_flight_packet")
public class DisablePlayerFlightPacket extends FDPacket {

    private boolean disable;

    public DisablePlayerFlightPacket(boolean disable){

        this.disable = disable;
    }



    @Override
    public void read(FriendlyByteBuf buf) {
        this.disable = buf.readBoolean();

    }

    public void toBytes(FriendlyByteBuf buf){

        buf.writeBoolean(disable);
    }

//    public void handle(DisablePlayerFlightPacket packet,PlayPayloadContext ctx){
//
//            ClientHelpers.disableFlight(disable);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.disableFlight(disable);
    }

    public static void send(ServerPlayer player, boolean disable){
        player.getAbilities().mayfly = !disable;
        if (disable) {
            player.getAbilities().flying = false;
        }
        FDPacketUtil.sendToPlayer(player,new DisablePlayerFlightPacket(disable));
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
