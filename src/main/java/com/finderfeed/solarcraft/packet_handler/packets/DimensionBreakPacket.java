package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DimensionBreakPacket {

    public DimensionBreakPacket(){

    }

    public DimensionBreakPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(ClientPacketHandles::handleDimBreakPacket);
        ctx.get().setPacketHandled(true);
    }

}
