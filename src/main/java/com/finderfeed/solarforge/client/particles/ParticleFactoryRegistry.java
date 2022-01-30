package com.finderfeed.solarforge.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleFactoryRegistry {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event){



        Minecraft.getInstance().particleEngine.register(ParticleTypesRegistry.SOLAR_STRIKE_PARTICLE.get(), SolarStrikeParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypesRegistry.INVISIBLE_PARTICLE.get(), InvisibleParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypesRegistry.HEAL_PARTICLE.get(), HealParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(), SmallSolarStrikeParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypesRegistry.RUNE_PARTICLE.get(), RuneParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypesRegistry.SOLAR_EXPLOSION_PARTICLE.get(), SolarExplosionParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypesRegistry.SPARK_PARTICLE.get(), SparkParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleTypesRegistry.CRYSTAL_SPARK_PARTICLE.get(), CrystalSparkParticle.Provider::new);

    }

}
