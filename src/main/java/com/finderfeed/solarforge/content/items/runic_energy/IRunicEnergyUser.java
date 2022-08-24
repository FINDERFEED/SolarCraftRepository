package com.finderfeed.solarforge.content.items.runic_energy;

import com.finderfeed.solarforge.misc_things.RunicEnergy;

import java.util.List;

public interface IRunicEnergyUser {


    float getMaxRunicEnergyCapacity();

    List<RunicEnergy.Type> allowedInputs();

    RunicEnergyCost getCost();

}
