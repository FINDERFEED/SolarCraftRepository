package com.finderfeed.solarcraft.content.entities.renderers.uldera_crystal;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.entities.uldera_crystal.UlderaCrystalBoss;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModel;
import com.finderfeed.solarcraft.registries.SCBedrockModels;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class UlderaCrystalBossRenderer extends EntityRenderer<UlderaCrystalBoss> {

    private FDModel model;

    public UlderaCrystalBossRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.model = new FDModel(SCBedrockModels.ULDERA_CRYSTAL);
    }


    @Override
    public void render(UlderaCrystalBoss entity, float idk, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        matrices.pushPose();

        entity.getAnimationManager().getAsClientManager().applyAnimations(model,pticks);

        matrices.scale(3,3,3);
        model.render(matrices,src.getBuffer(RenderType.entityTranslucentCull(new ResourceLocation(SolarCraft.MOD_ID,"textures/entities/uldera_crystal_inner.png"))),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);
        model.render(matrices,src.getBuffer(RenderType.eyes(new ResourceLocation(SolarCraft.MOD_ID,"textures/entities/uldera_crystal_glow.png"))),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);

        matrices.popPose();

    }


    @Override
    public boolean shouldRender(UlderaCrystalBoss p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(UlderaCrystalBoss p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
