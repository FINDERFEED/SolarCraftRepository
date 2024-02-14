package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors;

import net.minecraft.network.FriendlyByteBuf;

public abstract class ParticleProcessorData<T extends ParticleProcessor> {

    public abstract void toNetwork(FriendlyByteBuf buf);

    public abstract ParticleProcessorType<?> getType();

    public abstract T createInstance();

}
