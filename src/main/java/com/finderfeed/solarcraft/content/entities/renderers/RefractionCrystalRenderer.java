package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.content.entities.not_alive.RefractionCrystal;
import com.finderfeed.solarcraft.events.other_events.OBJModels;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class RefractionCrystalRenderer extends EntityRenderer<RefractionCrystal> {
    public RefractionCrystalRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }


    @Override
    public void render(RefractionCrystal crystal, float hz, float pticks, PoseStack matrices, MultiBufferSource src, int light) {

        matrices.pushPose();
        if (!crystal.isDeploying()) {
            matrices.pushPose();

            matrices.translate(0,3,0);
            matrices.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
            matrices.scale(0.8f,0.5f,0.8f);
            RenderingTools.renderHpBar(matrices,src,crystal.getHealth()/crystal.getMaxHealth());
            matrices.popPose();

            float time = RenderingTools.getTime(crystal.level,pticks);
            matrices.translate(0,1.1,0);
            matrices.scale(0.7f,0.7f,0.7f);
            matrices.mulPose(Vector3f.YN.rotationDegrees(time % 360));
            RenderingTools.renderEntityObjModel(OBJModels.CRYSTAL_1, matrices, src, light, OverlayTexture.NO_OVERLAY, (m) -> {
            });

        }
        matrices.popPose();

        super.render(crystal, hz, pticks, matrices, src, light);
    }

    @Override
    public ResourceLocation getTextureLocation(RefractionCrystal c) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
