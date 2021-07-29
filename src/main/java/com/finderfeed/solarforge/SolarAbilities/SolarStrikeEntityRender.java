package com.finderfeed.solarforge.SolarAbilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;



public class SolarStrikeEntityRender extends EntityRenderer<SolarStrikeEntity> {
    private static final ResourceLocation SOLAR_STRIKE = new ResourceLocation("solarforge","textures/misc/solar_strike.png");
    private static final ResourceLocation SOLAR_STRIKE_RAY = new ResourceLocation("solarforge","textures/misc/solar_strike_ray.png");
    public SolarStrikeEntityRender(EntityRendererProvider.Context ctx) {
        super(ctx);
    }
    @Override
    public void render(SolarStrikeEntity entity, float p_225623_2_, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int p_225623_6_) {
        super.render(entity,p_225623_2_,partialTicks,matrices,buffer,p_225623_6_);
        matrices.pushPose();
        matrices.translate(0, 0.1, 0);
        PoseStack.Pose ray_entry = matrices.last();

        if (entity.getLifeTicks() <= 40) {

            matrices.translate(0, 5 - ((float)entity.getLifeTicks() +partialTicks)/8, 0);
            matrices.scale(((float)entity.getLifeTicks() + partialTicks/1.8f)/40 ,0,((float)entity.getLifeTicks()+ partialTicks/1.8f)/40);
        }

        float time = (entity.level.getGameTime() + partialTicks)*10 % 360;
        matrices.mulPose(Vector3f.YP.rotationDegrees(time));

        PoseStack.Pose entry = matrices.last();
        Matrix4f matrix = entry.pose();
        VertexConsumer vertex = buffer.getBuffer(RenderType.text(SOLAR_STRIKE));
        int mod = 50;
        vertex.vertex(matrix,-0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,-0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix,-0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,-0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        VertexConsumer vertexray = buffer.getBuffer(RenderType.text(SOLAR_STRIKE_RAY));
        //if(entity.getLifeTicks() >= 55) {
            Matrix4f matrixray = ray_entry.pose();
            float modray = 3;
            float modray2 = 2;
            vertexray.vertex(matrixray, -1F*modray, 160*modray2, 0).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, 1F*modray, 160*modray2, 0).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, 1F*modray, 0, 0).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, -1F*modray, 0, 0).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertexray.vertex(matrixray, -1F*modray, 0, 0).color(255, 255, 255, 255)  .uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, 1F*modray, 0, 0).color(255, 255, 255, 255)   .uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, 1F*modray, 160, 0).color(255, 255, 255, 255) .uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, -1F*modray, 160, 0).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertexray.vertex(matrixray, 0, 160*modray2, -1f*modray).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, 0, 160*modray2, 1f*modray).color(255, 255, 255, 255) .uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, 0, 0, 1f*modray).color(255, 255, 255, 255)   .uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, 0, 0, -1f*modray).color(255, 255, 255, 255)  .uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertexray.vertex(matrixray, 0, 0, -1f*modray).color(255, 255, 255, 255)  .uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, 0, 0, 1f*modray).color(255, 255, 255, 255)   .uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, 0, 160, 1f*modray).color(255, 255, 255, 255) .uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertexray.vertex(matrixray, 0, 160, -1f*modray).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        //}
        matrices.popPose();
    }

    @Override
    public boolean shouldRender(SolarStrikeEntity p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }


    @Override
    public ResourceLocation getTextureLocation(SolarStrikeEntity p_110775_1_) {
        return SOLAR_STRIKE;
    }
}
