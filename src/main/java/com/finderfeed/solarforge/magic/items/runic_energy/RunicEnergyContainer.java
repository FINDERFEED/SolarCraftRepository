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

    public void saveToTag(CompoundTag tag){
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            tag.putFloat(type.id,this.costs[type.getIndex()]);
        }
    }

    public void loadFromTag(CompoundTag tag){
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            this.set(type,tag.getFloat(type.id));
        }
    }


}
