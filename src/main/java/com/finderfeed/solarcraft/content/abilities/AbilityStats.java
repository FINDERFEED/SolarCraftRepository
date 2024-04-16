package com.finderfeed.solarcraft.content.abilities;

import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveSerializable;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class AbilityStats implements ReflectiveSerializable<AbilityStats> {

    private HashMap<String,Float> values;
    private DefaultAbilityStats defaultAbilityStats;
    public AbilityStats(DefaultAbilityStats defaultAbilityStats){
        this.defaultAbilityStats = defaultAbilityStats;
        this.values = new HashMap<>();
    }

    public AbilityStats addStat(String name,Float statValue){
        this.values.put(name,statValue);
        return this;
    }

    @Override
    public AbilityStats fromJson(JsonObject object) {
        DefaultAbilityStats defaultStats = new DefaultAbilityStats();
        defaultStats = defaultStats.fromJson(object.getAsJsonObject("defaultStats"));
        AbilityStats s = new AbilityStats(defaultStats);
        JsonObject stats = object.get("stats").getAsJsonObject();
        for (var entry : stats.entrySet()){
            s.addStat(entry.getKey(),entry.getValue().getAsFloat());
        }
        return s;
    }

    @Override
    public void toJson(AbilityStats value, JsonObject object) {
        JsonObject defStats = new JsonObject();
        value.defaultAbilityStats.toJson(value.defaultAbilityStats,defStats);
        JsonObject stats = new JsonObject();
        for (var entry : value.values.entrySet()){
            stats.addProperty(entry.getKey(),entry.getValue());
        }
        object.add("defaultStats",defStats);
        object.add("stats",stats);
    }

    public DefaultAbilityStats getDefaultAbilityStats() {
        return defaultAbilityStats;
    }

    public float getStat(String s){
        return this.values.get(s);
    }
}
