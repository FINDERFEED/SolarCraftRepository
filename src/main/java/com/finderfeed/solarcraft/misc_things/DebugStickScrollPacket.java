package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("debug_stick_scroll")
public class DebugStickScrollPacket extends FDPacket {

    private boolean forward;

    public DebugStickScrollPacket(boolean forward){
        this.forward = forward;
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(forward);
    }

    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {
        Player player = ctx.player().get();
        if (player.getMainHandItem().getItem() instanceof DebugStick stick){
            stick.switchMode(player.getMainHandItem(),forward);
        }
        if (player.getOffhandItem().getItem() instanceof DebugStick stick){
            stick.switchMode(player.getOffhandItem(),forward);
        }

    }
}
