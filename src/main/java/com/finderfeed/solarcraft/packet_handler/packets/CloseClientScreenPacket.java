package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("close_client_screen_packet")
public class CloseClientScreenPacket extends FDPacket {

    public CloseClientScreenPacket(){}
    public CloseClientScreenPacket(FriendlyByteBuf buf){}

    @Override
    public void write(FriendlyByteBuf buf) {}

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.closeClientScreenPacketHandle();
    }
}
