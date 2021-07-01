package com.finderfeed.solarforge.packet_handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityIndexSetPacket {
    private int index;
    private int whatAbility;

    public AbilityIndexSetPacket(PacketBuffer buf){
    int [] arr = buf.readVarIntArray();
    index = arr[0];
    whatAbility = arr[1];
    }
    public AbilityIndexSetPacket(int[] arr){
        this.index = arr[0];
        this.whatAbility = arr[1];
    }
    public void toBytes(PacketBuffer buf){
    buf.writeVarIntArray(new int[]{index, whatAbility});
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayerEntity enti = ctx.get().getSender();
            PlayerEntity entity = (PlayerEntity)enti;
            entity.getPersistentData().putInt("solar_forge_ability_binded_"+Integer.toString(index),whatAbility);
            System.out.println("ability set");
        });
        ctx.get().setPacketHandled(true);
    }
}
