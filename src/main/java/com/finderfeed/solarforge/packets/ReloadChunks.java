package com.finderfeed.solarforge.packets;

import com.finderfeed.solarforge.ClientHelpers;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ReloadChunks {

    public ReloadChunks() {

    }

    public ReloadChunks(PacketBuffer buf) {

    }

    public void toBytes(PacketBuffer buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(ClientHelpers::reloadChunks);
        ctx.get().setPacketHandled(true);
    }
}