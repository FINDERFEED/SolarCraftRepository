package com.finderfeed.solarforge.mixins;

import com.finderfeed.solarforge.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarforge.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarforge.content.blocks.render.DimensionCoreRenderer;
import com.finderfeed.solarforge.content.blocks.render.EnergyGeneratorTileRender;
import com.finderfeed.solarforge.content.blocks.render.RuneEnergyPylonRenderer;
import com.finderfeed.solarforge.content.blocks.render.WormholeRenderer;
import com.finderfeed.solarforge.events.RenderEventsHandler;
import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import static com.finderfeed.solarforge.events.RenderEventsHandler.*;

@Mixin(GameRenderer.class)
public class ShaderMixin {

    public Vec2 resolution;

    @Inject(method = "render",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V",
    shift = At.Shift.AFTER))
    public void processPostEffects(float p_109094_, long p_109095_, boolean p_109096_, CallbackInfo ci){
        this.processBlockEntityShaders();
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

    private void processBlockEntityShaders(){
        float width = (float)Minecraft.getInstance().getWindow().getScreenWidth();
        float height = (float)Minecraft.getInstance().getWindow().getScreenHeight();
        if (resolution == null){
            resolution = new Vec2(width,height);
        }
        if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0)) {
            resizeShader(width,height, RuneEnergyPylonRenderer.SHADER, EnergyGeneratorTileRender.SHADER, WormholeRenderer.SHADER,
                    DimensionCoreRenderer.SHADER);
            RenderEventsHandler.ACTIVE_SHADERS.forEach((id,shader)->{
                shader.process(Minecraft.getInstance().getFrameTime());
            });

            RenderEventsHandler.ACTIVE_SHADERS.clear();
        }
    }

    private void resizeShader(float width, float height, PostChainPlusUltra... shader){
        if ((shader != null) && (resolution.x != width || resolution.y != height)){
            resolution = new Vec2(width,height);
            for (PostChainPlusUltra shaders : shader) {
                if (shaders != null) {
                    shaders.resize(Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight());
                }
            }
        }
    }

}