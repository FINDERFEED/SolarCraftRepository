package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;


public class AlchemistAbility extends ToggleableAbility{
    public AlchemistAbility() {
        super("alchemist", new RunicEnergyCost()
                .set(RunicEnergy.Type.TERA,2.5f)
                ,25000);
    }



}
