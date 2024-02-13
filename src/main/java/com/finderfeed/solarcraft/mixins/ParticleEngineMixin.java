package com.finderfeed.solarcraft.mixins;

import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmittersHandler;
import net.minecraft.client.particle.ParticleEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {

    @Inject(method="clearParticles",at = @At("HEAD"))
    public void clearParticles(CallbackInfo ci){
        ParticleEmittersHandler.EMITTER_ENGINE.clearEmitterParticles();
    }

}
