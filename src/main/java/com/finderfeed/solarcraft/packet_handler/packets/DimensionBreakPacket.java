package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("dimension_break_packet")
public class DimensionBreakPacket extends FDPacket {

    public DimensionBreakPacket(){

    }


    @Override
    public void read(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf){

    }
//    public void handle(DimensionBreakPacket packet,PlayPayloadContext ctx){
//        ClientPacketHandles.handleDimBreakPacket();
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleDimBreakPacket();
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
