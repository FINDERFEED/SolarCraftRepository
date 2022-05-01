package com.finderfeed.solarforge.abilities.ability_classes;

import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.ToggleAlchemistPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkDirection;


public class AlchemistAbility extends ToggleableAbility{
    public AlchemistAbility() {
        super("alchemist", new RunicEnergyCost()
                .set(RunicEnergy.Type.TERA,2.5f)
                ,25000);
    }



}
