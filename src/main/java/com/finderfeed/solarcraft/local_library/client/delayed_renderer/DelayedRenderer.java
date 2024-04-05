package com.finderfeed.solarcraft.local_library.client.delayed_renderer;

import com.finderfeed.solarcraft.SolarCraft;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class DelayedRenderer {


    public static final DelayedBufferSource SRC = new DelayedBufferSource();
    public static Matrix4f MODELVIEW_MATRIX;
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void renderLevel(RenderLevelStageEvent event){
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER){
            if (MODELVIEW_MATRIX != null){
                RenderSystem.enableCull();
                RenderSystem.enableBlend();
                RenderSystem.enableDepthTest();
                RenderSystem.depthMask(false);
                Matrix4f copy = new Matrix4f(RenderSystem.getModelViewMatrix());
                RenderSystem.getModelViewMatrix().set(MODELVIEW_MATRIX);
                if (Minecraft.useShaderTransparency()){
                    Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
                }
                SRC.endBatch();
                RenderSystem.getModelViewMatrix().set(copy);
                if (Minecraft.useShaderTransparency()){
                    Minecraft.getInstance().levelRenderer.getCloudsTarget().bindWrite(false);
                }
            }
        }else if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES){
            MODELVIEW_MATRIX = new Matrix4f(RenderSystem.getModelViewMatrix());
        }
    }





}
