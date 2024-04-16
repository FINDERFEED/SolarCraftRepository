package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.SCConfigs;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;

public class DispelAbility extends AbstractAbility{
    public DispelAbility() {
        super("solar_dispel");
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

    @Override
    public RunicEnergyCost getCastCost() {
        return SCConfigs.ABILITIES.dispelAbilityStats.getDefaultAbilityStats().getCastCost();
    }

    @Override
    public int getBuyCost() {
        return SCConfigs.ABILITIES.dispelAbilityStats.getDefaultAbilityStats().getBuyCost();
    }
}
