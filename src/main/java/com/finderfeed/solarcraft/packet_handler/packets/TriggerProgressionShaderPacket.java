package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("trigger_progression_shader_packet")
public class TriggerProgressionShaderPacket extends FDPacket {

    public TriggerProgressionShaderPacket() {

    }

    public TriggerProgressionShaderPacket(FriendlyByteBuf buf) {

    }


//    public void handle(PlayPayloadContext ctx) {
//        ctx.enqueueWork(ClientHelpers::triggerProgressionUnlockShader);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.triggerProgressionUnlockShader();
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }
}