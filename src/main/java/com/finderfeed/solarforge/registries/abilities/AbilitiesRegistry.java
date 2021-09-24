package com.finderfeed.solarforge.registries.abilities;

import com.finderfeed.solarforge.SolarAbilities.Abilities;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.AbstractAbility;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.FireballAbility;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.LightningAbility;
import com.finderfeed.solarforge.for_future_library.custom_registries.CustomDeferredRegistry;
import com.finderfeed.solarforge.for_future_library.custom_registries.CustomRegistryEntry;
import com.finderfeed.solarforge.registries.SolarcraftRegistries;



public class AbilitiesRegistry {

    public static final CustomDeferredRegistry<AbstractAbility> ABILITIES = CustomDeferredRegistry.create("solarforge", SolarcraftRegistries.ABILITIES);
    public static final LightningAbility LIGHTNING = (LightningAbility) ABILITIES.register("lightning", Abilities.LIGHTNING::getAbility);
    public static final FireballAbility FIREBALL = (FireballAbility) ABILITIES.register("fireball", Abilities.FIREBALL::getAbility);
    public static final AbstractAbility SOLAR_STRIKE = ABILITIES.register("solar_strike",Abilities.SOLAR_STRIKE::getAbility);
    public static final AbstractAbility DISARM = ABILITIES.register("disarm",Abilities.DISARM::getAbility);
    public static final AbstractAbility DISPEL = ABILITIES.register("dispel",Abilities.DISPEL::getAbility);
    public static final AbstractAbility ALCHEMIST = ABILITIES.register("alchemist",Abilities.ALCHEMIST::getAbility);
    public static final AbstractAbility METEORITE = ABILITIES.register("meteorite",Abilities.METEORITE::getAbility);
    public static final AbstractAbility HEAL = ABILITIES.register("heal",Abilities.HEAL::getAbility);


}
