package com.finderfeed.solarforge.SolarAbilities;

import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.*;

public enum Abilities {
    FIREBALL(new FireballAbility()),
    LIGHTNING(new LightningAbility()),
    SOLAR_STRIKE(new SolarStrikeAbility()),
    DISARM(new DisarmAbility()),
    METEORITE(new MeteoriteAbility()),
    HEAL(new HealAbility()),
    ALCHEMIST(new AlchemistAbility()),
    DISPEL(new DispelAbility());

    private final AbstractAbility ability;

    Abilities(AbstractAbility ability){
        this.ability = ability;
    }

    public AbstractAbility getAbility() {
        return ability;
    }

    public static Abilities[] getAll(){
        Abilities[] all = {
                FIREBALL,LIGHTNING,SOLAR_STRIKE,DISARM,METEORITE,HEAL,ALCHEMIST,DISPEL
        };
        return all;
    }
}
