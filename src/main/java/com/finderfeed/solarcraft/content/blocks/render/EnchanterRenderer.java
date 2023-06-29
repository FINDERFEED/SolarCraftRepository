package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.AbstractRunicEnergyContainerRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class EnchanterRenderer extends AbstractRunicEnergyContainerRenderer<EnchanterBlockEntity> {
    public static final ResourceLocation fancyRing = new ResourceLocation("solarcraft", "textures/misc/fancy_ring_1.png");

    public EnchanterRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(EnchanterBlockEntity tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        ItemStack stack = tile.getStackInSlot(0);
        float time = RenderingTools.getTime(tile.getLevel(),pticks);
        if (stack != null){
            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

            matrices.pushPose();
            matrices.translate(0.5,1.3,0.5);
            matrices.scale(0.5f,0.5f,0.5f);
//            matrices.mulPose(Vector3f.YN.rotationDegrees(time%360));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),time%360));
            renderer.render(stack, ItemDisplayContext.FIXED,true,matrices,buffer,light, OverlayTexture.NO_OVERLAY,
                    renderer.getModel(stack,Minecraft.getInstance().level, Minecraft.getInstance().player,0));
            matrices.popPose();
        }
        if (tile.enchantingInProgress()) {

            matrices.pushPose();

            VertexConsumer vertex = buffer.getBuffer(RenderType.text(fancyRing));
            matrices.translate(0.5, 0.001, 0.5);
//            matrices.mulPose(Vector3f.YP.rotationDegrees(time % 360));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),time % 360));
            matrices.scale(0.25f, 0, 0.25f);
            PoseStack.Pose entry = matrices.last();
            vertex.vertex(entry.pose(), -1.25F, 0, -1.25F).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 1.25F, 0, -1.25F).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 1.25F, 0, 1.25F).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), -1.25F, 0, 1.25F).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(entry.pose(), -1.25F, 0, 1.25F).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 1.25F, 0, 1.25F).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 1.25F, 0, -1.25F).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), -1.25F, 0, -1.25F).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();


//            matrices.mulPose(Vector3f.YP.rotationDegrees(-(time % 360) * 2));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),-(time % 360) * 2));
            vertex.vertex(entry.pose(), -2.8F, 0, -2.8F).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 2.8F, 0, -2.8F).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 2.8F, 0, 2.8F).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), -2.8F, 0, 2.8F).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(entry.pose(), -2.8F, 0, 2.8F).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 2.8F, 0, 2.8F).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), 2.8F, 0, -2.8F).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(entry.pose(), -2.8F, 0, -2.8F).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            matrices.popPose();
        }
        super.render(tile, pticks, matrices, buffer, light, overlay);
    }
}
