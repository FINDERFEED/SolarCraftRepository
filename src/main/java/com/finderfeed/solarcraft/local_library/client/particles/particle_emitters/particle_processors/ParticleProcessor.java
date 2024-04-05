package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmitter;
import net.minecraft.client.particle.Particle;

public abstract class ParticleProcessor {
    public abstract void processParticle(Particle particle, ParticleEmitter emitter);
    public abstract void initParticle(Particle particle,ParticleEmitter emitter);
    public abstract boolean isDone();

    public abstract ParticleProcessorType<? extends ParticleProcessor> getType();
}
