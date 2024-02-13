package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessorDeserializer;

public class ParticleEmitterProcessorType<T extends ParticleEmitterProcessor> {

    private ParticleEmitterProcessorDeserializer<T> deserializer;
    private String name;

    public ParticleEmitterProcessorType(String name,ParticleEmitterProcessorDeserializer<T> deserializer){
        this.name = name;
        this.deserializer = deserializer;
    }

    public ParticleEmitterProcessorDeserializer<T> getDeserializer() {
        return deserializer;
    }

    public String getName() {
        return name;
    }
}
