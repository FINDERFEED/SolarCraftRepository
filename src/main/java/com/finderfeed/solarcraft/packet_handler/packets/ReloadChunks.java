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

    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(ClientHelpers::reloadChunks);
        ctx.setPacketHandled(true);
    }
}