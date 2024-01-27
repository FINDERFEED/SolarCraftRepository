package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.abilities.ability_classes.ToggleableAbility;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("toggleable_ability_packet")
public class ToggleableAbilityPacket extends FDPacket {

    public String id;
    public boolean toggle;

    public ToggleableAbilityPacket(ToggleableAbility ability,boolean toggle){
        this.id = ability.getId();
        this.toggle = toggle;
    }


    public ToggleableAbilityPacket(FriendlyByteBuf buf) {
        this.id = buf.readUtf();
        this.toggle = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(id);
        buf.writeBoolean(toggle);
    }


    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ToggleableAbility ability = (ToggleableAbility) AbilitiesRegistry.getAbilityByID(id);
        ClientHelpers.handleToggleAbilityPacket(ability,toggle);

    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
