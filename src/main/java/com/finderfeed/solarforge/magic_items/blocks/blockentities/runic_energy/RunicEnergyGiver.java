package com.finderfeed.solarforge.magic_items.blocks.blockentities.runic_energy;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;

import java.util.List;

public interface RunicEnergyGiver {

    double extractEnergy(RunicEnergy.Type type, double maxAmount);
    List<RunicEnergy.Type> getTypes();
    BlockPos getPos();
    double getRunicEnergy(RunicEnergy.Type type);
}
