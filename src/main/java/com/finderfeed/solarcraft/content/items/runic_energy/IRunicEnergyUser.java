package com.finderfeed.solarcraft.content.items.runic_energy;

import com.finderfeed.solarcraft.misc_things.RunicEnergy;

import java.util.List;

public interface IRunicEnergyUser {


    float getMaxRunicEnergyCapacity();

    List<RunicEnergy.Type> allowedInputs();

    RunicEnergyCost getCost();

}
