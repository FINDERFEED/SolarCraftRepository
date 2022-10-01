package com.finderfeed.solarforge.content.blocks.solar_energy;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

public interface SolarEnergyContainer {

    int getSolarEnergy();

    void setSolarEnergy(int energy);

    int getMaxSolarEnergy();

    BlockPos getPos();

    double getSolarEnergyCollectionRadius();

    boolean canBeBinded();

    int maxEnergyInput();


    /**
     * returns a delta if more energy than allowed was put in.
     */
    default int addSolarEnergy(int energyAmount){
        int energy = this.getSolarEnergy() + energyAmount;
        this.setSolarEnergy(Mth.clamp(energy,0,getMaxSolarEnergy()));
        return Mth.clamp(energy - getMaxSolarEnergy(),0,Integer.MAX_VALUE);
    }

    /**
     * returns how much energy was actually taken
     */
    default int takeSolarEnergy(int energyToTake){
        int old = this.getSolarEnergy();
        int energy = this.getSolarEnergy() - energyToTake;
        this.setSolarEnergy(Mth.clamp(energy,0,getMaxSolarEnergy()));
        return Math.min(old, energyToTake);
    }

}
