package com.finderfeed.solarforge.magic_items.runic_network.repeater;

import com.finderfeed.solarforge.misc_things.RunicEnergy;

//NEVER GONNA LET YOU DOWN
public interface IRunicEnergyContainer {

    double extractEnergy(RunicEnergy.Type type, double maxAmount);
    double addEnergy(RunicEnergy.Type type, double amount);
    double getRunicEnergyEnergy(RunicEnergy.Type amount);

}
