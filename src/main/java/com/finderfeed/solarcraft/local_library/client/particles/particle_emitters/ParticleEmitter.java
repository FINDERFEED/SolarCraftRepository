package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.ParticleEmitterProcessor;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ParticleEmitter {

    public double x;
    public double y;
    public double z;
    public int lifetime;
    public int frequency;
    public List<ParticleProcessor> particleProcessors;
    public List<ParticleEmitterProcessor> particleEmitterProcessors;
    public ParticleOptions options;
    public boolean removed;
    public int tick;
    public List<Particle> activeParticles = new LinkedList<>();

    public ParticleEmitter(LinkedList<ParticleProcessor> particleProcessors, LinkedList<ParticleEmitterProcessor> particleEmitterProcessors,
                           ParticleOptions options,double x,double y,double z,int lifetime,int frequency){
        this.removed = false;
        this.tick = 0;
        this.particleEmitterProcessors = particleEmitterProcessors;
        this.particleProcessors = particleProcessors;
        this.options =options;
        this.x = x;
        this.y = y;
        this.z = z;
        this.lifetime = lifetime;
        this.frequency = frequency;
    }

    public void tick(){
        if (!removed) {
            ParticleEngine engine = Minecraft.getInstance().particleEngine;
            if (tick % frequency == 0){
                this.addNewParticle(engine);
            }
            this.clearDeadParticles();
            this.doProcessors();
            if (tick++ > lifetime) {
                this.removed = true;
            }
        }
    }

    private void clearDeadParticles(){
        Iterator<Particle> iterator = activeParticles.iterator();
        while (iterator.hasNext()){
            Particle particle = iterator.next();
            if (!particle.isAlive()){
                iterator.remove();
            }
        }
    }
    private void doProcessors(){
        Iterator<ParticleProcessor> processorIterator = particleProcessors.iterator();
        while (processorIterator.hasNext()){
            var processor = processorIterator.next();
            if (!processor.isDone()){
                for (Particle particle : activeParticles) {
                    processor.processParticle(particle,this);
                }
            }else{
                processorIterator.remove();
            }
        }
        Iterator<ParticleEmitterProcessor> emitterProcessorIterator = particleEmitterProcessors.iterator();
        while (emitterProcessorIterator.hasNext()){
            var processor = emitterProcessorIterator.next();
            if (!processor.isDone()){
                processor.processEmitter(this);
            }else{
                emitterProcessorIterator.remove();
            }
        }
    }

    private void addNewParticle(ParticleEngine engine){
        Particle particle = engine.createParticle(options,x,y,z,0,0,0);
        if (particle != null){
            this.activeParticles.add(particle);
            for (ParticleProcessor processor : particleProcessors){
                processor.initParticle(particle,this);
            }
        }
    }



    public boolean isRemoved() {
        return removed;
    }

    public int getTick() {
        return tick;
    }

    public int getLifetime() {
        return lifetime;
    }

}
