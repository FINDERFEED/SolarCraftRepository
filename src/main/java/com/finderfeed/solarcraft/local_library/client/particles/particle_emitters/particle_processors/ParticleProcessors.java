package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.instances.random_speed.RandomSpeedProcessorData;

import java.util.HashMap;

public class ParticleProcessors {

    public static final HashMap<String,ParticleProcessorType<?>> TYPES = new HashMap<>();


    public static final ParticleProcessorType<?> RANDOM_INIT_SPEED = register(new ParticleProcessorType<>(
            "random_init_speed", RandomSpeedProcessorData.DESERIALIZER
    ));


    public static <T extends ParticleProcessor> ParticleProcessorType<T> register(ParticleProcessorType<T> type){
        TYPES.put(type.getName(),type);
        return type;
    }

}
