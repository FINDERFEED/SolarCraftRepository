package com.finderfeed.solarcraft.local_library.client;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class GlowShaderInit {



    public static PostChainPlusUltra GLOW;

    public static RenderTarget GLOW_RENDER_TARGET;
    public static RenderStateShard.OutputStateShard GLOW_TARGET_SHARD = new RenderStateShard.OutputStateShard("glow_target",()->{
        if (ClientHelpers.isShadersEnabled()) {
            GLOW_RENDER_TARGET.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
        }
        GLOW_RENDER_TARGET.bindWrite(false);
    },()->{
        Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    });

    @SubscribeEvent
    public static void clientPlayerJoin(ClientPlayerNetworkEvent.LoggingIn event){
        try {
            GLOW = new PostChainPlusUltra(new ResourceLocation(SolarCraft.MOD_ID,"shaders/post/glow.json"),new UniformPlusPlus(
                    Map.of(
                            "l",1f,
                            "brightness",1f
                    )
            ));
            var window = Minecraft.getInstance().getWindow();
            GLOW.resize(
                    window.getScreenWidth(),
                    window.getScreenHeight()
            );
            GLOW_RENDER_TARGET = GLOW.getTempTarget("glow");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void afterLevelRender(RenderLevelStageEvent event){
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL){
            GLOW.updateUniforms(new UniformPlusPlus(
                    Map.of(
                            "l",1f,
                            "brightness",1f
                    )
            ));
            processGlowShader();
        }
    }

    public static void copyDepthBuffer(){
        //GLOW_RENDER_TARGET.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
    }
    public static void processGlowShader(){
        if (ClientHelpers.isShadersEnabled()) {
            GlowShaderInit.GLOW.process(Minecraft.getInstance().getFrameTime());
            GlowShaderInit.GLOW_RENDER_TARGET.bindWrite(false);
            GlStateManager._clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT,true);
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
        }
    }
}
