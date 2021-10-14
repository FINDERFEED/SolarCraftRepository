package com.finderfeed.solarforge.misc_things;



import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticlesList {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,"solarforge");
    public static final RegistryObject<SimpleParticleType> SOLAR_STRIKE_PARTICLE = PARTICLES.register("solar_strike_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> INVISIBLE_PARTICLE = PARTICLES.register("invisible",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> HEAL_PARTICLE = PARTICLES.register("heal_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SMALL_SOLAR_STRIKE_PARTICLE = PARTICLES.register("small_solar_strike_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> RUNE_PARTICLE = PARTICLES.register("rune_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SOLAR_EXPLOSION_PARTICLE = PARTICLES.register("solar_explosion_particle",()-> new SimpleParticleType(true));
}
