package com.finderfeed.solarcraft.content.abilities;

import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveSerializable;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

import java.util.HashMap;
import java.util.Map;

public class AbilityStats implements ReflectiveSerializable<AbilityStats> {

    public static final Codec<AbilityStats> CODEC = RecordCodecBuilder.create(p->p.group(
            DefaultAbilityStats.CODEC.fieldOf("defaultStats").forGetter(stats->stats.defaultAbilityStats),
            ExtraCodecs.strictUnboundedMap(Codec.STRING,Codec.FLOAT).fieldOf("stats").forGetter(stats->stats.values)
    ).apply(p,AbilityStats::new));
    private Map<String,Float> values;
    private DefaultAbilityStats defaultAbilityStats;
    public AbilityStats(DefaultAbilityStats defaultAbilityStats){
        this.defaultAbilityStats = defaultAbilityStats;
        this.values = new HashMap<>();
    }
    public AbilityStats(DefaultAbilityStats defaultAbilityStats, Map<String,Float> stats){
        this.defaultAbilityStats = defaultAbilityStats;
        this.values = stats;
    }

    public AbilityStats addStat(String name,Float statValue){
        this.values.put(name,statValue);
        return this;
    }

    public DefaultAbilityStats getDefaultAbilityStats() {
        return defaultAbilityStats;
    }

    public float getStat(String s){
        return this.values.get(s);
    }

    @Override
    public Codec<AbilityStats> reflectiveCodec() {
        return CODEC;
    }
}
