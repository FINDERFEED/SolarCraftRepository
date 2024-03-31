package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.finderfeed.solarcraft.content.entities.DungeonRay;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.local_library.helpers.ShapesRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class DungeonRayRenderer extends EntityRenderer<DungeonRay> {
    public DungeonRayRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }


    @Override
    public void render(DungeonRay ray, float yaw, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        super.render(ray, yaw, pticks, matrices, src, light);

        matrices.pushPose();
        Direction direction = ray.getDirection();
        float length = ray.getRayLength() + 0.5f;
        Vec3i normal = direction.getNormal();
        Vec3 vn = new Vec3(normal.getX(),normal.getY(),normal.getZ());
        VertexConsumer v = src.getBuffer(RenderType.lightning());
        Matrix4f mat = matrices.last().pose();
        float w = 0.125f;
        RenderingTools.applyMovementMatrixRotations(matrices,vn);
        for (int i = 0; i < 4;i++) {
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),i * 90));
            v.vertex(mat, -w, 0, -w).color(1, 1, 0, 0.25f).endVertex();
            v.vertex(mat, w, 0, -w).color(1, 1, 0, 0.25f).endVertex();
            v.vertex(mat, w, length - 0.5f, -w).color(1, 1, 0, 0.25f).endVertex();
            v.vertex(mat, -w, length - 0.5f, -w).color(1, 1, 0, 0.25f).endVertex();
        }
        matrices.popPose();
        matrices.pushPose();
        matrices.translate(-vn.x*0.125,-vn.y*0.125,-vn.z*0.125);
        ShapesRenderer.renderCube(ShapesRenderer.POSITION_COLOR,v,matrices,0.125f,1,1,0,1, LightTexture.FULL_BRIGHT);
        matrices.translate(vn.x*(length - 0.25),vn.y*(length - 0.25),vn.z*(length - 0.25));
        ShapesRenderer.renderCube(ShapesRenderer.POSITION_COLOR,v,matrices,0.125f,1,1,0,1, LightTexture.FULL_BRIGHT);
        matrices.popPose();

        matrices.pushPose();
        RenderingTools.applyMovementMatrixRotations(matrices,vn);
        for (int i = 0; i < 4;i++) {

            matrices.pushPose();
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),90 * i));
            matrices.translate(0.125,0.125,0);
            ShapesRenderer.renderCube(ShapesRenderer.POSITION_COLOR, v, matrices, 0.125f, 1, 1, 0, 1, LightTexture.FULL_BRIGHT);
            matrices.translate(0,length - 0.75f,0);
            ShapesRenderer.renderCube(ShapesRenderer.POSITION_COLOR, v, matrices, 0.125f, 1, 1, 0, 1, LightTexture.FULL_BRIGHT);
            matrices.popPose();
        }
        matrices.popPose();

    }

    @Override
    public ResourceLocation getTextureLocation(DungeonRay p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public boolean shouldRender(DungeonRay p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }


}
