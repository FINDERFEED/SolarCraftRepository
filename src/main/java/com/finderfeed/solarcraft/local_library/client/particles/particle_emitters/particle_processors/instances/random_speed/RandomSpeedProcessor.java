package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.instances.random_speed;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmitter;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessor;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessorType;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.ParticleProcessors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.level.Level;

public class RandomSpeedProcessor extends ParticleProcessor {

    private double xrand;
    private double yrand;
    private double zrand;
    public RandomSpeedProcessor(double xrand,double yrand,double zrand){
        this.xrand = xrand;
        this.yrand = yrand;
        this.zrand = zrand;
    }

    @Override
    public void processParticle(Particle particle, ParticleEmitter emitter) {

    }

    @Override
    public void initParticle(Particle particle, ParticleEmitter emitter) {
        Level level = Minecraft.getInstance().level;
        double xd = level.random.nextDouble() * 2 * xrand - xrand;
        double yd = level.random.nextDouble() * 2 * yrand - yrand;
        double zd = level.random.nextDouble() * 2 * zrand - zrand;
        particle.setParticleSpeed(xd,yd,zd);
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public ParticleProcessorType<? extends ParticleProcessor> getType() {
        return ParticleProcessors.RANDOM_INIT_SPEED;
    }
}
