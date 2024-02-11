package com.finderfeed.solarcraft.mixins;

import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarcraft.content.blocks.render.DimensionCoreRenderer;
import com.finderfeed.solarcraft.content.blocks.render.EnergyGeneratorTileRender;
import com.finderfeed.solarcraft.content.blocks.render.RuneEnergyPylonRenderer;
import com.finderfeed.solarcraft.content.blocks.render.WormholeRenderer;
import com.finderfeed.solarcraft.content.entities.renderers.OrbitalExplosionEntityRenderer;
import com.finderfeed.solarcraft.events.RenderEventsHandler;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.GlowShaderInit;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.world.phys.Vec2;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import static com.finderfeed.solarcraft.events.RenderEventsHandler.*;

@Mixin(GameRenderer.class)
public class ShaderMixin {

    public Vec2 resolution;


    @Inject(method = "render",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V",
    shift = At.Shift.AFTER))
    public void processPostEffects(float p_109094_, long p_109095_, boolean p_109096_, CallbackInfo ci){

        //GlowShaderInit.processGlowShader();
        this.processPostShaders();
        this.processProgressionShader();
    }

    private void processProgressionShader(){
        if (ClientHelpers.isShadersEnabled()) {
            if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0)) {
                if ((intensity > 0)) {
                    float time = Minecraft.getInstance().level.getGameTime();
                    UniformPlusPlus uniforms = new UniformPlusPlus(Map.of(
                            "intensity", intensity,
                            "timeModifier", 3f,
                            "time", time
                    ));
                    if (PROGRESSION_SHADER == null) {
                        PROGRESSION_SHADER = loadProgressionShader(PROGRESSION_SHADER_LOC, uniforms);
                    } else {
                        PROGRESSION_SHADER.updateUniforms(uniforms);
                        PROGRESSION_SHADER.resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
                    }
                    if (PROGRESSION_SHADER != null) {
                        PROGRESSION_SHADER.process(Minecraft.getInstance().getFrameTime());
                    }
                }
            }
        }
    }

    private void processPostShaders(){
        float width = (float)Minecraft.getInstance().getWindow().getScreenWidth();
        float height = (float)Minecraft.getInstance().getWindow().getScreenHeight();
        if (resolution == null){
            resolution = new Vec2(width,height);
        }
        if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0)) {
            resizeShader(width,height, RuneEnergyPylonRenderer.SHADER, EnergyGeneratorTileRender.SHADER, WormholeRenderer.SHADER,
                    DimensionCoreRenderer.SHADER,OrbitalExplosionEntityRenderer.postChain, GlowShaderInit.GLOW);

            RenderEventsHandler.ACTIVE_SHADERS.forEach((id,shader)->{
                shader.process(Minecraft.getInstance().getFrameTime());
            });

            OrbitalExplosionEntityRenderer.firstPass = true;
            RenderEventsHandler.ACTIVE_SHADERS.clear();
        }
    }

    private void resizeShader(float width, float height, PostChain... shader){
        if ((shader != null) && (resolution.x != width || resolution.y != height)){
            resolution = new Vec2(width,height);
            for (PostChain shaders : shader) {
                if (shaders != null) {
                    shaders.resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
                }
            }
        }
    }

}
