package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.RenderingTools;
import com.finderfeed.solarforge.rendering.shaders.Shaders;
import com.finderfeed.solarforge.rendering.shaders.Uniform;

import com.finderfeed.solarforge.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarforge.rendering.shaders.post_chains.UniformPlusPlus;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlConst;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.IOException;
import java.util.Map;


public class RenderEventsHandler {





    public static float intensity = 0;
    public static float radius = 0;
    public static final ResourceLocation PROGRESSION_SHADER_LOC = new ResourceLocation("solarforge","shaders/post/wave.json");



    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event){
        if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0)) {
            if ((intensity > 0)  ) {

                int width = Minecraft.getInstance().getWindow().getScreenWidth();
                int height = Minecraft.getInstance().getWindow().getScreenHeight();
                int time =(int)Minecraft.getInstance().level.getGameTime();
                UniformPlusPlus uniforms = new UniformPlusPlus(Map.of(
                        "intensity",intensity,
                        "screenH",height,
                        "screenW",width,
                        "timeModifier",3f,
                        "time",time
                ));
                if (PROGRESSION_SHADER == null){
                    PROGRESSION_SHADER = loadProgressionShader(PROGRESSION_SHADER_LOC, uniforms);
                }else{
                    PROGRESSION_SHADER.updateUniforms(uniforms);
                }
                RenderSystem.disableBlend();
                RenderSystem.disableDepthTest();
                RenderSystem.enableTexture();
                RenderSystem.resetTextureMatrix();
                if (PROGRESSION_SHADER != null) {
                    PROGRESSION_SHADER.process(Minecraft.getInstance().getFrameTime());
                }
                intensity-=0.02;
            }
        }
    }
    public static void triggerProgressionShader(){
            intensity = 3;
            radius = 0;
    }




    public static PostChainPlusUltra PROGRESSION_SHADER = null;
    public static PostChainPlusUltra loadProgressionShader(ResourceLocation name, UniformPlusPlus uniformPlusPlus)
    {
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


}



//                int width = Minecraft.getInstance().getWindow().getScreenWidth();
//                int height = Minecraft.getInstance().getWindow().getScreenHeight();
//                RenderingTools.renderHandManually(event.getMatrixStack(),event.getPartialTicks());
//                RenderTarget buffer = Minecraft.getInstance().getMainRenderTarget();
//                Framebuffers.buffer2.resize(width, height, false);
//                GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, Framebuffers.buffer2.frameBufferId);
//                int shader = Shaders.WAVE.getShader().getSHADER();
//                int time =(int)Minecraft.getInstance().level.getGameTime();
//                Shaders.WAVE.getShader().addUniform(new Uniform("intensity", intensity, shader));
//                Shaders.WAVE.getShader().addUniform(new Uniform("timeModifier", 3f, shader));
//                Shaders.WAVE.getShader().addUniform(new Uniform("time", time, shader));
//                Shaders.WAVE.getShader().setMatrices();
//                Shaders.WAVE.getShader().process();
//                RenderingTools.blitFramebufferToScreen(width, height,false,buffer);
//                Shaders.close();
//                GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, buffer.frameBufferId);
//                RenderingTools.blitFramebufferToScreen(width, height,false,Framebuffers.buffer2);
//                GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, 0);