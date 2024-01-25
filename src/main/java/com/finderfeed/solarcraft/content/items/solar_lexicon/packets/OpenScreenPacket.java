package com.finderfeed.solarcraft.content.items.solar_lexicon.packets;

import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

public class OpenScreenPacket extends AbstractPacket {
    public OpenScreenPacket(FriendlyByteBuf buf){

    }
    public OpenScreenPacket(){

    }
    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(PlayPayloadContext ctx) {

        ctx.enqueueWork(ClientPacketHandles::openLexiconScreen);
    
    }
}
