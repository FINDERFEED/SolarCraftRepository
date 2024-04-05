package com.finderfeed.solarcraft.client.particles;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;

public abstract class FDParticleOptions<T extends FDParticleOptions<?>> {

    public abstract void toNetwork(FriendlyByteBuf buf);

    public abstract Codec<T> codec();

}
