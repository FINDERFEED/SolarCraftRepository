package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.SolarCraftTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenAbilityScreenPacket {

    private String[] bindedAbilities;
    private boolean dontOpen;
    private int energy;
    /**
     * SHOULD BE 4 STRINGS!
     */
    public OpenAbilityScreenPacket(int energy,boolean dontOpen,String... bindedAbilities) {
        this.dontOpen = dontOpen;
        this.energy = energy;
        this.bindedAbilities = bindedAbilities;
    }

    public OpenAbilityScreenPacket(FriendlyByteBuf buf) {
        this.bindedAbilities = new String[4];
        this.dontOpen = buf.readBoolean();
        this.energy = buf.readInt();
        bindedAbilities[0] = buf.readUtf();
        bindedAbilities[1] = buf.readUtf();
        bindedAbilities[2] = buf.readUtf();
        bindedAbilities[3] = buf.readUtf();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(dontOpen);
        buf.writeInt(energy);
        buf.writeUtf(bindedAbilities[0]);
        buf.writeUtf(bindedAbilities[1]);
        buf.writeUtf(bindedAbilities[2]);
        buf.writeUtf(bindedAbilities[3]);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ClientHelpers.getClientPlayer().getPersistentData().putInt(SolarCraftTags.RAW_SOLAR_ENERGY,energy);
            if (!dontOpen) {
                ClientHelpers.openAbilityScreen(bindedAbilities);
            }else{
                ClientHelpers.updateAbilityScreen(bindedAbilities);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
