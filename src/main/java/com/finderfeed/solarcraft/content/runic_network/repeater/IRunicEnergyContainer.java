package com.finderfeed.solarcraft.content.runic_network.repeater;

import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;

//NEVER GONNA LET YOU DOWN
public interface IRunicEnergyContainer {

    double extractEnergy(RunicEnergy.Type type, double maxAmount);
    double addEnergy(RunicEnergy.Type type, double amount);
    double getRunicEnergyEnergy(RunicEnergy.Type amount);
    BlockPos getPos();
}
