package com.finderfeed.solarcraft.content.abilities;

import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveSerializable;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.google.gson.JsonObject;

public class DefaultAbilityStats implements ReflectiveSerializable<DefaultAbilityStats> {

    private RunicEnergyCost cost;
    private int buyCost;

    public DefaultAbilityStats(){

    }

    public DefaultAbilityStats(RunicEnergyCost cost,int buyCost){
        this.cost = cost;
        this.buyCost = buyCost;
    }

    @Override
    public DefaultAbilityStats fromJson(JsonObject object) {
        RunicEnergyCost c = new RunicEnergyCost();
        c = c.fromJson(object.getAsJsonObject("castCost"));
        int buyCost = object.get("buyCost").getAsInt();
        return new DefaultAbilityStats(c,buyCost);
    }

    @Override
    public void toJson(DefaultAbilityStats value, JsonObject object) {
        JsonObject scost = new JsonObject();
        cost.toJson(cost,scost);
        object.add("castCost",scost);
        object.addProperty("buyCost",buyCost);
    }

    public int getBuyCost() {
        return buyCost;
    }

    public RunicEnergyCost getCastCost() {
        return cost;
    }
}
