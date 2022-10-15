package com.finderfeed.solarcraft.content.items.solar_lexicon.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public class UpdateAllProgressionOnClient {
    public UpdateAllProgressionOnClient(FriendlyByteBuf buf){

    }
    public UpdateAllProgressionOnClient(){

    }

    public void toBytes(FriendlyByteBuf buf) {

    }


    public void handle(Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(()->{
                ClientHelpers.reloadProgression(ctx.get().getSender());
        });
        ctx.get().setPacketHandled(true);
    }
}
