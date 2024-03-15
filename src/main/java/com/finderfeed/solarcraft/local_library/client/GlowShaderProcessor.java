package com.finderfeed.solarcraft.local_library.client;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarcraft.config.SolarcraftClientConfig;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.SCRenderTargets;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
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







    public static RenderStateShard.OutputStateShard getBloomShard(float deviation,float size,float xscale,float colorMod){
        return new RenderStateShard.OutputStateShard("bloom_target",()->{
            if (SolarcraftClientConfig.GLOW_ENABLED.get()) {
                SCRenderTargets.BLOOM_OUT_TARGET.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
            }
            SCRenderTargets.BLOOM_OUT_TARGET.bindWrite(false);
        },()->{
            if (SolarcraftClientConfig.GLOW_ENABLED.get()) {
            //    processBloomShader(deviation,size,xscale,colorMod);
            }
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
        });
    }

    public static PostChainPlusUltra BLIT_BLOOM;
    public static EffectInstance BLOOM_SHADER;

    @SubscribeEvent
    public static void clientPlayerJoin(ClientPlayerNetworkEvent.LoggingIn event){
//        try {
//            var window = Minecraft.getInstance().getWindow();
//
//
//
////            BLIT_BLOOM = new PostChainPlusUltra(new ResourceLocation(SolarCraft.MOD_ID,"shaders/post/blit_bloom.json"),new UniformPlusPlus(Map.of()));
////            BLIT_BLOOM.resize(window.getScreenWidth(),window.getScreenHeight());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void afterLevelRender(RenderLevelStageEvent event){
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL){
            blitBloom();
        }
    }

    public static void blitBloom(){
        if (SolarcraftClientConfig.GLOW_ENABLED.get()) {
//            GlowShaderProcessor.BLIT_BLOOM.process(Minecraft.getInstance().getFrameTime());

            processBloomShader();

            SCRenderTargets.BLOOM_OUT_TARGET.bindWrite(false);
            GlStateManager._clearColor(0,0,0,0);
            GlStateManager._clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, true);
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
        }
    }

    public static void processBloomShader(){
        BLOOM_SHADER.safeGetUniform("deviation").set(0.84089642f);
        BLOOM_SHADER.safeGetUniform("size").set(5f);
        BLOOM_SHADER.safeGetUniform("scale").set(1f);
        BLOOM_SHADER.safeGetUniform("colMod").set(1.5f);
        RenderingTools.loadDefaultShaderUniforms(BLOOM_SHADER,SCRenderTargets.BLOOM_OUT_TARGET,Minecraft.getInstance().getMainRenderTarget(),
                Minecraft.getInstance().level.getGameTime() + Minecraft.getInstance().getFrameTime());
        RenderingTools.blitShaderEffect(BLOOM_SHADER,SCRenderTargets.BLOOM_OUT_TARGET,Minecraft.getInstance().getMainRenderTarget(),
                GL11.GL_SRC_ALPHA,GL11.GL_ONE,false);
    }
}
