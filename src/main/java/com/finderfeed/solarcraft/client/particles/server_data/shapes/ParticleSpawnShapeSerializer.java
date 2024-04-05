package com.finderfeed.solarcraft.client.particles.server_data.shapes;

import net.minecraft.network.FriendlyByteBuf;

public interface ParticleSpawnShapeSerializer<T extends ParticleSpawnShape> {

    void toBytes(T shape, FriendlyByteBuf buf);
    T fromNetwork(FriendlyByteBuf buf);

}
