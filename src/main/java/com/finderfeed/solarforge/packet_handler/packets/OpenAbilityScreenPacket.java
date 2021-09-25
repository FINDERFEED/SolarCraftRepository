package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarCraftTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenAbilityScreenPacket {

    public final int energy;
    public final boolean only;
    public OpenAbilityScreenPacket(FriendlyByteBuf buf){
        energy = buf.readInt();
        only = buf.readBoolean();
    }
    public OpenAbilityScreenPacket(int a,boolean onlyEnergy){
        energy = a;
        only = onlyEnergy;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(energy);
        buf.writeBoolean(only);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ClientHelpers.getClientPlayer().getPersistentData().putInt(SolarCraftTags.RAW_SOLAR_ENERGY,energy);
            if (!only) {
                ClientHelpers.openAbilityScreen();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
