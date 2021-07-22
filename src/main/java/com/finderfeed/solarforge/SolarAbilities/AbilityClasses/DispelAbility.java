package com.finderfeed.solarforge.SolarAbilities.AbilityClasses;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.server.ServerWorld;

public class DispelAbility extends AbstractAbility{
    public DispelAbility() {
        super("solar_dispel",400,new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.FIRA,350)
        .addRunicEnergy(RunicEnergy.Type.ZETA,500)
        .addRunicEnergy(RunicEnergy.Type.KELDA,100));
    }

    @Override
    public void cast(ServerPlayerEntity playerEntity, ServerWorld world) {
        super.cast(playerEntity, world);
        if (allowed) {

            Object[] arr =  playerEntity.getActiveEffects().toArray();
            for (int i = 0; i < arr.length; i++) {
                if (!((EffectInstance)arr[i]).getEffect().isBeneficial()) {
                    playerEntity.removeEffect(((EffectInstance)arr[i]).getEffect());
                }
            }

        }

    }
}
