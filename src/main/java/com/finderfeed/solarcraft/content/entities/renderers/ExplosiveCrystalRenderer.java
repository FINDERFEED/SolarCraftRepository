package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.content.entities.not_alive.ExplosiveCrystal;
import com.finderfeed.solarcraft.events.other_events.OBJModels;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
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
            matrices.translate(0,2.5,0);

            matrices.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
            matrices.translate(0.5,0,0);
            matrices.scale(0.03f,0.03f,0.03f);
            matrices.mulPose(Vector3f.ZN.rotationDegrees(180));

            GuiComponent.drawCenteredString(matrices,Minecraft.getInstance().font,
                    String.valueOf(crystal.getRemainingActivationSeconds()),0,0,0xff1111);

            matrices.popPose();

            matrices.pushPose();
            matrices.translate(0,3.1,0);
            matrices.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
            matrices.scale(0.7f,0.5f,0.7f);

            RenderingTools.renderHpBar(matrices,src,crystal.getHealth()/crystal.getMaxHealth());
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
