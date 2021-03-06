package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.blocks.render.EnergyGeneratorTileRender;
import com.finderfeed.solarforge.magic.blocks.render.RuneEnergyPylonRenderer;

import com.finderfeed.solarforge.magic.blocks.render.WormholeRenderer;
import com.finderfeed.solarforge.client.rendering.deprecated_shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarforge.client.rendering.deprecated_shaders.post_chains.UniformPlusPlus;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.event.RenderLevelLastEvent;


import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;


public class RenderEventsHandler {

    //progression shader
    public static float intensity = 0;
    public static final ResourceLocation PROGRESSION_SHADER_LOC = new ResourceLocation("solarforge","shaders/post/wave.json");
    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent event){
        if ((intensity > 0) && (event.phase == TickEvent.Phase.START) ){
            intensity-=0.03;
        }
    }



    //Thx to DEMON GIRL COLLECTOR (Melonslice) for helping me to fix my shader!
    @SubscribeEvent
    public void renderWorld(RenderLevelLastEvent event){
        if (ClientHelpers.isShadersEnabled()) {
            if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0)) {
                if ((intensity > 0)) {
                    RenderingTools.renderHandManually(event.getPoseStack(), event.getPartialTick());

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
                    RenderSystem.disableBlend();
                    RenderSystem.disableDepthTest();
                    RenderSystem.enableTexture();
                    RenderSystem.resetTextureMatrix();
                    if (PROGRESSION_SHADER != null) {
                        PROGRESSION_SHADER.process(Minecraft.getInstance().getFrameTime());
                    }
                }
            }
        }
    }
    public static void triggerProgressionShader(){
        intensity = 3;
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




    //all tile entity shaders are happening here
    public static Map<UniformPlusPlus,PostChainPlusUltra> ACTIVE_SHADERS = new HashMap<>();
    public Vec2 resolution;
    @SubscribeEvent
    public void renderActiveShaders(RenderLevelLastEvent event){
        float width = (float)Minecraft.getInstance().getWindow().getScreenWidth();
        float height = (float)Minecraft.getInstance().getWindow().getScreenHeight();
        if (resolution == null){
            resolution = new Vec2(width,height);
        }
        if ((Minecraft.getInstance().getWindow().getScreenWidth() != 0) && (Minecraft.getInstance().getWindow().getScreenHeight() != 0)) {
            if (!ACTIVE_SHADERS.isEmpty()) {
                RenderingTools.renderHandManually(event.getPoseStack(), event.getPartialTick());
            }
            resizeShader(width,height,RuneEnergyPylonRenderer.SHADER,EnergyGeneratorTileRender.SHADER, WormholeRenderer.SHADER);


            ACTIVE_SHADERS.forEach((uniforms,shader)->{
                shader.updateUniforms(uniforms);
                shader.process(Minecraft.getInstance().getFrameTime());
            });
            ACTIVE_SHADERS.clear();
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