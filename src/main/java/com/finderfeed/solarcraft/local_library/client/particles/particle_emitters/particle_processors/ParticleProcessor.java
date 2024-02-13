package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors;

import net.minecraft.client.particle.Particle;
import net.minecraft.network.FriendlyByteBuf;

public abstract class ParticleProcessor {
    public abstract void processParticle(Particle particle, double emitterX, double emitterY, double emitterZ);
    public abstract void initProcessor(Particle particle,double emitterX,double emitterY,double emitterZ);
    public abstract boolean isDone();

    public abstract ParticleProcessorType<? extends ParticleProcessor> getType();
}
