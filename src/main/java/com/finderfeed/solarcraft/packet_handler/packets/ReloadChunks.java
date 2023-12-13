package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;


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