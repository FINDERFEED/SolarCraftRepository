package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
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

    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(()->{
            ToggleableAbility ability = (ToggleableAbility) AbilitiesRegistry.getAbilityByID(id);
            ClientHelpers.handleToggleAbilityPacket(ability,toggle);
        });
        ctx.setPacketHandled(true);
    }

}
