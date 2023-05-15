package com.finderfeed.solarcraft.mixins;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.content.entities.renderers.OrbitalExplosionEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "renderLevel",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/renderer/LevelRenderer;renderDebug(Lnet/minecraft/client/Camera;)V",
    shift = At.Shift.BEFORE))
    public void renderPostShaders(PoseStack matrices, float smth, long p_109602_, boolean p_109603_, Camera camera, GameRenderer p_109605_, LightTexture p_109606_, Matrix4f p_109607_, CallbackInfo ci){
        OrbitalExplosionEntityRenderer.processShaders(smth);
    }




}
