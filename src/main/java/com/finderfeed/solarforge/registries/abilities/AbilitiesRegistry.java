package com.finderfeed.solarforge.registries.abilities;

import com.finderfeed.solarforge.abilities.ability_classes.*;

import java.util.*;


public class AbilitiesRegistry {

    private static final List<AbstractAbility> ABILITIES = new ArrayList<>();
    private static final List<ToggleableAbility> TOGGLEABLE_ABILITIES = new ArrayList<>();
    private static final Map<String,AbstractAbility> ABILITY_MAP = new HashMap<>();

    public static final LightningAbility LIGHTNING = register("lightning", new LightningAbility());
    public static final FireballAbility FIREBALL =  register("fireball", new FireballAbility());
    public static final AbstractAbility SOLAR_STRIKE = register("solar_strike", new SolarStrikeAbility());
    public static final AbstractAbility DISARM = register("disarm",new DisarmAbility());
    public static final AbstractAbility DISPEL = register("dispel",new DispelAbility());
    public static final ToggleableAbility ALCHEMIST = register("alchemist",new AlchemistAbility());
    public static final AbstractAbility METEORITE = register("meteorite",new MeteoriteAbility());
    public static final AbstractAbility HEAL = register("heal",new HealAbility());
    //public static final AbstractAbility MAGIC_BOLT = register("magic_bolt",new MagicBoltAbility());



    public static <T extends AbstractAbility> T register(String id, T ability){
        if (!ABILITIES.contains(ability)){
            ABILITIES.add(ability);
            if (ability instanceof ToggleableAbility a){
                TOGGLEABLE_ABILITIES.add(a);
            }
        }
        if (!ABILITY_MAP.containsKey(id)){
            ABILITY_MAP.put(id,ability);
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
