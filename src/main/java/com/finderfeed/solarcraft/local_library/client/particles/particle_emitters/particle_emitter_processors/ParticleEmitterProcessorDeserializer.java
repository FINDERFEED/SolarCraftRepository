package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors;

import net.minecraft.network.FriendlyByteBuf;

public abstract class ParticleEmitterProcessorDeserializer<T extends ParticleEmitterProcessor> {

    public abstract T fromNetwork(FriendlyByteBuf buf);

}
