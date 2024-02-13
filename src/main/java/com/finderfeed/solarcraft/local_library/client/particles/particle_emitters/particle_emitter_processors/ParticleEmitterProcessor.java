package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmitter;

public abstract class ParticleEmitterProcessor {

    public abstract void processEmitter(ParticleEmitter emitter);
    public abstract void initEmitter(ParticleEmitter emitter);

    public abstract boolean isDone();

}
