package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.RenderingTools;
import com.finderfeed.solarforge.rendering.shaders.Shaders;
import com.finderfeed.solarforge.rendering.shaders.Uniform;

import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlConst;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL20;


public class RenderEventsHandler {





    public static float intensity = 0;
    public static float radius = 0;



    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event){
        if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0) ) {
            if ((intensity > 0)  ) {


                RenderTarget inTarget = Minecraft.getInstance().getMainRenderTarget();
                RenderTarget outTarget = Framebuffers.buffer2;
                inTarget.unbindWrite();
                float var2 = (float)outTarget.width;
                float var3 = (float)outTarget.height;
                RenderSystem.viewport(0, 0, (int)var2, (int)var3);

                int shader = Shaders.WAVE.getShader().getSHADER();
                int time =(int)Minecraft.getInstance().level.getGameTime();
                Shaders.WAVE.getShader().addUniform(new Uniform("intensity", intensity, shader));
                Shaders.WAVE.getShader().addUniform(new Uniform("timeModifier", 3f, shader));
                Shaders.WAVE.getShader().addUniform(new Uniform("time", time, shader));
                Shaders.WAVE.getShader().setMatrices();
                Shaders.WAVE.getShader().process();

                outTarget.clear(Minecraft.ON_OSX);
                outTarget.bindWrite(false);
                RenderSystem.depthFunc(519);
                BufferBuilder var5 = Tesselator.getInstance().getBuilder();
                var5.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
                var5.vertex(0.0D, 0.0D, 500.0D).endVertex();
                var5.vertex((double)var2, 0.0D, 500.0D).endVertex();
                var5.vertex((double)var2, (double)var3, 500.0D).endVertex();
                var5.vertex(0.0D, (double)var3, 500.0D).endVertex();
                var5.end();
                BufferUploader._endInternal(var5);
                RenderSystem.depthFunc(515);
                Shaders.close();
                outTarget.unbindWrite();
                inTarget.unbindRead();
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
            new TextureTarget(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight(),true,Minecraft.ON_OSX);
}

class Framebuffer extends RenderTarget{

    public Framebuffer(boolean p_166199_) {
        super(p_166199_);
        this.resize(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight(),true);
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