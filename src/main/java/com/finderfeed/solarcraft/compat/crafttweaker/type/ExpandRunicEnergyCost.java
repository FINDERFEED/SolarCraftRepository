package com.finderfeed.solarcraft.compat.crafttweaker.type;

//import com.blamejared.crafttweaker.api.annotation.ZenRegister;
//import com.blamejared.crafttweaker_annotations.annotations.Document;
//import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
//import org.openzen.zencode.java.ZenCodeType;


//@NativeTypeRegistration(value = RunicEnergyCost.class, zenCodeName = "mods.solarcraft.RunicEnergyCost")
//@ZenRegister
//@Document("mods/SolarForge/Type/RunicEnergyCost")
public class ExpandRunicEnergyCost {
//
//    /**
//     *
//     * @return An empty RunicEnergyCost with no requirements.
//     */
//    @ZenCodeType.StaticExpansionMethod
//    public static RunicEnergyCost EMPTY(){
//        return new RunicEnergyCost();
//    }
//
//    /**
//     *
//     * @return A copy of the internal RunicEnergyTypes and the necessary amount needed for each one.
//     */
//
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("types")
//    public static Map<RunicEnergy.Type, Float> getType(RunicEnergyCost internal){
//        Map<RunicEnergy.Type, Float> toReturn = new HashMap<>();
//        for (RunicEnergy.Type type : internal.getSetTypes()){
//            toReturn.put(type, internal.get(type));
//        }
//        return toReturn;
//    }
//
//    /**
//     * Sets a certain RunicEnergyType to the passed in amount.
//     *
//     * Ideally, it should be used in chain, just like a builder, to manage the final object you want.
//     *
//     * ```zenscript
//     * import mods.solarcraft.RunicEnergyCost;
//     *
//     *
//     * var customEnergyCost = RunicEnergyCost.EMPTY()
//     * .setTypeOfMap(<constant:solarcraft:energytype:ultima>, 20)
//     * .setTypeOfMap(<constant:solarcraft:energytype:urba>, 5)
//     * .setTypeOfMap(<constant:solarcraft:energytype:zeta>, 30);
//     *
//     * ```
//     *
//     * `customEnergyCost` is now usable as a variable in an {@link com.finderfeed.solarcraft.compat.crafttweaker.manager.InfusingManager}
//     *
//     * @param type The type to set
//     * @param amount The amount to set the type to
//     * @return The modified RunicEnergyCost.
//     *
//     * @docParam type <constant:solarcraft:energytype:ultima>
//     * @docParam amount 10
//     */
//    @ZenCodeType.Method()
//    public static RunicEnergyCost setTypeOfMap(RunicEnergyCost internal, RunicEnergy.Type type, int amount){
//        internal.getSetTypes().add(type);
//        internal.getCosts()[type.getIndex()] = amount;
//        return internal;
//    }


}
