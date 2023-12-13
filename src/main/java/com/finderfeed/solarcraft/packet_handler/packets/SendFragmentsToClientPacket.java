package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.config.JsonFragmentsHelper;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendFragmentsToClientPacket {

    private String json;

    public SendFragmentsToClientPacket(FriendlyByteBuf buf){
        this.json = buf.readUtf();
    }
    public SendFragmentsToClientPacket(JsonObject serializedFragments){
        this.json = JsonFragmentsHelper.jsonToString(serializedFragments);
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(json);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ClientHelpers.deserializeServersideFragmentsAndPutThemInList(JsonFragmentsHelper.jsonFromString(json));
        });
        ctx.get().setPacketHandled(true);
    }
}
