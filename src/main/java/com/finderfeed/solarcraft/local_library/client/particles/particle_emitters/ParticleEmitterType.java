package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters;

public class ParticleEmitterType<T extends ParticleEmitter> {

    private ParticleEmitterDeserializer<T> deserializer;
    private String name;
    public ParticleEmitterType(String name,ParticleEmitterDeserializer<T> deserializer){
        this.name = name;
        this.deserializer = deserializer;
    }

    public String getName() {
        return name;
    }

    public ParticleEmitterDeserializer<T> getDeserializer() {
        return deserializer;
    }
}
