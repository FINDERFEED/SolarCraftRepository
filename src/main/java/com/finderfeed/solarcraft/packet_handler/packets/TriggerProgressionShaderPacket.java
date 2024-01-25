package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

public class TriggerProgressionShaderPacket {

    public TriggerProgressionShaderPacket() {

    }

    public TriggerProgressionShaderPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handle(PlayPayloadContext ctx) {
        ctx.enqueueWork(ClientHelpers::triggerProgressionUnlockShader);
        
    }
}