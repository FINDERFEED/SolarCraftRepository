package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors;

import java.util.HashMap;

public class ParticleEmitterProcessors {

    public static final HashMap<String, ParticleEmitterProcessorType<?>> TYPES = new HashMap<>();






    public static <T extends ParticleEmitterProcessor> ParticleEmitterProcessorType<T> register(ParticleEmitterProcessorType<T> type){
        TYPES.put(type.getName(),type);
        return type;
    }


}
