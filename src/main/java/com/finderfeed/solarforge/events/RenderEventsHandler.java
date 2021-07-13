package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.shaders.Shaders;
import com.finderfeed.solarforge.shaders.Uniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class RenderEventsHandler {



    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event){
        Framebuffer buffer = Minecraft.getInstance().getMainRenderTarget();

        Shaders.TEST.getShader().process();
        Shaders.TEST.getShader().addUniform(new Uniform("distance",0.1f,Shaders.TEST.getShader().getSHADER()));
        Shaders.TEST.getShader().addUniform(new Uniform("size",0.1f,Shaders.TEST.getShader().getSHADER()));
        Shaders.TEST.getShader().addUniform(new Uniform("intensity",0.1f,Shaders.TEST.getShader().getSHADER()));

        buffer.blitToScreen(Minecraft.getInstance().getWindow().getWidth(),Minecraft.getInstance().getWindow().getHeight());
        Shaders.close();
    }


}
