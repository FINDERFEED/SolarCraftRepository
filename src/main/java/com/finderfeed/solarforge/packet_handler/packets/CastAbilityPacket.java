package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.content.abilities.AbilityHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CastAbilityPacket {
    private final int index;
    public CastAbilityPacket(FriendlyByteBuf buf){

        index = buf.readInt();

    }
    public CastAbilityPacket(int index){
    this.index = index;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(index);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer enti = ctx.get().getSender();
            AbilityHelper.castAbility(enti.getLevel(),enti, AbilityHelper.getBindedAbilityID(enti,index));
        });
        ctx.get().setPacketHandled(true);
    }
}
