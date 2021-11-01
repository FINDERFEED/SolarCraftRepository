package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;

import com.finderfeed.solarforge.magic_items.blocks.render.abstracts.AbstractRunicEnergyContainerRCBERenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

public class InfusingTableRenderer extends AbstractRunicEnergyContainerRCBERenderer<InfusingTableTileEntity> {
    public final ResourceLocation text = new ResourceLocation("solarforge","textures/misc/solar_infuser_ring.png");
    public InfusingTableRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(InfusingTableTileEntity tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int light2) {
        super.render(tile,partialTicks,matrices,buffer,light,light2);
        float time = (tile.getLevel().getGameTime()+partialTicks) ;
        if (tile.RECIPE_IN_PROGRESS) {
            matrices.pushPose();

            matrices.translate(0, -0.30, 0);
            float bigTing = 33 - (time + 15) % 67;
            if (bigTing >= 0) {
                matrices.translate(0, bigTing / 100, 0);
            } else {
                matrices.translate(0, -bigTing / 100, 0);
            }
            drawRing(partialTicks, matrices, buffer, light, light2, 0.75f,time*2);
            matrices.popPose();

            matrices.pushPose();

            matrices.translate(0, -0.30, 0);
            float centerTing = 33 - (time + 30) % 67;
            if (centerTing >= 0) {
                matrices.translate(0, centerTing / 100, 0);
            } else {
                matrices.translate(0, -centerTing / 100, 0);
            }
            drawRing(partialTicks, matrices, buffer, light, light2, 0.525f,time*2+90);
            matrices.popPose();

            matrices.pushPose();

            matrices.translate(0, -0.30, 0);
            float smallTing = 33 - (time + 45) % 67;
            if (smallTing >= 0) {
                matrices.translate(0, smallTing / 100, 0);
            } else {
                matrices.translate(0, -smallTing / 100, 0);
            }
            drawRing(partialTicks, matrices, buffer, light, light2, 0.30f,time*2+270);
            matrices.popPose();
        }
        matrices.pushPose();
        if (!tile.isEmpty() ) {

            if (tile.getItem(9).isEmpty()) {
                matrices.translate(0.5, 0.5, 0.5);
                matrices.mulPose(Vector3f.YP.rotationDegrees((time % 360) * 2f));
                Minecraft.getInstance().getItemRenderer().render(tile.getItem(0), ItemTransforms.TransformType.GROUND, true,
                        matrices, buffer, light, light2, Minecraft.getInstance().getItemRenderer().getModel(tile.getItem(0), null, null,0));
            }else{
                matrices.translate(0.5, 0.5, 0.5);
                matrices.mulPose(Vector3f.YP.rotationDegrees((time % 360) * 2f));
                Minecraft.getInstance().getItemRenderer().render(tile.getItem(9), ItemTransforms.TransformType.GROUND, true,
                        matrices, buffer, light, light2, Minecraft.getInstance().getItemRenderer().getModel(tile.getItem(9), null, null,0));
            }
        }
        matrices.popPose();
    }

    public void drawRing(float partialTicks,PoseStack stack,MultiBufferSource buffer,int light,int light2,float scaleFactor,float angle){
        VertexConsumer vertex = buffer.getBuffer(RenderType.text(text));
        stack.translate(0.5,0.8,0.5);
        stack.mulPose(Vector3f.YP.rotationDegrees(angle));
        PoseStack.Pose entry = stack.last();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,0.5F*scaleFactor).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(entry.pose(),-0.5F*scaleFactor,0,-0.5F*scaleFactor).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
    }
}
