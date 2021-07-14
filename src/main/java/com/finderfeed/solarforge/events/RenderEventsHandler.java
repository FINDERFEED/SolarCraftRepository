package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.shaders.Shaders;
import com.finderfeed.solarforge.shaders.Uniform;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.FramebufferConstants;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class RenderEventsHandler {


    public static final Framebuffer buffer2 =
            new Framebuffer(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight(),false,true);


    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event){
        Framebuffer buffer = Minecraft.getInstance().getMainRenderTarget();
        buffer2.resize(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight(),false);


        GlStateManager._glBindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER,buffer2.frameBufferId);

        Shaders.TEST.getShader().process();
        Shaders.TEST.getShader().addUniform(new Uniform("distance",0.1f,Shaders.TEST.getShader().getSHADER()));
        Shaders.TEST.getShader().addUniform(new Uniform("size",0.1f,Shaders.TEST.getShader().getSHADER()));
        Shaders.TEST.getShader().addUniform(new Uniform("intensity",0.1f,Shaders.TEST.getShader().getSHADER()));
        buffer.blitToScreen(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight());
        Shaders.close();
        GlStateManager._glBindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER,buffer.frameBufferId);
        buffer2.blitToScreen(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight());
        GlStateManager._glBindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER,0);
    }


}
