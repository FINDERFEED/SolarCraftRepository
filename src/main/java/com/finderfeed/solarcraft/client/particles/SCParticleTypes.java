package com.finderfeed.solarcraft.client.particles;



import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.lightning_particle.LightningParticleOptions;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SCParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE,"solarcraft");
    public static final DeferredHolder<ParticleType<?>,SimpleParticleType> SOLAR_STRIKE_PARTICLE = PARTICLES.register("solar_strike_particle",()-> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>,SimpleParticleType> INVISIBLE_PARTICLE = PARTICLES.register("invisible",()-> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>,SimpleParticleType> HEAL_PARTICLE = PARTICLES.register("heal_particle",()-> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>,SimpleParticleType> SMALL_SOLAR_STRIKE_PARTICLE = PARTICLES.register("small_solar_strike_particle",()-> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>,SimpleParticleType> RUNE_PARTICLE = PARTICLES.register("rune_particle",()-> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>,SimpleParticleType> SOLAR_EXPLOSION_PARTICLE = PARTICLES.register("solar_explosion_particle",()-> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>,SimpleParticleType> SPARK_PARTICLE = PARTICLES.register("spark_particle",()-> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>,SimpleParticleType> CRYSTAL_SPARK_PARTICLE = PARTICLES.register("crystal_spark_particle",()-> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>,ParticleType<BallParticleOptions>> BALL_PARTICLE = PARTICLES.register("ball_particle",()->
            new ParticleType<>(true, BallParticleOptions.DESERIALIZER) {
                @Override
                public Codec<BallParticleOptions> codec() {
                    return BallParticleOptions.codec();
                }
            });
    public static final DeferredHolder<ParticleType<?>,ParticleType<LightningParticleOptions>> LIGHTNING_PARTICLE = PARTICLES.register("lightning_particle",()->
            new ParticleType<>(true, LightningParticleOptions.DESERIALIZER) {
                @Override
                public Codec<LightningParticleOptions> codec() {
                    return LightningParticleOptions.codec();
                }
            });

}
