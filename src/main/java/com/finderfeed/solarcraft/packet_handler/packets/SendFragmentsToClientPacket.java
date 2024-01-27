package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.config.JsonFragmentsHelper;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("send_fragments_to_client_packet")
public class SendFragmentsToClientPacket extends FDPacket {

    private String json;

    public SendFragmentsToClientPacket(FriendlyByteBuf buf) {
        this.json = buf.readUtf();
    }

    public SendFragmentsToClientPacket(JsonObject serializedFragments){
        this.json = JsonFragmentsHelper.jsonToString(serializedFragments);
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(json);
    }
//    public void handle(PlayPayloadContext ctx){
//
//            ClientHelpers.deserializeServersideFragmentsAndPutThemInList(JsonFragmentsHelper.jsonFromString(json));
//
//
//    }
    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.deserializeServersideFragmentsAndPutThemInList(JsonFragmentsHelper.jsonFromString(json));

    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
