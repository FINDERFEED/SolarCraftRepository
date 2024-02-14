package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors;

public class ParticleProcessorType<T extends ParticleProcessor> {

    private ParticleProcessorDeserializer<?> deserializer;
    private String name;

    public ParticleProcessorType(String name,ParticleProcessorDeserializer<?> deserializer){
        this.name = name;
        this.deserializer = deserializer;
    }
    public ParticleProcessorDeserializer<?> getDataDeserializer() {
        return deserializer;
    }

    public String getName() {
        return name;
    }
}
