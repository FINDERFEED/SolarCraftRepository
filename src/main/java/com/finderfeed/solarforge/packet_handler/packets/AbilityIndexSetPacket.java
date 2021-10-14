package com.finderfeed.solarforge.packet_handler.packets;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityIndexSetPacket {
    private int index;
    private String whatAbility;

    public AbilityIndexSetPacket(FriendlyByteBuf buf){
        index = buf.readInt();
        whatAbility = buf.readUtf();
    }
    public AbilityIndexSetPacket(int arr,String id){
        this.index = arr;
        this.whatAbility = id;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(index);
        buf.writeUtf(whatAbility);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer enti = ctx.get().getSender();
            Player entity = (Player)enti;
            entity.getPersistentData().putString("solar_forge_ability_binded_"+Integer.toString(index),whatAbility);

        });
        ctx.get().setPacketHandled(true);
    }
}
