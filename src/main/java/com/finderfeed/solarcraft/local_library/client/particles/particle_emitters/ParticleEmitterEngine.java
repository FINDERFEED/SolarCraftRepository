package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters;

import java.util.Iterator;
import java.util.LinkedList;

public class ParticleEmitterEngine {
    public LinkedList<ParticleEmitter> emitters;

    public ParticleEmitterEngine(){
        this.emitters = new LinkedList<>();
    }

    public void tick(){
        Iterator<ParticleEmitter> emitterIterator = emitters.iterator();
        while (emitterIterator.hasNext()){
            var emitter = emitterIterator.next();
            if (emitter.removed){
                emitterIterator.remove();
            }else{
                emitter.tick();
            }
        }
    }

    public void addEmitter(ParticleEmitter emitter){
        this.emitters.add(emitter);
    }

    public void clearEmitters(){
        emitters.clear();
    }

    public void clearEmitterParticles(){
        for (ParticleEmitter emitter : emitters){
            emitter.clearParticles();
        }
    }
}
