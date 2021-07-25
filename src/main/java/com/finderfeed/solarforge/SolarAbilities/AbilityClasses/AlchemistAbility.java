package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

public class AlchemistAbility extends AbstractAbility{
    public AlchemistAbility() {
        super("alchemist",0, new RunicEnergyCostConstructor());
    }

    @Override
    public void cast(ServerPlayer player, ServerLevel world) {

        if (player.getPersistentData().getBoolean("solar_forge_can_player_use_"+id)){
            if (player.getPersistentData().getBoolean("is_alchemist_toggled")){
                //SolarForgePacketHandler.INSTANCE.sendTo(new ToggleAlchemistPacket(false),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                player.getPersistentData().putBoolean("is_alchemist_toggled",false);
            }else{
                // SolarForgePacketHandler.INSTANCE.sendTo(new ToggleAlchemistPacket(true),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                player.getPersistentData().putBoolean("is_alchemist_toggled",true);
            }

        }

    }
}
