package com.finderfeed.solarcraft.content.items.solar_lexicon.packets;

import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("open_lexicon_screen_packet")
public class OpenScreenPacket extends FDPacket {

    public OpenScreenPacket(FriendlyByteBuf buf) {

    }

    public OpenScreenPacket(){

    }



    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.openLexiconScreen();
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }
}
