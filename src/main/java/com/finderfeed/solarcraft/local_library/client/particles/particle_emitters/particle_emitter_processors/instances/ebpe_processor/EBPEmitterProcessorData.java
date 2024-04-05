package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.instances.ebpe_processor;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.ParticleEmitterProcessorData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.ParticleEmitterProcessorDeserializer;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.ParticleEmitterProcessorType;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.ParticleEmitterProcessors;
import net.minecraft.network.FriendlyByteBuf;

public class EBPEmitterProcessorData extends ParticleEmitterProcessorData<EBPEProcessor> {


    public static final ParticleEmitterProcessorDeserializer<EBPEmitterProcessorData> DESERIALIZER = new ParticleEmitterProcessorDeserializer<EBPEmitterProcessorData>() {
        @Override
        public EBPEmitterProcessorData fromNetwork(FriendlyByteBuf buf) {
            return new EBPEmitterProcessorData(buf.readInt());
        }
    };

    private int entityId;
    public EBPEmitterProcessorData(int entityId){
        this.entityId = entityId;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
    }

    @Override
    public ParticleEmitterProcessorType<?> getType() {
        return ParticleEmitterProcessors.ENTITY_BOUND;
    }

    @Override
    public EBPEProcessor createInstance() {
        return new EBPEProcessor(entityId);
    }
}
