package com.finderfeed.solarcraft.content.abilities;

import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveSerializable;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class DefaultAbilityStats implements ReflectiveSerializable<DefaultAbilityStats> {


    public static final Codec<DefaultAbilityStats> CODEC = RecordCodecBuilder.create(p->
            p.group(
                    RunicEnergyCost.CODEC.fieldOf("castCost").forGetter(stats->stats.cost),
                    Codec.INT.fieldOf("buyCost").forGetter(stats->stats.buyCost)
            ).apply(p,DefaultAbilityStats::new)
    );

    private RunicEnergyCost cost;
    private int buyCost;

    public DefaultAbilityStats(RunicEnergyCost cost,int buyCost){
        this.cost = cost;
        this.buyCost = buyCost;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public RunicEnergyCost getCastCost() {
        return cost;
    }

    @Override
    public Codec<DefaultAbilityStats> reflectiveCodec() {
        return CODEC;
    }
}
