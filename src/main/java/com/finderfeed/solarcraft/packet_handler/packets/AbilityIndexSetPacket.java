package com.finderfeed.solarcraft.packet_handler.packets;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.NetworkEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
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
    public void handle(NetworkEvent.Context ctx){
        ctx.enqueueWork(()->{
            ServerPlayer enti = ctx.getSender();
            Player entity = (Player)enti;
            entity.getPersistentData().putString("solar_forge_ability_binded_"+Integer.toString(index),whatAbility);

        });
        ctx.setPacketHandled(true);
    }
}
