package com.finderfeed.solarcraft.local_library.client;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarcraft.config.SolarcraftClientConfig;
import com.finderfeed.solarcraft.registries.SCRenderTargets;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
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
public class GlowShaderProcessor {



    public static PostChainPlusUltra GLOW;

    public static RenderTarget GLOW_RENDER_TARGET;
    public static RenderStateShard.OutputStateShard GLOW_TARGET_SHARD = new RenderStateShard.OutputStateShard("glow_target",()->{
            if (SolarcraftClientConfig.GLOW_ENABLED.get()) {
                GLOW_RENDER_TARGET.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
            }
        GLOW_RENDER_TARGET.bindWrite(false);
        },()->{
        Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    });

    public static PostChainPlusUltra BLOOM;
    public static RenderTarget BLOOM_IN;
    public static RenderStateShard.OutputStateShard BLOOM_TARGET_SHARD = new RenderStateShard.OutputStateShard("bloom_target",()->{
        if (SolarcraftClientConfig.GLOW_ENABLED.get()) {
            BLOOM_IN.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
        }
        BLOOM_IN.bindWrite(false);
        },()->{
        processBloomShader();
        Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    });

    public static PostChainPlusUltra BLIT_BLOOM;

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

            BLOOM = new PostChainPlusUltra(new ResourceLocation(SolarCraft.MOD_ID,"shaders/post/bloom.json"),new UniformPlusPlus(
                    Map.of(
                            "deviation",1f,
                            "size",5f
                    )
            ));
            BLOOM.resize(
                    window.getScreenWidth(),
                    window.getScreenHeight()
            );
            BLOOM_IN = BLOOM.getTempTarget("bloom_in");

            BLIT_BLOOM = new PostChainPlusUltra(new ResourceLocation(SolarCraft.MOD_ID,"shaders/post/blit_bloom.json"),new UniformPlusPlus(Map.of()));
            BLIT_BLOOM.resize(window.getScreenWidth(),window.getScreenHeight());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void afterLevelRender(RenderLevelStageEvent event){
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL){
            processGlowShader();

            //GlowShaderProcessor.BLIT_BLOOM.process(Minecraft.getInstance().getFrameTime());
            SCRenderTargets.BLOOM_OUT_TARGET.clear(Minecraft.ON_OSX);
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
            //processBloomShader();

        }
    }
    public static void processBloomShader(){
        if (SolarcraftClientConfig.GLOW_ENABLED.get()){
            BLOOM.updateUniforms(new UniformPlusPlus(
                    Map.of(
                            "deviation",1f,
                            "size",5f,
                            "xscale",3f,
                            "colMod",1f
                    )
            ));
            BLOOM.process(Minecraft.getInstance().getFrameTime());
            BLOOM_IN.bindWrite(false);
            GlStateManager._clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT,true);
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
        }
    }

    public static void processGlowShader(){
        if (SolarcraftClientConfig.GLOW_ENABLED.get()) {
            GlowShaderProcessor.GLOW.process(Minecraft.getInstance().getFrameTime());
            GlowShaderProcessor.GLOW_RENDER_TARGET.bindWrite(false);
            GlStateManager._clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT,true);
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
        }
    }
}
