package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters;

import net.minecraft.network.FriendlyByteBuf;

public abstract class ParticleEmitterDeserializer<T extends ParticleEmitter> {

    public abstract T fromNetwork(DParticleEmitterData data,FriendlyByteBuf buf);

}
