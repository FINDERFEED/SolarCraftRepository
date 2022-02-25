package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.entities.ExplosiveCrystal;
import com.finderfeed.solarforge.events.other_events.OBJModels;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class ExplosiveCrystalRenderer extends EntityRenderer<ExplosiveCrystal> {
    public ExplosiveCrystalRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(ExplosiveCrystal crystal, float hz, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        matrices.pushPose();
        if (!crystal.isDeploying()) {
            matrices.pushPose();
            matrices.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
            matrices.translate(0,3,0);
            GuiComponent.drawCenteredString(matrices,Minecraft.getInstance().font,
                    String.valueOf(crystal.getRemainingActivationSeconds()),0,0,0xffaaaa);

            matrices.popPose();
            matrices.pushPose();

            float time = RenderingTools.getTime(crystal.level,pticks);
            matrices.translate(0,1.1,0);
            matrices.scale(0.7f,0.7f,0.7f);
            matrices.mulPose(Vector3f.YN.rotationDegrees(time % 360));
            RenderingTools.renderObjModel(OBJModels.EXPLOSIVE_CRYSTAL, matrices, src, light, OverlayTexture.NO_OVERLAY, (m) -> {
            });
            matrices.popPose();
        }
        matrices.popPose();
        super.render(crystal, hz, pticks, matrices, src, light);
    }

    @Override
    public ResourceLocation getTextureLocation(ExplosiveCrystal crystal) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
