package com.finderfeed.solarforge.compat.crafttweaker.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NativeTypeRegistration(value = RunicEnergyCost.class, zenCodeName = "mods.solarforge.RunicEnergyCost")
@ZenRegister
@Document("mods/SolarForge/Type/RunicEnergyCost")
public class ExpandRunicEnergyCost {

    @ZenCodeType.Method
    @ZenCodeType.Getter("types")
    public static Map<RunicEnergy.Type, Float> getType(RunicEnergyCost internal){
        Map<RunicEnergy.Type, Float> toReturn = new HashMap<>();
        for (RunicEnergy.Type type : internal.getSetTypes()){
            toReturn.put(type, (float) type.getIndex());
        }
        return toReturn;
    }


}
