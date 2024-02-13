package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors;

public abstract class ParticleProcessorData {

    public abstract void toNetwork();

    public abstract ParticleProcessorType<?> getType();

}
