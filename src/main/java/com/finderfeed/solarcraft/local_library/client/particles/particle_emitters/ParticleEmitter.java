package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessor;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ParticleEmitter {

    private DParticleEmitterData data;
    private boolean removed;
    private int tick;
    private List<Particle> activeParticles = new ArrayList<>();

    public ParticleEmitter(DParticleEmitterData data){
        this.removed = false;
        this.tick = 0;
        this.data = data;
    }

    public void tick(){

    }

    public DParticleEmitterData getDefaultData() {
        return data;
    }

}
