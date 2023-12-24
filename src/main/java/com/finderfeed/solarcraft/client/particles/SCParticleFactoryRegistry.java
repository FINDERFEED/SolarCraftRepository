package com.finderfeed.solarcraft.client.particles;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticle;
import com.finderfeed.solarcraft.client.particles.lightning_particle.LightningParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class SCParticleFactoryRegistry {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(SCParticleTypes.SOLAR_STRIKE_PARTICLE.get(), SolarStrikeParticle.Factory::new);
        event.registerSpriteSet(SCParticleTypes.INVISIBLE_PARTICLE.get(), InvisibleParticle.Factory::new);
        event.registerSpriteSet(SCParticleTypes.HEAL_PARTICLE.get(), HealParticle.Factory::new);
        event.registerSpriteSet(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), SmallSolarStrikeParticle.Factory::new);
        event.registerSpriteSet(SCParticleTypes.RUNE_PARTICLE.get(), RuneParticle.Factory::new);
        event.registerSpriteSet(SCParticleTypes.SOLAR_EXPLOSION_PARTICLE.get(), SolarExplosionParticle.Factory::new);
        event.registerSpriteSet(SCParticleTypes.SPARK_PARTICLE.get(), SparkParticle.Factory::new);
        event.registerSpriteSet(SCParticleTypes.CRYSTAL_SPARK_PARTICLE.get(), CrystalSparkParticle.Provider::new);
        event.registerSpriteSet(SCParticleTypes.BALL_PARTICLE.get(), BallParticle.Provider::new);
        event.registerSpecial(SCParticleTypes.LIGHTNING_PARTICLE.get(), new LightningParticle.Provider());
    }

}
