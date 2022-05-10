package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_crystal;

import com.finderfeed.solarforge.events.other_events.OBJModels;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.blocks.render.abstracts.TileEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class ClearingRitualCrystalRenderer extends TileEntityRenderer<ClearingRitualCrystalTile> {
    public ClearingRitualCrystalRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ClearingRitualCrystalTile tile, float pticks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
        matrices.pushPose();
        float time = (tile.getTicker() + pticks) % 360;
        matrices.translate(0.5,0.5,0.5);
        matrices.mulPose(Vector3f.YP.rotationDegrees(time));
        matrices.scale(0.6f,0.6f,0.6f);
        RenderingTools.renderObjModel(OBJModels.CLEARING_RITUAL_CRYSTAL,matrices,src,light,overlay,(m)->{});
        matrices.popPose();
        matrices.pushPose();
        if (tile.getREType() != null) {
            VertexConsumer vertex = src.getBuffer(RenderType.text(new ResourceLocation("solarforge", "textures/misc/tile_energy_pylon_" + tile.getREType().id + ".png")));
            for (int i = 0; i < 8; i ++) {
                matrices.pushPose();
                matrices.translate(0.5,0.5,0.5);
                matrices.scale(0.7f,0.7f,0.7f);
                matrices.mulPose(Vector3f.YN.rotationDegrees(i*45+time));
                Matrix4f matrix1 = matrices.last().pose();
                vertex.vertex(matrix1, -0.5f, 0.5f, 1.5f).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, 0.5f, 0.5f, 1.5f).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, 0.5f, -0.5f, 1.5f).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, -0.5f, -0.5f, 1.5f).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

                vertex.vertex(matrix1, -0.5f, -0.5f, 1.5f).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, 0.5f, -0.5f, 1.5f).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, 0.5f, 0.5f, 1.5f).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, -0.5f, 0.5f, 1.5f).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                matrices.popPose();
            }
        }
        matrices.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(ClearingRitualCrystalTile tile) {
        return super.shouldRenderOffScreen(tile);
    }

    @Override
    public boolean shouldRender(ClearingRitualCrystalTile tile, Vec3 idk) {
        return super.shouldRender(tile, idk);
    }
}
