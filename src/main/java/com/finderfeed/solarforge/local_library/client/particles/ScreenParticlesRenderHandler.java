package com.finderfeed.solarforge.local_library.client.particles;


import com.finderfeed.solarforge.SolarForge;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = SolarForge.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ScreenParticlesRenderHandler {

    private static final Map<ParticleRenderType, List<ScreenParticle>> PARTICLES = new HashMap<>();
    private static final Tesselator TESSELATOR = new Tesselator();

    @SubscribeEvent
    public static void tickParticles(TickEvent.ClientTickEvent event){
        if (event.phase == TickEvent.Phase.START){
            Collection<List<ScreenParticle>> particles = PARTICLES.values();
            for (List<ScreenParticle> p : particles){
                Iterator<ScreenParticle> iterator = p.iterator();
                while (iterator.hasNext()){
                    ScreenParticle particle = iterator.next();
                    particle.tick();
                    if (particle.isRemoved()) iterator.remove();
                }
            }
        }
    }


    @SubscribeEvent
    public static void renderParticles(TickEvent.RenderTickEvent event){
        if (event.phase == TickEvent.Phase.END) {
            BufferBuilder builder = TESSELATOR.getBuilder();
            for (Map.Entry<ParticleRenderType,List<ScreenParticle>> entry : PARTICLES.entrySet()){
                ParticleRenderType type = entry.getKey();
                type.begin(builder, Minecraft.getInstance().getTextureManager());
                for (ScreenParticle particle : entry.getValue()){
                    particle.render(builder,Minecraft.getInstance().getFrameTime());
                }
                type.end(TESSELATOR);
            }
        }
    }

    public static void addParticlesOfSameRendertype(ScreenParticle... particles){
        if (particles.length == 0) return;
        List<ScreenParticle> ps = PARTICLES.get(particles[0].getRenderType());
        ps.addAll(Arrays.asList(particles));
    }

    public static void addParticle(ScreenParticle particle){
        PARTICLES.get(particle.getRenderType()).add(particle);
    }


    public static void registerRenderType(ParticleRenderType type){
        if (PARTICLES.containsKey(type)) throw new IllegalStateException("Particle render type " + type.toString() + " already registered");
        PARTICLES.put(type,new ArrayList<>());
    }

}
