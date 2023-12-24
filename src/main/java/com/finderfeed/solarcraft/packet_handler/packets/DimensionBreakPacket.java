package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class DimensionBreakPacket {

    public DimensionBreakPacket(){

    }

    public DimensionBreakPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }
    public void handle(NetworkEvent.Context ctx){
        ctx.enqueueWork(ClientPacketHandles::handleDimBreakPacket);
        ctx.setPacketHandled(true);
    }

}
