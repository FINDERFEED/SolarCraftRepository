package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.content.entities.not_alive.SunstrikeEntity;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SunstrikeRenderer extends EntityRenderer<SunstrikeEntity> {

    private static final ResourceLocation LOC = new ResourceLocation(SolarCraft.MOD_ID,"textures/entities/falling_star_projectile.png");
    private static final ResourceLocation SOLAR_STRIKE = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/solar_strike.png");
    private static final ResourceLocation SOLAR_STRIKE_RAY = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/solar_strike_ray.png");


    public SunstrikeRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(SunstrikeEntity entity, float idk, float pticks, PoseStack matrices, MultiBufferSource src, int light) {

        matrices.pushPose();
        matrices.translate(0,0.001,0);
        matrices.mulPose(Vector3f.YP.rotationDegrees(RenderingTools.getTime(entity.level,pticks)*15));
        double percent = FDMathHelper.clamp(0,(entity.tickCount + pticks)/(float)SunstrikeEntity.FALLING_TIME,1);
        matrices.pushPose();
        VertexConsumer vertex = src.getBuffer(RenderType.text(LOC));
        float h = 5;
        matrices.translate(0,h*(1-percent) + 0.7f,0);

        matrices.scale(1.4f,1.4f,1.4f);
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
        float p = (float)percent;
        matrices.scale(p,p,p);
        mat = matrices.last().pose();
        vertex =src.getBuffer(RenderType.text(SOLAR_STRIKE));
        int mod =2;
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
        VertexConsumer vertexray = src.getBuffer(SolarCraftRenderTypes.depthMaskedTextSeeThrough(SOLAR_STRIKE_RAY));
        mat = matrices.last().pose();
        float modray = 0.125f;
        vertexray.vertex(mat, -1F*modray, h*(float)(1-percent), 0).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 1F*modray, h*(float)(1-percent), 0).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 1F*modray, 0, 0).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, -1F*modray, 0, 0).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertexray.vertex(mat, -1F*modray, 0, 0).color(255, 255, 255, 255)  .uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 1F*modray, 0, 0).color(255, 255, 255, 255)   .uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 1F*modray, h*(float)(1-percent), 0).color(255, 255, 255, 255) .uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, -1F*modray, h*(float)(1-percent), 0).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertexray.vertex(mat, 0, h*(float)(1-percent), -1f*modray).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, h*(float)(1-percent), 1f*modray).color(255, 255, 255, 255) .uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, 0, 1f*modray).color(255, 255, 255, 255)   .uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, 0, -1f*modray).color(255, 255, 255, 255)  .uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertexray.vertex(mat, 0, 0, -1f*modray).color(255, 255, 255, 255)  .uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, 0, 1f*modray).color(255, 255, 255, 255)   .uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, h*(float)(1-percent), 1f*modray).color(255, 255, 255, 255) .uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertexray.vertex(mat, 0, h*(float)(1-percent), -1f*modray).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        matrices.popPose();
        matrices.popPose();
        super.render(entity, idk, pticks, matrices, src, light);
    }

    @Override
    public boolean shouldRender(SunstrikeEntity p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(SunstrikeEntity p_114482_) {
        return null;
    }
}
