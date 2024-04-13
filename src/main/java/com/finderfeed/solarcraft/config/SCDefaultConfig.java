package com.finderfeed.solarcraft.config;

import com.finderfeed.solarcraft.config.json_config.reflective.ConfigValue;
import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveJsonConfig;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;

public class SCDefaultConfig extends ReflectiveJsonConfig {

    @ConfigValue
    public float testValue1 = 1;
    @ConfigValue
    public String testValue2 = "test";
    @ConfigValue
    public RunicEnergyCost testCost = new RunicEnergyCost().set(RunicEnergy.Type.TERA,321).set(RunicEnergy.Type.FIRA,32);

    public SCDefaultConfig() {
        super("default_solarcraft_config");
    }

}
