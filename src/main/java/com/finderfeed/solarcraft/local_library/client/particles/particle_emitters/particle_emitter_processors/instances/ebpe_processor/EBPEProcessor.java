package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.instances.ebpe_processor;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmitter;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.ParticleEmitterProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Entity Bound Particle Emitter Processor
 */
public class EBPEProcessor extends ParticleEmitterProcessor {

    private Entity entity;
    private int entityId;

    public EBPEProcessor(int entityId){
        this.entityId = entityId;
    }

    @Override
    public void processEmitter(ParticleEmitter emitter) {
        if (entity != null) {
            Vec3 pos = entity.position().add(0, entity.getBbHeight() / 2f, 0);
            emitter.x = pos.x;
            emitter.y = pos.y;
            emitter.z = pos.z;
            if (entity.isRemoved() || (entity instanceof LivingEntity living && living.isDeadOrDying())) {
                emitter.removed = true;
            }
        }else{
            emitter.removed = true;
        }
    }

    @Override
    public void initEmitter(ParticleEmitter emitter) {
        this.entity = Minecraft.getInstance().level.getEntity(entityId);
        emitter.lifetime = Integer.MAX_VALUE;
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
