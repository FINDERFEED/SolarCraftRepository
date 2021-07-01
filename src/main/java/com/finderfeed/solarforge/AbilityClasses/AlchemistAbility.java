package com.finderfeed.solarforge.AbilityClasses;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;

public class AlchemistAbility extends AbstractAbility{
    public AlchemistAbility(String id, int manacost) {
        super(id, manacost);
    }

    @Override
    public void cast(ServerPlayerEntity player, ServerWorld world) {

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
