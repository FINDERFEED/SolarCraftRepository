package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.DungeonRayController;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class DungeonRayControllerRenderer extends EntityRenderer<DungeonRayController> {
    public DungeonRayControllerRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(DungeonRayController controller, float yaw, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        if (DungeonRayController.DEBUG){
            this.renderDebug(controller,matrices,src,pticks);
        }

    }

    private void renderDebug(DungeonRayController controller,PoseStack matrices,MultiBufferSource src,float pticks){
        matrices.pushPose();
        VertexConsumer v = src.getBuffer(RenderType.lines());



        matrices.popPose();
    }

    private void renderBox(PoseStack matrices,VertexConsumer v,Vec3 center,float r,float g,float b,float a){
        matrices.pushPose();
        matrices.translate(center.x,center.y,center.z);
        for (int i = 0; i < 4;i++) {
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),90));
            Matrix4f mat = matrices.last().pose();
            Matrix3f normal = matrices.last().normal();
            v.vertex(mat,-0.5f, -0.5f, -0.5f).color(r, g, b, a).normal(normal,1, 0, 0).endVertex();
            v.vertex(mat,0.5f, -0.5f, -0.5f).color(r, g, b, a).normal(normal,-1, 0, 0).endVertex();

            v.vertex(mat,-0.5f, 0.5f, -0.5f).color(r, g, b, a).normal(normal,1, 0, 0).endVertex();
            v.vertex(mat,0.5f, 0.5f, -0.5f).color(r, g, b, a).normal(normal,-1, 0, 0).endVertex();

            v.vertex(mat,-0.5f,-0.5f,-0.5f).color(r,g,b,a).normal(0,1,0).endVertex();
            v.vertex(mat,-0.5f,0.5f,-0.5f).color(r,g,b,a).normal(0,-1,0).endVertex();
        }
        matrices.popPose();
    }

    private void renderLine(PoseStack matrices,VertexConsumer v,Vec3 initPoint,Vec3 endPoint,float r,float g,float b,float a){
        Vec3 between = endPoint.subtract(initPoint);
        matrices.pushPose();
        matrices.translate(initPoint.x,initPoint.y,initPoint.z);

        Matrix4f mat = matrices.last().pose();
        v.vertex(mat,0,0,0).color(r,g,b,a).normal((float)between.x,(float)between.y,(float)between.z).endVertex();
        v.vertex(mat,(float)between.x,(float)between.y,(float)between.z).color(r,g,b,a).normal(-(float)between.x,-(float)between.y,-(float)between.z).endVertex();


        matrices.popPose();
    }



    @Override
    public ResourceLocation getTextureLocation(DungeonRayController p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
