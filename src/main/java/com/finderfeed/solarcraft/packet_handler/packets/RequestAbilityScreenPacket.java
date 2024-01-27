package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("request_ability_screen_packet")
public class RequestAbilityScreenPacket extends FDPacket {

    private boolean dontOpen;

    public RequestAbilityScreenPacket(boolean dontOpen) {
        this.dontOpen = dontOpen;
    }


    public RequestAbilityScreenPacket(FriendlyByteBuf buf) {
        this.dontOpen = buf.readBoolean();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(dontOpen);
    }

//    public void handle(PlayPayloadContext ctx) {
//
//            ServerPlayer player = ctx.getSender();
//            SCPacketHandler.INSTANCE.sendTo(new OpenAbilityScreenPacket(
//                            player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY),
//                            dontOpen,
//                            AbilityHelper.getBindedAbilityID(player,1),
//                            AbilityHelper.getBindedAbilityID(player,2),
//                            AbilityHelper.getBindedAbilityID(player,3),
//                            AbilityHelper.getBindedAbilityID(player,4)
//                    )
//                    ,player.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
//
//    }

    @Override
    public void serverPlayHandle(PlayPayloadContext ctx) {

        ServerPlayer player = (ServerPlayer) ctx.player().get();
        FDPacketUtil.sendToPlayer(player,new OpenAbilityScreenPacket(
                player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY),
                dontOpen,
                AbilityHelper.getBindedAbilityID(player,1),
                AbilityHelper.getBindedAbilityID(player,2),
                AbilityHelper.getBindedAbilityID(player,3),
                AbilityHelper.getBindedAbilityID(player,4)
        ));
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
