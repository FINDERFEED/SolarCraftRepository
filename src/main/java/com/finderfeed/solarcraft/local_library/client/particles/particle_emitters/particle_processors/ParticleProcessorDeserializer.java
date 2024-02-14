package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors;

import net.minecraft.network.FriendlyByteBuf;

public abstract class ParticleProcessorDeserializer<T extends ParticleProcessorData<?>> {

    public abstract T fromNetwork(FriendlyByteBuf buf);

}
