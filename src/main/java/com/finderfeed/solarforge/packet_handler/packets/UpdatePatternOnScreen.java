package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdatePatternOnScreen {

    private final int[] pattern;


    public UpdatePatternOnScreen(int[] pattern) {
        this.pattern = pattern;
    }

    public UpdatePatternOnScreen(PacketBuffer buf) {
        pattern = buf.readVarIntArray();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeVarIntArray(pattern);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientHelpers.updatePatternOnScreen(pattern);
        });
        ctx.get().setPacketHandled(true);
    }
}