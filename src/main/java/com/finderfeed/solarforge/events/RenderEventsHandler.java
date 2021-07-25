package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.RenderingTools;
import com.finderfeed.solarforge.rendering.shaders.Shaders;
import com.finderfeed.solarforge.rendering.shaders.Uniform;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlConst;

import net.minecraftforge.client.event.RenderWorldLastEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;



public class RenderEventsHandler {





    public static float intensity = 0;
    public static float radius = 0;



    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event){

        if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0) ) {

            if ((intensity > 0)  ) {
                RenderingTools.renderHandManually(event.getMatrixStack(),event.getPartialTicks());
                RenderTarget buffer = Minecraft.getInstance().getMainRenderTarget();
                Framebuffers.buffer2.resize(Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight(), false);

                GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, Framebuffers.buffer2.frameBufferId);
                int shader = Shaders.WAVE.getShader().getSHADER();
                int time =(int)Minecraft.getInstance().level.getGameTime();
                Shaders.WAVE.getShader().addUniform(new Uniform("intensity", intensity, shader));
                Shaders.WAVE.getShader().addUniform(new Uniform("timeModifier", 3f, shader));
                Shaders.WAVE.getShader().addUniform(new Uniform("time", time, shader));
                Shaders.WAVE.getShader().setMatrices();


                Shaders.WAVE.getShader().process();

                buffer.blitToScreen(Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight());
                Shaders.close();
                GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, buffer.frameBufferId);

                Framebuffers.buffer2.blitToScreen(Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight());
                GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, 0);
                intensity-=0.02;
                radius+=0.01;



            }
        }
    }

    public static void triggerProgressionShader(){
            intensity = 3;
            radius = 0;
    }





}

class Framebuffers{
    public static final RenderTarget buffer2 =
            new Framebuffer(true);
}

class Framebuffer extends RenderTarget{

    public Framebuffer(boolean p_166199_) {
        super(p_166199_);
        this.resize(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight(),false);
    }
}
