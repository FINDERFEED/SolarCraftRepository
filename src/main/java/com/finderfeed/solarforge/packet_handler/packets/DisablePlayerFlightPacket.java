package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

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
        SolarForgePacketHandler.INSTANCE.sendTo(new DisablePlayerFlightPacket(disable),player.connection.connection,
                NetworkDirection.PLAY_TO_CLIENT);
    }
}
