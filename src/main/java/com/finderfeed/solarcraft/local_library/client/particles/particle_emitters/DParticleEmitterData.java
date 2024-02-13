package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters;

import com.finderfeed.solarcraft.local_library.NetworkSerializable;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessor;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

public class DParticleEmitterData implements NetworkSerializable {

    public double x;
    public double y;
    public double z;
    public int lifetime;
    public int frequency;
    public List<ParticleProcessor> particleProcessors;
    public ParticleOptions particle;


    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeInt(lifetime);
        buf.writeInt(frequency);
        buf.writeId(BuiltInRegistries.PARTICLE_TYPE,particle.getType());
        particle.writeToNetwork(buf);

    }

    @Override
    public void fromNetwork(FriendlyByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.lifetime = buf.readInt();
        this.frequency = buf.readInt();
        ParticleType pType = buf.readById(BuiltInRegistries.PARTICLE_TYPE);
        this.particle = pType.getDeserializer().fromNetwork(pType,buf);

    }
}
