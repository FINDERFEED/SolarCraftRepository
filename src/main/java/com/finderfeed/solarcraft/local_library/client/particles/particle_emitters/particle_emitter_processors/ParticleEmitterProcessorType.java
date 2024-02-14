package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors;

public class ParticleEmitterProcessorType<T extends ParticleEmitterProcessor> {

    private ParticleEmitterProcessorDeserializer<?> deserializer;
    private String name;

    public ParticleEmitterProcessorType(String name,ParticleEmitterProcessorDeserializer<?> deserializer){
        this.name = name;
        this.deserializer = deserializer;
    }

    public ParticleEmitterProcessorDeserializer<?> getDataDeserializer() {
        return deserializer;
    }

    public String getName() {
        return name;
    }
}
