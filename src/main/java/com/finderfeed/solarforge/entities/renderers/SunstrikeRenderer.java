package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.entities.SunstrikeEntity;
import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SunstrikeRenderer extends EntityRenderer<SunstrikeEntity> {

    private static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/falling_star_projectile.png");
    private static final ResourceLocation SOLAR_STRIKE = new ResourceLocation(SolarForge.MOD_ID,"textures/misc/solar_strike.png");
    private static final ResourceLocation SOLAR_STRIKE_RAY = new ResourceLocation(SolarForge.MOD_ID,"textures/misc/solar_strike_ray.png");


    protected SunstrikeRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(SunstrikeEntity entity, float idk, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        matrices.pushPose();
        double percent = FinderfeedMathHelper.clamp(0,entity.tickCount/40,1);
        matrices.pushPose();
        VertexConsumer vertex = src.getBuffer(RenderType.text(LOC));

        matrices.translate(0,3*(1-percent),0);

        matrices.scale(1.5f,1.5f,1.5f);
        matrices.mulPose(Vector3f.XP.rotationDegrees(180));
        Matrix4f mat = matrices.last().pose();
        RenderingTools.basicVertex(mat,vertex,-0.25,-0.5,0,0,0);
        RenderingTools.basicVertex(mat,vertex,-0.25,0.5,0,1,0);
        RenderingTools.basicVertex(mat,vertex,0.25,0.5,0,1,1);
        RenderingTools.basicVertex(mat,vertex,0.25,-0.5,0,0,1);

        RenderingTools.basicVertex(mat,vertex,0.25,-0.5,0,0,1);
        RenderingTools.basicVertex(mat,vertex,0.25,0.5,0,1,1);
        RenderingTools.basicVertex(mat,vertex,-0.25,0.5,0,1,0);
        RenderingTools.basicVertex(mat,vertex,-0.25,-0.5,0,0,0);


        RenderingTools.basicVertex(mat,vertex,0,-0.5,-0.25,0,0);
        RenderingTools.basicVertex(mat,vertex,0,0.5,-0.25,1,0);
        RenderingTools.basicVertex(mat,vertex,0,0.5,0.25,1,1);
        RenderingTools.basicVertex(mat,vertex,0,-0.5,0.25,0,1);

        RenderingTools.basicVertex(mat,vertex,0,-0.5,0.25,0,1);
        RenderingTools.basicVertex(mat,vertex,0,0.5,0.25,1,1);
        RenderingTools.basicVertex(mat,vertex,0,0.5,-0.25,1,0);
        RenderingTools.basicVertex(mat,vertex,0,-0.5,-0.25,0,0);
        matrices.popPose();


        matrices.pushPose();
        float p = 1-(float)percent;
        matrices.scale(p,p,p);
        mat = matrices.last().pose();
        vertex =src.getBuffer(RenderType.text(SOLAR_STRIKE));
        int mod = 1;
        vertex.vertex(mat,-0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(mat,0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(mat,0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(mat,-0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(mat,-0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(mat,0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(mat,0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(mat,-0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        matrices.popPose();


        matrices.pushPose();
        VertexConsumer vertexray = src.getBuffer(RenderType.text(SOLAR_STRIKE_RAY));
        //if(entity.getLifeTicks() >= 55) {
        mat = matrices.last().pose();
        float modray = 3;
        float modray2 = 2;
        vertexray.vertex(mat, -1F*modray, 160*modray2, 0).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 1F*modray, 160*modray2, 0).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 1F*modray, 0, 0).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, -1F*modray, 0, 0).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertexray.vertex(mat, -1F*modray, 0, 0).color(255, 255, 255, 255)  .uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 1F*modray, 0, 0).color(255, 255, 255, 255)   .uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 1F*modray, 160, 0).color(255, 255, 255, 255) .uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, -1F*modray, 160, 0).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertexray.vertex(mat, 0, 160*modray2, -1f*modray).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, 160*modray2, 1f*modray).color(255, 255, 255, 255) .uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, 0, 1f*modray).color(255, 255, 255, 255)   .uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, 0, -1f*modray).color(255, 255, 255, 255)  .uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertexray.vertex(mat, 0, 0, -1f*modray).color(255, 255, 255, 255)  .uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, 0, 1f*modray).color(255, 255, 255, 255)   .uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, 160, 1f*modray).color(255, 255, 255, 255) .uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, 160, -1f*modray).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        matrices.popPose();
        matrices.popPose();
        super.render(entity, idk, pticks, matrices, src, light);
    }

    @Override
    public ResourceLocation getTextureLocation(SunstrikeEntity p_114482_) {
        return null;
    }
}
