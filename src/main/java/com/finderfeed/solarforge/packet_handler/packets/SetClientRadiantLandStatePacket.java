package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetClientRadiantLandStatePacket extends AbstractPacket {

    private boolean cleaned;

    public SetClientRadiantLandStatePacket(boolean cleaned){
        this.cleaned = cleaned;
    }

    public SetClientRadiantLandStatePacket(FriendlyByteBuf buf){
        this.cleaned = buf.readBoolean();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(cleaned);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientHelpers.setClientRadiantLandState(cleaned);
        });
        ctx.get().setPacketHandled(true);
    }
}
