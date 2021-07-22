package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;

public class HealAbility extends AbstractAbility{
    public HealAbility() {
        super("solar_heal",250,new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.ZETA,200)
        .addRunicEnergy(RunicEnergy.Type.URBA,350)
        .addRunicEnergy(RunicEnergy.Type.ARDO,400));
    }

    @Override
    public void cast(ServerPlayerEntity entity, ServerWorld world) {
        super.cast(entity, world);
        if (allowed) {
            entity.heal(4);
        }

    }
}
