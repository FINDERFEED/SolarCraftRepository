package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters;

import com.finderfeed.solarcraft.local_library.NetworkSerializable;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.ParticleEmitterProcessor;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.ParticleEmitterProcessorData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.ParticleEmitterProcessorDeserializer;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.ParticleEmitterProcessors;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessor;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessorData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessorDeserializer;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessors;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParticleEmitterData {

    public double x;
    public double y;
    public double z;
    public int lifetime;
    public int frequency;
    public List<ParticleProcessorData> particleProcessors;
    public List<ParticleEmitterProcessorData> particleEmitterProcessors;
    public ParticleOptions particle;



    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeInt(lifetime);
        buf.writeInt(frequency);
        buf.writeId(BuiltInRegistries.PARTICLE_TYPE,particle.getType());
        particle.writeToNetwork(buf);
        buf.writeInt(particleProcessors.size());
        for (ParticleProcessorData processor : particleProcessors){
            buf.writeUtf(processor.getType().getName());
            processor.toNetwork(buf);
        }
        buf.writeInt(particleEmitterProcessors.size());
        for (ParticleEmitterProcessorData processor : particleEmitterProcessors){
            buf.writeUtf(processor.getType().getName());
            processor.toNetwork(buf);
        }
    }

    public static ParticleEmitter createEmitterFromPacket(FriendlyByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        int lifetime = buf.readInt();
        int frequency = buf.readInt();
        ParticleType pType = buf.readById(BuiltInRegistries.PARTICLE_TYPE);
        ParticleOptions particle = pType.getDeserializer().fromNetwork(pType,buf);
        LinkedList<ParticleProcessor> particleProcessors = readParticleProcessors(buf);
        LinkedList<ParticleEmitterProcessor> particleEmitterProcessors = readParticleEmitterProcessors(buf);
        return new ParticleEmitter(particleProcessors, particleEmitterProcessors,particle,x,y,z,lifetime,frequency);
    }

    private static LinkedList<ParticleProcessor> readParticleProcessors(FriendlyByteBuf buf){
        int particleProcessorSize = buf.readInt();
        LinkedList<ParticleProcessor> particleProcessors = new LinkedList<>();
        for (int i = 0; i < particleProcessorSize;i++){
            String name = buf.readUtf();
            ParticleProcessorDeserializer<?> deserializer = ParticleProcessors.TYPES.get(name).getDeserializer();
            ParticleProcessor processor = deserializer.fromNetwork(buf);
            particleProcessors.add(processor);
        }
        return particleProcessors;
    }

    private static LinkedList<ParticleEmitterProcessor> readParticleEmitterProcessors(FriendlyByteBuf buf){
        int particleProcessorSize = buf.readInt();
        LinkedList<ParticleEmitterProcessor> particleProcessors = new LinkedList<>();
        for (int i = 0; i < particleProcessorSize;i++){
            String name = buf.readUtf();
            ParticleEmitterProcessorDeserializer<?> deserializer = ParticleEmitterProcessors.TYPES.get(name).getDeserializer();
            ParticleEmitterProcessor processor = deserializer.fromNetwork(buf);
            particleProcessors.add(processor);
        }
        return particleProcessors;
    }
}
