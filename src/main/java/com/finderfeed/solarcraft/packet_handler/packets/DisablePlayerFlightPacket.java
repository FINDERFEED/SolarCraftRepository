package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.NetworkDirection;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DisablePlayerFlightPacket {

    private boolean disable;

    public DisablePlayerFlightPacket(boolean disable){

        this.disable = disable;
    }

    public DisablePlayerFlightPacket(FriendlyByteBuf buffer){

        this.disable = buffer.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf){

        buf.writeBoolean(disable);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ClientHelpers.disableFlight(disable);
        });
        ctx.get().setPacketHandled(true);
    }


    public static void send(ServerPlayer player,boolean disable){
        player.getAbilities().mayfly = !disable;
        if (disable) {
            player.getAbilities().flying = false;
        }
        SCPacketHandler.INSTANCE.sendTo(new DisablePlayerFlightPacket(disable),player.connection.connection,
                NetworkDirection.PLAY_TO_CLIENT);
    }
}
