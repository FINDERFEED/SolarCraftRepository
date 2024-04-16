package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.registries.SCConfigs;


public class AlchemistAbility extends ToggleableAbility{

    public static final String EXPERIENCE_PER_BLOCK = "experiencePerBlock";

    public AlchemistAbility() {
        super("alchemist");
    }


    @Override
    public RunicEnergyCost getCastCost() {
        return SCConfigs.ABILITIES.alchemistAbilityStats.getDefaultAbilityStats().getCastCost();
    }

    @Override
    public int getBuyCost() {
        return SCConfigs.ABILITIES.alchemistAbilityStats.getDefaultAbilityStats().getBuyCost();
    }
}
