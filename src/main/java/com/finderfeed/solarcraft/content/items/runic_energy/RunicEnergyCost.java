package com.finderfeed.solarcraft.content.items.runic_energy;

import com.finderfeed.solarcraft.misc_things.RunicEnergy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RunicEnergyCost {

    public static final RunicEnergyCost EMPTY = new RunicEnergyCost();
    private List<RunicEnergy.Type> setTypes = new ArrayList<>();
    private float[] costs = new float[8];

    public RunicEnergyCost set(RunicEnergy.Type type,float amount){
        setTypes.add(type);
        costs[type.getIndex()] = amount;
        return this;
    }

    public float get(RunicEnergy.Type type){
        return costs[type.getIndex()];
    }

    public List<RunicEnergy.Type> getSetTypes() {
        return setTypes;
    }

    public float[] getCosts() {
        return costs;
    }

    public void nullifyUnusedTypes(){
        setTypes.removeIf(type -> get(type) == 0);
    }
}
