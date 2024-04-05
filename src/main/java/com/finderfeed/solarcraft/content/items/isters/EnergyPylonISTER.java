package com.finderfeed.solarcraft.content.items.isters;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;


public class EnergyPylonISTER extends BlockEntityWithoutLevelRenderer {

    public final ResourceLocation MAIN = new ResourceLocation("solarcraft","textures/misc/tile_energy_pylon.png");

    public EnergyPylonISTER(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack martices, MultiBufferSource src, int p_239207_5_, int p_239207_6_) {
        if (ctx == ItemDisplayContext.GUI){
            martices.pushPose();
            VertexConsumer vertex = src.getBuffer(RenderType.text(MAIN));
            martices.translate(0.5,0.5,0);
            Matrix4f matrix = martices.last().pose();
            float time = (Minecraft.getInstance().level.getGameTime() + Minecraft.getInstance().getDeltaFrameTime())*5;
//            p_239207_3_.mulPose(Vector3f.ZN.rotationDegrees(time%360));
            martices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZN(),time%360));
            vertex.vertex(matrix,  -0.5f,-0.5f,0).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix,  0.5f,-0.5f,0).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix,  0.5f,0.5f,0).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5f,0.5f,0).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            martices.popPose();

            martices.pushPose();
            martices.translate(0.5,0.5,0);
            Matrix4f matrix2 = martices.last().pose();
//            martices.mulPose(Vector3f.ZP.rotationDegrees(time%360));
            martices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),time%360));

            vertex.vertex(matrix2,  -0.5f,-0.5f,0).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix2,  0.5f,-0.5f,0).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix2,  0.5f,0.5f,0).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix2, -0.5f,0.5f,0).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            martices.popPose();
        }

    }
}
