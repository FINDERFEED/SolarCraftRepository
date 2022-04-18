package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarforge.entities.EarthquakeEntity;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class EarthquakeRenderer extends EntityRenderer<EarthquakeEntity> {

    private static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/earthquake.png");
    private static final ResourceLocation LOC_EXPL = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/earthquake_activated.png");
    public EarthquakeRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(EarthquakeEntity entity, float hz, float pticks, PoseStack matrices, MultiBufferSource src, int light) {

        matrices.pushPose();
        VertexConsumer vertex = src.getBuffer(RenderType.text(LOC));
        matrices.translate(0,0.01,0);
        RenderingTools.applyMovementMatrixRotations(matrices,entity.getDir());
        Matrix4f m = matrices.last().pose();
        float percent = entity.getLength()/EarthquakeEntity.MAX_LENGTH;
        int yellow = (int)Math.round(FDMathHelper.clamp(0,230*((entity.tickCount + pticks) /EarthquakeEntity.ACTIVATION_TIME),230));
        RenderingTools.coloredBasicVertex(m,vertex,-0.5*(percent*2),0,0,0,1,255,255,255-yellow,255);
        RenderingTools.coloredBasicVertex(m,vertex,-0.5*(percent*2),EarthquakeEntity.MAX_LENGTH*percent,0,1/*percent*/,1,255,255,255-yellow,255);
        RenderingTools.coloredBasicVertex(m,vertex,0.5*(percent*2),EarthquakeEntity.MAX_LENGTH*percent,0,1/*percent*/,0,255,255,255-yellow,255);
        RenderingTools.coloredBasicVertex(m,vertex,0.5*(percent*2),0,0,0,0,255,255,255-yellow,255);

        RenderingTools.coloredBasicVertex(m,vertex,0.5*(percent*2),0,0,0,0,255,255,255-yellow,255);
        RenderingTools.coloredBasicVertex(m,vertex,0.5*(percent*2),EarthquakeEntity.MAX_LENGTH*percent,0,1/*percent*/,0,255,255,255-yellow,255);
        RenderingTools.coloredBasicVertex(m,vertex,-0.5*(percent*2),EarthquakeEntity.MAX_LENGTH*percent,0,1/*percent*/,1,255,255,255-yellow,255);
        RenderingTools.coloredBasicVertex(m,vertex,-0.5*(percent*2),0,0,0,1,255,255,255-yellow,255);
        matrices.popPose();
        int delta = EarthquakeEntity.ACTIVATION_TIME - 3;
        if (entity.tickCount > delta) {
            matrices.pushPose();
            vertex = src.getBuffer(SolarCraftRenderTypes.depthMaskedTextSeeThrough(LOC_EXPL));
            matrices.translate(0, 0.01, 0);
            RenderingTools.applyMovementMatrixRotations(matrices, entity.getDir());
            m = matrices.last().pose();
            int alpha = (int) Math.round(FDMathHelper.clamp(0, 255 * ((entity.tickCount - delta + pticks) / 3), 255));
            int secondDelta = EarthquakeEntity.ACTIVATION_TIME + 10;
            if (entity.tickCount > EarthquakeEntity.ACTIVATION_TIME){
                alpha = (int) Math.round(FDMathHelper.clamp(0, 255 * ((secondDelta - entity.tickCount - pticks) / 10), 255));
            }

//            vertex.vertex(m,0, 0, 0)            .color(255,255,0,alpha).endVertex();
//            vertex.vertex(m,0, 8 * percent, 0)  .color(255,255,0,alpha).endVertex();
//            vertex.vertex(m,0, 8 * percent,  -3).color(0,0,0,0).endVertex();
//            vertex.vertex(m,0, 0,  -3)          .color(0,0,0,0).endVertex();
//
//            vertex.vertex(m,0, 0,  -3)          .color(0,0,0,0).endVertex();
//            vertex.vertex(m,0, 8 * percent,  -3).color(0,0,0,0).endVertex();
//            vertex.vertex(m,0, 8 * percent, 0)  .color(255,255,0,alpha).endVertex();
//            vertex.vertex(m,0, 0, 0)            .color(255,255,0,alpha).endVertex();
            float t = alpha / 255f;
            RenderingTools.coloredBasicVertex(m, vertex, 0, 0, 0, 0, 1, 255, 255, 0, 217);
            RenderingTools.coloredBasicVertex(m, vertex, 0, EarthquakeEntity.MAX_LENGTH * percent, 0, 1 * percent, 1, 255, 255, 0, 217);
            RenderingTools.coloredBasicVertex(m, vertex,0, EarthquakeEntity.MAX_LENGTH * percent,  -3*t, 1 * percent, 0, 255, 255, 0, 217);
            RenderingTools.coloredBasicVertex(m, vertex,0, 0,  -3*t, 0, 0, 255, 255, 0, 255);

            RenderingTools.coloredBasicVertex(m, vertex,0, 0,  -3*t, 0, 0, 255, 255, 0, 217);
            RenderingTools.coloredBasicVertex(m, vertex,0, EarthquakeEntity.MAX_LENGTH * percent,  -3*t, 1 * percent, 0, 255, 255, 0, 217);
            RenderingTools.coloredBasicVertex(m, vertex, 0, EarthquakeEntity.MAX_LENGTH * percent, 0, 1 * percent, 1, 255, 255, 0, 217);
            RenderingTools.coloredBasicVertex(m, vertex, 0, 0, 0, 0, 1, 255, 255, 0, 217);
            matrices.popPose();
        }
        super.render(entity, hz, pticks, matrices, src, light);
    }

    @Override
    public ResourceLocation getTextureLocation(EarthquakeEntity e) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public boolean shouldRender(EarthquakeEntity p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }
}
