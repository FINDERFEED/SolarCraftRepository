package com.finderfeed.solarcraft.mixins;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarcraft.content.blocks.render.DimensionCoreRenderer;
import com.finderfeed.solarcraft.content.blocks.render.EnergyGeneratorTileRender;
import com.finderfeed.solarcraft.content.blocks.render.RuneEnergyPylonRenderer;
import com.finderfeed.solarcraft.content.blocks.render.WormholeRenderer;
import com.finderfeed.solarcraft.content.entities.renderers.OrbitalExplosionEntityRenderer;
import com.finderfeed.solarcraft.local_library.client.GlowShaderProcessor;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.SCRenderTargets;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Random;

import static com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler.cameraShakeEffect;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "renderLevel",at = @At(value = "HEAD"))
    public void renderLevel(float p_109090_, long p_109091_, PoseStack matrices, CallbackInfo ci){
        if (cameraShakeEffect != null) {
            Random random = new Random(Minecraft.getInstance().level.getGameTime() * 1233);

            float spread = cameraShakeEffect.getMaxSpread();
            float mod = 1f;
            int time = cameraShakeEffect.getTicker();
            if (time <= cameraShakeEffect.getInTime()) {
                if (cameraShakeEffect.getInTime() == 0){
                    mod = 0;
                }else {
                    mod = time / (float) cameraShakeEffect.getInTime();
                }
            } else if (time >= cameraShakeEffect.getInTime() + cameraShakeEffect.getStayTime()) {
                if (cameraShakeEffect.getOutTime() == 0){
                    mod = 0;
                } else {
                    mod = 1.0f - (time - (cameraShakeEffect.getInTime() + cameraShakeEffect.getStayTime())) / (float) cameraShakeEffect.getOutTime();
                }
            }


            spread *= mod;

            float rx = random.nextFloat() * spread * 2 - spread;
            float ry = random.nextFloat() * spread * 2 - spread;
            matrices.translate(rx,ry,0);
//            System.out.println(rx);
//            System.out.println(ry);
        }
    }

    @Inject(method = "<init>",at = @At(value = "RETURN"))
    public void init(Minecraft mc, ItemInHandRenderer p_234220_, ResourceManager p_234221_, RenderBuffers p_234222_, CallbackInfo ci){
        int width = mc.getWindow().getWidth();
        int height = mc.getWindow().getHeight();
        SCRenderTargets.init(width,height);
        RenderingTools.FULL_SIZED_SHADER_ORTHO_MATRIX = new Matrix4f().setOrtho(0.0F, (float)width, 0.0F, (float)height, 0.1F, 1000.0F);

    }

    @Inject(method = "resize",at = @At(value = "HEAD"))
    public void onScreenResize(int width, int height, CallbackInfo ci){
        SCRenderTargets.BLOOM_OUT_TARGET.resize(width, height, Minecraft.ON_OSX);
        resizeShader(width,height,
                RuneEnergyPylonRenderer.SHADER,
                EnergyGeneratorTileRender.SHADER,
                WormholeRenderer.SHADER,
                DimensionCoreRenderer.SHADER,
                OrbitalExplosionEntityRenderer.postChain,
                GlowShaderProcessor.BLIT_BLOOM
                );
        RenderingTools.FULL_SIZED_SHADER_ORTHO_MATRIX = new Matrix4f().setOrtho(0.0F, (float)width, 0.0F, (float)height, 0.1F, 1000.0F);
    }

    private void resizeShader(int width, int height, PostChain... shader){
        for (PostChain shaders : shader) {
            if (shaders != null) {
                shaders.resize(width, height);
            }
        }
    }
}
