package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public class RequestAbilityScreen {

    public final boolean only;

    public RequestAbilityScreen(FriendlyByteBuf buf){
        only = buf.readBoolean();
    }
    public RequestAbilityScreen(boolean onlyEnergy){
        this.only = onlyEnergy;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeBoolean(only);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer enti = ctx.get().getSender();
            SolarForgePacketHandler.INSTANCE.sendTo(new OpenAbilityScreenPacket(enti.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY),only),enti.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        });
        ctx.get().setPacketHandled(true);
    }

}
