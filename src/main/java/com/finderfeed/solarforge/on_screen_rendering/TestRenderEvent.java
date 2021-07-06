package com.finderfeed.solarforge.on_screen_rendering;

import com.finderfeed.solarforge.shaders.Shaders;
import com.finderfeed.solarforge.shaders.SolarShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TestRenderEvent {


    public int tick = 0;

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Pre event){


    }


    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event){

    }
}
