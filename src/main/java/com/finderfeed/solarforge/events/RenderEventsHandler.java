package com.finderfeed.solarforge.events;


import java.util.Map;

import com.finderfeed.solarforge.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarforge.rendering.shaders.post_chains.UniformPlusPlus;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class RenderEventsHandler {





    public static float intensity = 0;
    public static float radius = 0;
    public static final ResourceLocation PROGRESSION_SHADER_LOC = new ResourceLocation("solarforge","shaders/post/wave.json");



    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event){
        if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0)) {
            if ((intensity > 0)  ) {
              
                    UniformPlusPlus uniforms = new UniformPlusPlus(Map.of(
                                    "Intensity", intensity,
                                    "TimeModifier", 3f,
                                    "Time", (float) Minecraft.getInstance().level.getGameTime()
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