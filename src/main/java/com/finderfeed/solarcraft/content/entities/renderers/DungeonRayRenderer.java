package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.finderfeed.solarcraft.content.entities.DungeonRay;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
        Direction direction = ray.getDirection();
        float length = ray.getRayLength();
        Vec3i normal = direction.getNormal();
        Vec3 vn = new Vec3(normal.getX(),normal.getY(),normal.getZ());
        VertexConsumer v = src.getBuffer(SCRenderTypes.LIGHTING_NO_CULL);
        Matrix4f mat = matrices.last().pose();
        float w = 0.125f;
        RenderingTools.applyMovementMatrixRotations(matrices,vn);
        for (int i = 0; i < 4;i++) {
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),i * 90));
            v.vertex(mat, -w, 0, -w).color(1, 1, 0, 0.5f).endVertex();
            v.vertex(mat, w, 0, -w).color(1, 1, 0, 0.5f).endVertex();
            v.vertex(mat, w, length, -w).color(1, 1, 0, 0.5f).endVertex();
            v.vertex(mat, -w, length, -w).color(1, 1, 0, 0.5f).endVertex();
        }

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
