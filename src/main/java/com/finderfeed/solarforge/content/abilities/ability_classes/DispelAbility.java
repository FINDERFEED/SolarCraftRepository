package com.finderfeed.solarforge.content.abilities.ability_classes;

import com.finderfeed.solarforge.content.abilities.AbilityHelper;
import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;

public class DispelAbility extends AbstractAbility{
    public DispelAbility() {
        super("solar_dispel",new RunicEnergyCost()
        .set(RunicEnergy.Type.FIRA,350)
        .set(RunicEnergy.Type.ZETA,500)
        .set(RunicEnergy.Type.KELDA,100),20000);
    }

    @Override
    public void cast(ServerPlayer playerEntity, ServerLevel world) {

        if (AbilityHelper.isAbilityUsable(playerEntity,this,true)) {

            Object[] arr =  playerEntity.getActiveEffects().toArray();
            for (int i = 0; i < arr.length; i++) {
                if (!((MobEffectInstance)arr[i]).getEffect().isBeneficial()) {
                    playerEntity.removeEffect(((MobEffectInstance)arr[i]).getEffect());
                }
            }
            AbilityHelper.spendAbilityEnergy(playerEntity,this);

        }

    }
}
