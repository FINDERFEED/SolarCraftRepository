package com.finderfeed.solarcraft.config.bosses;

import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveSerializable;
import com.finderfeed.solarcraft.content.abilities.AbilityStats;
import com.finderfeed.solarcraft.content.abilities.DefaultAbilityStats;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

import java.util.Map;

public class BossStats implements ReflectiveSerializable<BossStats> {


    public static final Codec<BossStats> CODEC = RecordCodecBuilder.create(p->p.group(
            ExtraCodecs.strictUnboundedMap(Codec.STRING,Codec.FLOAT).fieldOf("stats").forGetter(stats->stats.values)
    ).apply(p,BossStats::new));
    private Map<String,Float> values;

    public BossStats(Map<String,Float> values){
        this.values = values;
    }

    public BossStats(){}

    public BossStats addStat(String name,Float value){
        this.values.put(name,value);
        return this;
    }

    @Override
    public Codec<BossStats> reflectiveCodec() {
        return CODEC;
    }

    public float getValue(String name){
        if (!values.containsKey(name)){
            throw new RuntimeException("Boss stats don't have a key: " + name);
        }
        return values.get(name);
    }
}
