package com.finderfeed.solarcraft.local_library.client.particles;


import com.finderfeed.solarcraft.SolarCraft;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import java.util.*;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ScreenParticlesRenderHandler {

    private static final Map<ParticleRenderType, List<ScreenParticle>> PARTICLES = new HashMap<>();
    private static final Tesselator TESSELATOR = new Tesselator();

    @SubscribeEvent
    public static void tickParticles(TickEvent.ClientTickEvent event){
        if (event.phase == TickEvent.Phase.START && !Minecraft.getInstance().isPaused()){
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

        if (Minecraft.getInstance().level == null || event.phase == TickEvent.Phase.START) return;
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

    public static void clearAllParticles(){
        for (List<ScreenParticle> particles : PARTICLES.values()){
            Iterator<ScreenParticle> p = particles.iterator();
            while (p.hasNext()){
                p.next();
                p.remove();
            }
        }
    }

}
