package com.finderfeed.solarforge.magic_items.items.isters;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class EnergyPylonISTER extends ItemStackTileEntityRenderer {

    public final ResourceLocation MAIN = new ResourceLocation("solarforge","textures/misc/tile_energy_pylon.png");
    @Override
    public void renderByItem(ItemStack p_239207_1_, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_) {
        if (p_239207_2_ == ItemCameraTransforms.TransformType.GUI){
            p_239207_3_.pushPose();
            IVertexBuilder vertex = p_239207_4_.getBuffer(RenderType.text(MAIN));
            p_239207_3_.translate(0.5,0.5,0);
            Matrix4f matrix = p_239207_3_.last().pose();
            float time = (Minecraft.getInstance().level.getGameTime() + Minecraft.getInstance().getDeltaFrameTime())*5;
            p_239207_3_.mulPose(Vector3f.ZN.rotationDegrees(time%360));
            vertex.vertex(matrix,  -0.5f,-0.5f,0).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix,  0.5f,-0.5f,0).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix,  0.5f,0.5f,0).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5f,0.5f,0).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            p_239207_3_.popPose();

            p_239207_3_.pushPose();
            p_239207_3_.translate(0.5,0.5,0);
            Matrix4f matrix2 = p_239207_3_.last().pose();
            p_239207_3_.mulPose(Vector3f.ZP.rotationDegrees(time%360));
            vertex.vertex(matrix2,  -0.5f,-0.5f,0).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix2,  0.5f,-0.5f,0).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix2,  0.5f,0.5f,0).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix2, -0.5f,0.5f,0).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            p_239207_3_.popPose();
        }

    }
}
