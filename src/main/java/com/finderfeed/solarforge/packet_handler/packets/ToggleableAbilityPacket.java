package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarforge.registries.abilities.AbilitiesRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleableAbilityPacket {

    public String id;
    public boolean toggle;

    public ToggleableAbilityPacket(ToggleableAbility ability,boolean toggle){
        this.id = ability.getId();
        this.toggle = toggle;
    }

    public ToggleableAbilityPacket(FriendlyByteBuf buf){
        this.id = buf.readUtf();
        this.toggle = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(id);
        buf.writeBoolean(toggle);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            ToggleableAbility ability = (ToggleableAbility) AbilitiesRegistry.getAbilityByID(id);
            ClientHelpers.handleToggleAbilityPacket(ability,toggle);
        });
        ctx.get().setPacketHandled(true);
    }

}
