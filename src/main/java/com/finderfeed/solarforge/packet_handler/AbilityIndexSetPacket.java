package com.finderfeed.solarforge.packet_handler;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityIndexSetPacket {
    private int index;
    private int whatAbility;

    public AbilityIndexSetPacket(FriendlyByteBuf buf){
    int [] arr = buf.readVarIntArray();
    index = arr[0];
    whatAbility = arr[1];
    }
    public AbilityIndexSetPacket(int[] arr){
        this.index = arr[0];
        this.whatAbility = arr[1];
    }
    public void toBytes(FriendlyByteBuf buf){
    buf.writeVarIntArray(new int[]{index, whatAbility});
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer enti = ctx.get().getSender();
            Player entity = (Player)enti;
            entity.getPersistentData().putInt("solar_forge_ability_binded_"+Integer.toString(index),whatAbility);
            System.out.println("ability set");
        });
        ctx.get().setPacketHandled(true);
    }
}
