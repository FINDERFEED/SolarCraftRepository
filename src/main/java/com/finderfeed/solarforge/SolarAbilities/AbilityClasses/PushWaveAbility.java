package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

public class PushWaveAbility extends AbstractAbility{
    public PushWaveAbility(String id, int manacost) {
        super("",1,new RunicEnergyCostConstructor());
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        super.cast(entity, world);
    }
}
