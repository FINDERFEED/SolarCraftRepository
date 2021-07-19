package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TriggerProgressionShaderPacket {

    public TriggerProgressionShaderPacket() {

    }

    public TriggerProgressionShaderPacket(PacketBuffer buf) {

    }

    public void toBytes(PacketBuffer buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(ClientHelpers::triggerProgressionUnlockShader);
        ctx.get().setPacketHandled(true);
    }
}