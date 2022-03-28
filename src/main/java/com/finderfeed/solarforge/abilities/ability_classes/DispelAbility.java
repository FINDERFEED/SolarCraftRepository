package com.finderfeed.solarforge.abilities.ability_classes;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;

public class DispelAbility extends AbstractAbility{
    public DispelAbility() {
        super("solar_dispel",new RunicEnergyCostConstructor()
        .addRunicEnergy(RunicEnergy.Type.FIRA,350)
        .addRunicEnergy(RunicEnergy.Type.ZETA,500)
        .addRunicEnergy(RunicEnergy.Type.KELDA,100),20000);
    }

    @Override
    public void cast(ServerPlayer playerEntity, ServerLevel world) {
        super.cast(playerEntity, world);
        if (allowed) {

            Object[] arr =  playerEntity.getActiveEffects().toArray();
            for (int i = 0; i < arr.length; i++) {
                if (!((MobEffectInstance)arr[i]).getEffect().isBeneficial()) {
                    playerEntity.removeEffect(((MobEffectInstance)arr[i]).getEffect());
                }
            }

        }

    }
}
