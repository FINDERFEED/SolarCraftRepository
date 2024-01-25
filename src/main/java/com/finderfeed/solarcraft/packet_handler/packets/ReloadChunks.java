package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("reload_chunks_packet")
public class ReloadChunks extends FDPacket {

    public ReloadChunks() {

    }

    @Override
    public void read(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

//    public void handle(PlayPayloadContext ctx) {
//        ctx.enqueueWork(ClientHelpers::reloadChunks);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.reloadChunks();
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }
}