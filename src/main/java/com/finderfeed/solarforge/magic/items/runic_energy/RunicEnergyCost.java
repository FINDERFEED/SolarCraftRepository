package com.finderfeed.solarforge.magic.items.runic_energy;

import com.finderfeed.solarforge.misc_things.RunicEnergy;

public class RunicEnergyCost {

    public static final RunicEnergyCost EMPTY = new RunicEnergyCost();

    private float[] costs = new float[8];

    public RunicEnergyCost set(RunicEnergy.Type type,float amount){
        costs[type.getIndex()] = amount;
        return this;
    }

    public float get(RunicEnergy.Type type){
        return costs[type.getIndex()];
    }

}
