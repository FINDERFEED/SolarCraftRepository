package com.finderfeed.solarforge.registries.abilities;

import com.finderfeed.solarforge.SolarAbilities.Abilities;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.AbstractAbility;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.LightningAbility;
import com.finderfeed.solarforge.for_future_library.custom_registries.CustomDeferredRegistry;
import com.finderfeed.solarforge.for_future_library.custom_registries.CustomRegistryEntry;
import com.finderfeed.solarforge.registries.SolarcraftRegistries;



public class AbilitiesRegistry {

    public static final CustomDeferredRegistry<AbstractAbility> ABILITIES = CustomDeferredRegistry.create("solarforge", SolarcraftRegistries.ABILITIES);
    public static final LightningAbility LIGHTNING = (LightningAbility) ABILITIES.register("lightning", Abilities.LIGHTNING::getAbility);




}
