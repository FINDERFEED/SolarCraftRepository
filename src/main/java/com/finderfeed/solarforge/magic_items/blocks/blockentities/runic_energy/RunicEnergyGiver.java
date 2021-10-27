package com.finderfeed.solarforge.magic_items.blocks.blockentities.runic_energy;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;

public interface RunicEnergyGiver {

    double extractEnergy(RunicEnergy.Type type, double maxAmount);
    RunicEnergy.Type[] getTypes();
    BlockPos getPos();
}
