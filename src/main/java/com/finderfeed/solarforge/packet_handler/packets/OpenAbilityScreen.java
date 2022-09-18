package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.SolarCraftTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

@Deprecated
public class OpenAbilityScreen {

    public final int energy;
    public final boolean only;
    public OpenAbilityScreen(FriendlyByteBuf buf){
        energy = buf.readInt();
        only = buf.readBoolean();
    }
    public OpenAbilityScreen(int a, boolean onlyEnergy){
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
                ClientHelpers.openAbilityScreenOld();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
