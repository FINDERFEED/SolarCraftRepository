package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.DungeonRayController;
import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.DungeonRayHandler;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
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

        var handlers = controller.getHandlers();
        for (int i = 0;i < handlers.size();i++){
            this.renderHandler(handlers.get(i),matrices,v,i == controller.getCurrentSelectedHandlerId());
        }


        matrices.popPose();
    }

    private void renderHandler(DungeonRayHandler handler,PoseStack matrices,VertexConsumer v,boolean selected){
        var list = handler.movePositionOffsets;
        for (int i = 0; i < list.size();i++){
            float r = 1,g = 1,b = 1;
            if (selected) {
                if (i == 0) {
                    r = 0;
                    g = 1;
                    b = 0;
                } else if (i == list.size() - 1) {
                    r = 1;
                    g = 0;
                    b = 0;
                }
            }
            Vec3 c = list.get(i).getCenter().subtract(0.5,0.5,0.5);
            this.renderBox(matrices,v,c,r,g,b,1);
            if (i < list.size() - 1){
                Vec3 next = list.get(i+1).getCenter().subtract(0.5,0.5,0.5);
                this.renderLine(matrices,v,c,next,1,1,1,1);
            }
        }

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
    @Override
    public boolean shouldRender(DungeonRayController p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }
}
