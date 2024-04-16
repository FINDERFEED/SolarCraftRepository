package com.finderfeed.solarcraft.content.items.runic_energy;

import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveSerializable;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RunicEnergyCost implements ReflectiveSerializable<RunicEnergyCost> {


    public static Codec<RunicEnergyCost> CODEC = RecordCodecBuilder.create(p->p.group(
            ExtraCodecs.strictOptionalField(Codec.FLOAT,"zeta",0f).forGetter(cost->cost.get(RunicEnergy.Type.ZETA)),
            ExtraCodecs.strictOptionalField(Codec.FLOAT,"ardo",0f).forGetter(cost->cost.get(RunicEnergy.Type.ARDO)),
            ExtraCodecs.strictOptionalField(Codec.FLOAT,"urba",0f).forGetter(cost->cost.get(RunicEnergy.Type.URBA)),
            ExtraCodecs.strictOptionalField(Codec.FLOAT,"kelda",0f).forGetter(cost->cost.get(RunicEnergy.Type.KELDA)),
            ExtraCodecs.strictOptionalField(Codec.FLOAT,"fira",0f).forGetter(cost->cost.get(RunicEnergy.Type.FIRA)),
            ExtraCodecs.strictOptionalField(Codec.FLOAT,"tera",0f).forGetter(cost->cost.get(RunicEnergy.Type.TERA)),
            ExtraCodecs.strictOptionalField(Codec.FLOAT,"giro",0f).forGetter(cost->cost.get(RunicEnergy.Type.GIRO)),
            ExtraCodecs.strictOptionalField(Codec.FLOAT,"ultima",0f).forGetter(cost->cost.get(RunicEnergy.Type.ULTIMA))
    ).apply(p, RunicEnergyCost::new));


    public static final RunicEnergyCost EMPTY = new RunicEnergyCost();
    private List<RunicEnergy.Type> setTypes = new ArrayList<>();
    private float[] costs = new float[8];
    private boolean immutable = false;

    public RunicEnergyCost(){}

    public RunicEnergyCost(float zeta,float ardo,float urba,float kelda,float fira,float tera,float giro,float ultima){
        set(RunicEnergy.Type.URBA,urba);
        set(RunicEnergy.Type.KELDA,kelda);
        set(RunicEnergy.Type.TERA,tera);
        set(RunicEnergy.Type.ZETA,zeta);
        set(RunicEnergy.Type.FIRA,fira);
        set(RunicEnergy.Type.ULTIMA,giro);
        set(RunicEnergy.Type.GIRO,ultima);
        set(RunicEnergy.Type.ARDO,ardo);
        nullifyUnusedTypes();
    }

    public RunicEnergyCost set(RunicEnergy.Type type,float amount){
        if (immutable){
            throw new IllegalStateException("Cannot set RE in immutable RunicEnergyCost!");
        }
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

    public RunicEnergyCost immutable(){
        this.immutable = true;
        return this;
    }


    @Override
    public void toJson(RunicEnergyCost cost, JsonObject object){
        for (RunicEnergy.Type type : cost.getSetTypes()){
            object.addProperty(type.id,cost.get(type));
        }
    }

    @Override
    public RunicEnergyCost fromJson(JsonObject object){
        RunicEnergyCost cost = new RunicEnergyCost();
        for (var entry : object.entrySet()){
            String name = entry.getKey();
            float val = entry.getValue().getAsFloat();
            RunicEnergy.Type type = RunicEnergy.Type.byId(name);
            cost.set(type,val);
        }
        return cost;
    }

}
