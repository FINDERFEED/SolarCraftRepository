package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors;

import net.minecraft.network.FriendlyByteBuf;

public abstract class ParticleEmitterProcessorData {

    public abstract void toNetwork(FriendlyByteBuf buf);

    public abstract ParticleEmitterProcessorType<?> getType();
}
