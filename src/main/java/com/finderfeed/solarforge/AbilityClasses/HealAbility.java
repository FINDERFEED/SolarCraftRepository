package com.finderfeed.solarforge.AbilityClasses;

import com.finderfeed.solarforge.capability_mana.CapabilitySolarMana;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;

public class HealAbility extends AbstractAbility{
    public HealAbility(String id, int manacost) {
        super(id, manacost);
    }

    @Override
    public void cast(ServerPlayerEntity entity, ServerWorld world) {
        if (entity.getPersistentData().getBoolean("solar_forge_can_player_use_"+id)) {

            entity.heal(4);
            super.cast(entity, world);
        }

    }
}
