package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters;

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
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ParticleEmitterData {

    public double x = 0;
    public double y = 0;
    public double z = 0;
    public int lifetime = 60;
    public int frequency = 1;
    public List<ParticleProcessorData<?>> particleProcessors = new ArrayList<>();
    public List<ParticleEmitterProcessorData<?>> particleEmitterProcessors = new ArrayList<>();
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

    public void fromNetwork(FriendlyByteBuf buf){
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.lifetime = buf.readInt();
        this.frequency = buf.readInt();
        ParticleType pType = buf.readById(BuiltInRegistries.PARTICLE_TYPE);
        ParticleOptions particle = pType.getDeserializer().fromNetwork(pType,buf);
        this.particle = particle;
        List<ParticleProcessorData<?>> particleProcessors = readParticleProcessors(buf);
        List<ParticleEmitterProcessorData<?>> particleEmitterProcessors = readParticleEmitterProcessors(buf);
        this.particleProcessors = particleProcessors;
        this.particleEmitterProcessors = particleEmitterProcessors;
    }


    public ParticleEmitter toParticleEmitter(){
        LinkedList<ParticleProcessor> processors = new LinkedList<>(
                this.particleProcessors.stream().map(ParticleProcessorData::createInstance).collect(Collectors.toList())
        );
        LinkedList<ParticleEmitterProcessor> particleEmitterProcessors = new LinkedList<>(
                this.particleEmitterProcessors.stream().map(ParticleEmitterProcessorData::createInstance).collect(Collectors.toList())
        );

        return new ParticleEmitter(processors,particleEmitterProcessors,
                particle,
                x,y,z,lifetime,frequency);
    }



    private static ArrayList<ParticleProcessorData<?>> readParticleProcessors(FriendlyByteBuf buf){
        int particleProcessorSize = buf.readInt();
        ArrayList<ParticleProcessorData<?>> particleProcessors = new ArrayList<>();
        for (int i = 0; i < particleProcessorSize;i++){
            String name = buf.readUtf();
            ParticleProcessorDeserializer<?> deserializer = ParticleProcessors.TYPES.get(name).getDataDeserializer();
            ParticleProcessorData<?> processor = deserializer.fromNetwork(buf);
            particleProcessors.add(processor);
        }
        return particleProcessors;
    }

    private static ArrayList<ParticleEmitterProcessorData<?>> readParticleEmitterProcessors(FriendlyByteBuf buf){
        int particleProcessorSize = buf.readInt();
        ArrayList<ParticleEmitterProcessorData<?>> particleProcessors = new ArrayList<>();
        for (int i = 0; i < particleProcessorSize;i++){
            String name = buf.readUtf();
            ParticleEmitterProcessorDeserializer<?> deserializer = ParticleEmitterProcessors.TYPES.get(name).getDataDeserializer();
            ParticleEmitterProcessorData<?> processor = deserializer.fromNetwork(buf);
            particleProcessors.add(processor);
        }
        return particleProcessors;
    }

    public ParticleEmitterData setPos(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public ParticleEmitterData setPos(Vec3 pos){
        return this.setPos(pos.x,pos.y,pos.z);
    }

    public ParticleEmitterData setFrequency(int frequency){
        this.frequency = frequency;
        return this;
    }

    public ParticleEmitterData setLifetime(int lifetime){
        this.lifetime = lifetime;
        return this;
    }

    public ParticleEmitterData setParticle(ParticleOptions options){
        this.particle = options;
        return this;
    }

    public ParticleEmitterData addParticleProcessor(ParticleProcessorData<?> data){
        this.particleProcessors.add(data);
        return this;
    }
    public ParticleEmitterData addParticleEmitterProcessor(ParticleEmitterProcessorData<?> data){
        this.particleEmitterProcessors.add(data);
        return this;
    }
}
