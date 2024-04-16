package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.abilities.AbilityStats;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.SCConfigs;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

public class HealAbility extends AbstractAbility{
    public static String HEAL_AMOUNT = "healAmount";
    public HealAbility() {
        super("solar_heal");
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this,true)) {
            AbilityStats stats = SCConfigs.ABILITIES.healAbilityStats;
            float healAmount = stats.getStat(HEAL_AMOUNT);
            entity.heal(healAmount);
            AbilityHelper.spendAbilityEnergy(entity,this);
        }

    }

    @Override
    public RunicEnergyCost getCastCost() {
        return SCConfigs.ABILITIES.healAbilityStats.getDefaultAbilityStats().getCastCost();
    }

    @Override
    public int getBuyCost() {
        return SCConfigs.ABILITIES.healAbilityStats.getDefaultAbilityStats().getBuyCost();
    }
}
