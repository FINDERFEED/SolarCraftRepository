package com.finderfeed.solarforge.magic.items.runic_energy;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.nbt.CompoundTag;

public class RunicEnergyContainer {
    private float[] costs = new float[8];

    public RunicEnergyContainer set(RunicEnergy.Type type, float amount){
        costs[type.getIndex()] = amount;
        return this;
    }

    public float get(RunicEnergy.Type type){
        return costs[type.getIndex()];
    }

    public static void saveToTag(RunicEnergyContainer c,CompoundTag tag){
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            tag.putFloat(type.id,c.costs[type.getIndex()]);
        }
    }

    public static RunicEnergyContainer loadFromTag(CompoundTag tag){
        RunicEnergyContainer container = new RunicEnergyContainer();
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            container.set(type,tag.getFloat(type.id));
        }
        return container;
    }
}
