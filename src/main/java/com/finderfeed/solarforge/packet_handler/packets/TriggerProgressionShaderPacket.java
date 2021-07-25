package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class TriggerProgressionShaderPacket {

    public TriggerProgressionShaderPacket() {

    }

    public TriggerProgressionShaderPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(ClientHelpers::triggerProgressionUnlockShader);
        ctx.get().setPacketHandled(true);
    }
}