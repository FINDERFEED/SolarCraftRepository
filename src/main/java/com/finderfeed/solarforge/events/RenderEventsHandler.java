package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.shaders.Shaders;
import com.finderfeed.solarforge.shaders.Uniform;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.FramebufferConstants;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


public class RenderEventsHandler {


    public static final Framebuffer buffer2 =
            new Framebuffer(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight(),false,true);


    public float intensity = 0;
    public float radius = 0;
    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event){
        if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0)) {
            if ((intensity > 0) && (radius <= 0.6) ) {
                Framebuffer buffer = Minecraft.getInstance().getMainRenderTarget();
                buffer2.resize(Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight(), false);


                GlStateManager._glBindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, buffer2.frameBufferId);

                Shaders.TEST.getShader().process();
                Shaders.TEST.getShader().addUniform(new Uniform("distance", 10f, Shaders.TEST.getShader().getSHADER()));
                Shaders.TEST.getShader().addUniform(new Uniform("size", 0.1f, Shaders.TEST.getShader().getSHADER()));
                Shaders.TEST.getShader().addUniform(new Uniform("intensity", intensity, Shaders.TEST.getShader().getSHADER()));
                Shaders.TEST.getShader().addUniform(new Uniform("radiusCircle", radius, Shaders.TEST.getShader().getSHADER()));
                Shaders.TEST.getShader().addUniform(new Uniform("time", (int) Minecraft.getInstance().level.getGameTime(), Shaders.TEST.getShader().getSHADER()));
                buffer.blitToScreen(Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight());
                Shaders.close();
                GlStateManager._glBindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, buffer.frameBufferId);
                buffer2.blitToScreen(Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight());
                GlStateManager._glBindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, 0);
                intensity-=0.007;
                radius+=0.01;
            }
        }
    }

    @SubscribeEvent
    public void triggerShader(InputEvent.KeyInputEvent event){
        if ((event.getKey() == GLFW.GLFW_KEY_F) && event.getAction() == GLFW.GLFW_PRESS ){
            this.intensity = 2;
            this.radius = 0;

        }
    }



}
