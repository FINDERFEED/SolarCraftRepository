package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.world.entity.player.Player;

import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("ability_index_set")
public class AbilityIndexSetPacket extends FDPacket {
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
    public static void handle(AbilityIndexSetPacket packet, PlayPayloadContext context){
        var opt = context.player();
        if (opt.isPresent()) {
            bindAbility(opt.get(), packet.index, packet.whatAbility);
        }
    }

    public static void bindAbility(Player player,int index,String abilityId){
        player.getPersistentData().putString("solar_forge_ability_binded_"+Integer.toString(index),abilityId);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
