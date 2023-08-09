package com.finderfeed.solarcraft.client.particles;



import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.lightning_particle.LightningParticleOptions;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SCParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,"solarcraft");
    public static final RegistryObject<SimpleParticleType> SOLAR_STRIKE_PARTICLE = PARTICLES.register("solar_strike_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> INVISIBLE_PARTICLE = PARTICLES.register("invisible",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> HEAL_PARTICLE = PARTICLES.register("heal_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SMALL_SOLAR_STRIKE_PARTICLE = PARTICLES.register("small_solar_strike_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> RUNE_PARTICLE = PARTICLES.register("rune_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SOLAR_EXPLOSION_PARTICLE = PARTICLES.register("solar_explosion_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPARK_PARTICLE = PARTICLES.register("spark_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> CRYSTAL_SPARK_PARTICLE = PARTICLES.register("crystal_spark_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<BallParticleOptions>> BALL_PARTICLE = PARTICLES.register("ball_particle",()->
            new ParticleType<>(true, BallParticleOptions.DESERIALIZER) {
                @Override
                public Codec<BallParticleOptions> codec() {
                    return BallParticleOptions.codec();
                }
            });
    public static final RegistryObject<ParticleType<LightningParticleOptions>> LIGHTNING_PARTICLE = PARTICLES.register("lightning_particle",()->
            new ParticleType<>(true, LightningParticleOptions.DESERIALIZER) {
                @Override
                public Codec<LightningParticleOptions> codec() {
                    return LightningParticleOptions.codec();
                }
            });

}
