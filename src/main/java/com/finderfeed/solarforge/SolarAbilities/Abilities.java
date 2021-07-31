package com.finderfeed.solarforge.SolarAbilities;

import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.*;

import java.util.Map;

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

    public static Map<String,Abilities> BY_IDS = Map.of(
            FIREBALL.getAbility().id,FIREBALL,
            LIGHTNING.getAbility().id,LIGHTNING,
            SOLAR_STRIKE.getAbility().id,SOLAR_STRIKE,
            DISARM.getAbility().id,DISARM,
            METEORITE.getAbility().id,METEORITE,
            HEAL.getAbility().id,HEAL,
            ALCHEMIST.getAbility().id,ALCHEMIST,
            DISPEL.getAbility().id,DISPEL
    );
}
