package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.minecraft.network.FriendlyByteBuf;

@Packet("cast_ability_packet")
public class CastAbilityPacket extends FDPacket {
    private int index;
    public CastAbilityPacket(int index){
        this.index = index;
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        index = buf.readInt();

    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(index);
    }
//    public static void handle(CastAbilityPacket packet, PlayPayloadContext ctx){
//
//            ServerPlayer enti = (ServerPlayer) ctx.player().get();
//            AbilityHelper.castAbility((ServerLevel) enti.level(),enti, AbilityHelper.getBindedAbilityID(enti,packet.index));
//
//    }

    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {
        ServerPlayer enti = (ServerPlayer) ctx.player().get();
        AbilityHelper.castAbility((ServerLevel) enti.level(),enti, AbilityHelper.getBindedAbilityID(enti,index));
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        toBytes(friendlyByteBuf);
    }
}
