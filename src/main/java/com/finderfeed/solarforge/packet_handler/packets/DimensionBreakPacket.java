package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.content.abilities.AbilityHelper;
import com.finderfeed.solarforge.helpers.ClientHelpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
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
        ctx.get().enqueueWork(ClientHelpers::handleDimBreakPacket);
        ctx.get().setPacketHandled(true);
    }

}
