package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.instances.ebpe_processor.EBPEmitterProcessorData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.instances.ebpe_processor.EBPEProcessor;

import java.util.HashMap;

public class ParticleEmitterProcessors {

    public static final HashMap<String, ParticleEmitterProcessorType<?>> TYPES = new HashMap<>();


    public static final ParticleEmitterProcessorType<EBPEProcessor> ENTITY_BOUND = register(
            new ParticleEmitterProcessorType<>("entity_bound", EBPEmitterProcessorData.DESERIALIZER)
    );




    public static <T extends ParticleEmitterProcessor> ParticleEmitterProcessorType<T> register(ParticleEmitterProcessorType<T> type){
        TYPES.put(type.getName(),type);
        return type;
    }


}
