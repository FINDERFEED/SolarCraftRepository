package com.finderfeed.solarforge.compat.crafttweaker.type;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Locale;

@NativeTypeRegistration(value = RunicEnergy.Type.class, zenCodeName = "mods.solarforge.RunicEnergyType")
@ZenRegister
@Document("mods/SolarForge/Type/RunicEnergyType")
@BracketEnum("solarforge:energytype")
public class ExpandRunicEnergyType {


    /**
     * @return A Usable RunicEnergyType to use in {@link com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost} to set up a requirement.
     */

    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(RunicEnergy.Type internal) {

        return "<constant:solarforge:energytype:" + internal.name().toLowerCase(Locale.ROOT) + ">";
    }

}
