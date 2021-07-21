package com.finderfeed.solarforge.misc_things;



import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticlesList {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,"solarforge");
    public static final RegistryObject<BasicParticleType> SOLAR_STRIKE_PARTICLE = PARTICLES.register("solar_strike_particle",()-> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> INVISIBLE_PARTICLE = PARTICLES.register("invisible",()-> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> HEAL_PARTICLE = PARTICLES.register("heal_particle",()-> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SMALL_SOLAR_STRIKE_PARTICLE = PARTICLES.register("small_solar_strike_particle",()-> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> RUNE_PARTICLE = PARTICLES.register("rune_particle",()-> new BasicParticleType(true));
}
