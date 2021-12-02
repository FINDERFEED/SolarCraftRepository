package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.ToggleAlchemistPacket;
import com.finderfeed.solarforge.registries.abilities.AbilitiesRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkDirection;


public class AlchemistAbility extends ToggleableAbility{
    public AlchemistAbility() {
        super("alchemist",0.5, new RunicEnergyCostConstructor(),25000);
    }


    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        super.cast(entity, world);
        SolarForgePacketHandler.INSTANCE.sendTo(new ToggleAlchemistPacket(this.isToggled(entity)), entity.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
