package com.finderfeed.solarcraft.client.particles.server_data.shapes;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

public interface ParticleSpawnShape {

    void placeParticles(Level level, ParticleOptions options, double posX, double posY, double posZ, double xd, double yd, double zd);

    ParticleSpawnShapeType getType();


}
