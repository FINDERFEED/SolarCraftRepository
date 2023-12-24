package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
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
    public void handle(NetworkEvent.Context ctx){
        ctx.enqueueWork(()->{
            ServerPlayer enti = ctx.getSender();
            AbilityHelper.castAbility((ServerLevel) enti.level(),enti, AbilityHelper.getBindedAbilityID(enti,index));
        });
        ctx.setPacketHandled(true);
    }
}
