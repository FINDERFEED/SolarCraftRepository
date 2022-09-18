package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public class ReloadChunks {

    public ReloadChunks() {

    }

    public ReloadChunks(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(ClientHelpers::reloadChunks);
        ctx.get().setPacketHandled(true);
    }
}