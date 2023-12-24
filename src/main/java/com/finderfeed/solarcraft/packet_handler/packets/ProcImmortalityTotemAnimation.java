package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class ProcImmortalityTotemAnimation extends AbstractPacket {



    public ProcImmortalityTotemAnimation(FriendlyByteBuf buf) {}
    public ProcImmortalityTotemAnimation(){}
    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(()->{
            DistExecutor.safeRunWhenOn(Dist.CLIENT,()-> ClientHelpers::playTotemAnimation);
        });
        ctx.setPacketHandled(true);
    }
}
