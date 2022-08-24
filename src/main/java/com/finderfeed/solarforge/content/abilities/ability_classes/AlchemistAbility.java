package com.finderfeed.solarforge.content.abilities.ability_classes;

import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;


public class AlchemistAbility extends ToggleableAbility{
    public AlchemistAbility() {
        super("alchemist", new RunicEnergyCost()
                .set(RunicEnergy.Type.TERA,2.5f)
                ,25000);
    }



}
