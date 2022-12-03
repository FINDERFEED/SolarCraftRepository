package com.finderfeed.solarcraft.events;


import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;

import com.finderfeed.solarcraft.misc_things.PostShader;
import net.minecraft.client.Minecraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;


import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;


public class RenderEventsHandler {

    //progression shader
    public static float intensity = 0;
    public static final ResourceLocation PROGRESSION_SHADER_LOC = new ResourceLocation("solarcraft","shaders/post/wave.json");
    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent event){
        if ((intensity > 0) && (event.phase == TickEvent.Phase.START) ){
            intensity-=0.03;
        }
    }


    public static void triggerProgressionShader(){
        intensity = 3;
    }
    public static PostChainPlusUltra PROGRESSION_SHADER = null;
    public static PostChainPlusUltra loadProgressionShader(ResourceLocation name, UniformPlusPlus uniformPlusPlus) {
        Minecraft mc = Minecraft.getInstance();
        try
        {
            PostChainPlusUltra shader = new PostChainPlusUltra(name,uniformPlusPlus);
            shader.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
            return shader;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }




    //all tile entity shaders are happening here
    public static Map<String, PostShader> ACTIVE_SHADERS = new HashMap<>();
    public Vec2 resolution;


}