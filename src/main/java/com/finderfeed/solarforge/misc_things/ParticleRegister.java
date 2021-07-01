package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.particles.HealParticle;
import com.finderfeed.solarforge.particles.InvisibleParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegister {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particleEngine.register(ParticlesList.SOLAR_STRIKE_PARTICLE.get(),SolarStrikeParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticlesList.INVISIBLE_PARTICLE.get(), InvisibleParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticlesList.HEAL_PARTICLE.get(), HealParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(), SmallSolarStrikeParticle.Factory::new);
    }

}
