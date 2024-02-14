package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.instances.random_speed;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessorData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessorDeserializer;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessorType;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessors;
import net.minecraft.network.FriendlyByteBuf;

public class RandomSpeedProcessorData extends ParticleProcessorData<RandomSpeedProcessor> {

    public static final ParticleProcessorDeserializer<RandomSpeedProcessorData> DESERIALIZER = new ParticleProcessorDeserializer<RandomSpeedProcessorData>() {
        @Override
        public RandomSpeedProcessorData fromNetwork(FriendlyByteBuf buf) {
            return new RandomSpeedProcessorData(
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble()
            );
        }
    };

    private double x;
    private double y;
    private double z;

    public RandomSpeedProcessorData(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    @Override
    public ParticleProcessorType<?> getType() {
        return ParticleProcessors.RANDOM_INIT_SPEED;
    }

    @Override
    public RandomSpeedProcessor createInstance() {
        return new RandomSpeedProcessor(x,y,z);
    }
}
