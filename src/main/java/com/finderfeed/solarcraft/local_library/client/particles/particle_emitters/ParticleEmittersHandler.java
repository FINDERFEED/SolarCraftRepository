package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.event.TickEvent;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ParticleEmittersHandler {


    public static final ParticleEmitterEngine EMITTER_ENGINE = new ParticleEmitterEngine();

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event){
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.START && mc.level != null && !mc.isPaused()){
            EMITTER_ENGINE.tick();
        }
    }


    @SubscribeEvent
    public static void onLogoff(ClientPlayerNetworkEvent.LoggingOut event){
        EMITTER_ENGINE.clearEmitters();
    }



}
