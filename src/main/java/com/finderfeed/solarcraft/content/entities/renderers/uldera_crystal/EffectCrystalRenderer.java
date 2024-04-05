package com.finderfeed.solarcraft.content.entities.renderers.uldera_crystal;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.entities.uldera_crystal.EffectCrystal;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModel;
import com.finderfeed.solarcraft.registries.SCBedrockModels;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class EffectCrystalRenderer extends EntityRenderer<EffectCrystal> {

    public static final ResourceLocation LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/entities/effect_crystal.png");
    public static final ResourceLocation LOCATION_GLOW = new ResourceLocation(SolarCraft.MOD_ID,"textures/entities/effect_crystal_glow.png");

    private FDModel model;
    public EffectCrystalRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        model = new FDModel(SCBedrockModels.EFFECT_CRYSTAL);
    }

    @Override
    public void render(EffectCrystal crystal, float p_114486_, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        matrices.pushPose();
        matrices.translate(0,crystal.getBbHeight()/2f,0);
        crystal.getAnimationManager().getAsClientManager().applyAnimations(model,pticks);
        model.render(matrices,src.getBuffer(RenderType.entityTranslucentCull(LOCATION)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);
        model.render(matrices,src.getBuffer(RenderType.eyes(LOCATION_GLOW)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EffectCrystal p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
