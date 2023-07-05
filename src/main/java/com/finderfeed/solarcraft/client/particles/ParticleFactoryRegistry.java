package com.finderfeed.solarcraft.client.particles;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ParticleFactoryRegistry {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(SolarcraftParticleTypes.SOLAR_STRIKE_PARTICLE.get(), SolarStrikeParticle.Factory::new);
        event.registerSpriteSet(SolarcraftParticleTypes.INVISIBLE_PARTICLE.get(), InvisibleParticle.Factory::new);
        event.registerSpriteSet(SolarcraftParticleTypes.HEAL_PARTICLE.get(), HealParticle.Factory::new);
        event.registerSpriteSet(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), SmallSolarStrikeParticle.Factory::new);
        event.registerSpriteSet(SolarcraftParticleTypes.RUNE_PARTICLE.get(), RuneParticle.Factory::new);
        event.registerSpriteSet(SolarcraftParticleTypes.SOLAR_EXPLOSION_PARTICLE.get(), SolarExplosionParticle.Factory::new);
        event.registerSpriteSet(SolarcraftParticleTypes.SPARK_PARTICLE.get(), SparkParticle.Factory::new);
        event.registerSpriteSet(SolarcraftParticleTypes.CRYSTAL_SPARK_PARTICLE.get(), CrystalSparkParticle.Provider::new);
//        Minecraft.getInstance().particleEngine.register(SolarcraftParticleTypes.SOLAR_STRIKE_PARTICLE.get(), SolarStrikeParticle.Factory::new);
//        Minecraft.getInstance().particleEngine.register(SolarcraftParticleTypes.INVISIBLE_PARTICLE.get(), InvisibleParticle.Factory::new);
//        Minecraft.getInstance().particleEngine.register(SolarcraftParticleTypes.HEAL_PARTICLE.get(), HealParticle.Factory::new);
//        Minecraft.getInstance().particleEngine.register(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), SmallSolarStrikeParticle.Factory::new);
//        Minecraft.getInstance().particleEngine.register(SolarcraftParticleTypes.RUNE_PARTICLE.get(), RuneParticle.Factory::new);
//        Minecraft.getInstance().particleEngine.register(SolarcraftParticleTypes.SOLAR_EXPLOSION_PARTICLE.get(), SolarExplosionParticle.Factory::new);
//        Minecraft.getInstance().particleEngine.register(SolarcraftParticleTypes.SPARK_PARTICLE.get(), SparkParticle.Factory::new);
//        Minecraft.getInstance().particleEngine.register(SolarcraftParticleTypes.CRYSTAL_SPARK_PARTICLE.get(), CrystalSparkParticle.Provider::new);

    }

}
