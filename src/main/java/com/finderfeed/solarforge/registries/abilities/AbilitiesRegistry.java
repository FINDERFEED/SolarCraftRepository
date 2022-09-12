package com.finderfeed.solarforge.registries.abilities;

import com.finderfeed.solarforge.content.abilities.ability_classes.*;

import java.util.*;


public class AbilitiesRegistry {

    private static final List<AbstractAbility> ABILITIES = new ArrayList<>();
    private static final List<ToggleableAbility> TOGGLEABLE_ABILITIES = new ArrayList<>();
    private static final Map<String,AbstractAbility> ABILITY_MAP = new HashMap<>();

    public static final LightningAbility LIGHTNING = register(new LightningAbility());
    public static final FireballAbility FIREBALL =  register(new FireballAbility());
    public static final AbstractAbility SOLAR_STRIKE = register(new SolarStrikeAbility());
    public static final AbstractAbility DISARM = register(new DisarmAbility());
    public static final AbstractAbility DISPEL = register(new DispelAbility());
    public static final ToggleableAbility ALCHEMIST = register(new AlchemistAbility());
    public static final AbstractAbility METEORITE = register(new MeteoriteAbility());
    public static final AbstractAbility HEAL = register(new HealAbility());
    public static final AbstractAbility MAGIC_BOLT = register(new MagicBoltAbility());



    public static <T extends AbstractAbility> T register(T ability){
        if (!ABILITIES.contains(ability)){
            ABILITIES.add(ability);
            if (ability instanceof ToggleableAbility a){
                TOGGLEABLE_ABILITIES.add(a);
            }
        }
        if (!ABILITY_MAP.containsKey(ability.id)){
            ABILITY_MAP.put(ability.id,ability);
        }
        return ability;
    }

    public static List<ToggleableAbility> getToggleableAbilities(){
        return TOGGLEABLE_ABILITIES;
    }

    public static List<AbstractAbility> getAllAbilities(){
        return ABILITIES;
    }

    public static AbstractAbility getAbilityByID(String id){
        return ABILITY_MAP.get(id);
    }

    public static void init(){}
}
