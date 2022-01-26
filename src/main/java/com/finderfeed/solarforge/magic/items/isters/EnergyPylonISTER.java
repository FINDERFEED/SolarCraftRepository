package com.finderfeed.solarforge.magic.items.isters;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Matrix4f;

import com.mojang.math.Vector3f;

public class EnergyPylonISTER extends BlockEntityWithoutLevelRenderer {

    public final ResourceLocation MAIN = new ResourceLocation("solarforge","textures/misc/tile_energy_pylon.png");

    public EnergyPylonISTER(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }

    @Override
    public void renderByItem(ItemStack p_239207_1_, ItemTransforms.TransformType p_239207_2_, PoseStack p_239207_3_, MultiBufferSource p_239207_4_, int p_239207_5_, int p_239207_6_) {
        if (p_239207_2_ == ItemTransforms.TransformType.GUI){
            p_239207_3_.pushPose();
            VertexConsumer vertex = p_239207_4_.getBuffer(RenderType.text(MAIN));
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
