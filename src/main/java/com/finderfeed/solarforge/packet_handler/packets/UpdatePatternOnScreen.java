package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public class UpdatePatternOnScreen {

    private final int[] pattern;


    public UpdatePatternOnScreen(int[] pattern) {
        this.pattern = pattern;
    }

    public UpdatePatternOnScreen(FriendlyByteBuf buf) {
        pattern = buf.readVarIntArray();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(pattern);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientHelpers.updatePatternOnScreen(pattern);
        });
        ctx.get().setPacketHandled(true);
    }
}