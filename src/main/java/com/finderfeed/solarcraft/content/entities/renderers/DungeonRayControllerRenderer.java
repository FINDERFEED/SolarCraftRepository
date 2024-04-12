package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.DungeonRayController;
import com.finderfeed.solarcraft.content.entities.dungeon_ray_controller.DungeonRayHandler;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModel;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.local_library.helpers.ShapesRenderer;
import com.finderfeed.solarcraft.registries.SCBedrockModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class DungeonRayControllerRenderer extends EntityRenderer<DungeonRayController> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(SolarCraft.MOD_ID,"textures/entities/ray_controller.png");

    private FDModel model;

    public DungeonRayControllerRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.model = new FDModel(SCBedrockModels.RAY_CONTROLLER);
    }

    @Override
    public void render(DungeonRayController controller, float yaw, float pticks, PoseStack matrices, MultiBufferSource src, int light) {

        controller.getAnimationManager().getAsClientManager().applyAnimations(model,pticks);
        matrices.pushPose();
        matrices.translate(0,-0.5,0);
        model.render(matrices,src.getBuffer(RenderType.entityTranslucent(TEXTURE)),light, OverlayTexture.NO_OVERLAY,1,1,1,1);
        matrices.popPose();

        if (Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()){
            this.renderDebug(controller,matrices,src,pticks);
        }
        try {
            for (DungeonRayHandler handler : controller.getHandlers()) {
                if (handler.oldPos != null) {
                    Vec3 oldPos = handler.oldPos;
                    Vec3 current = handler.currentPosition;
                    Vec3 n = new Vec3(
                            Mth.lerp(pticks, oldPos.x, current.x),
                            Mth.lerp(pticks, oldPos.y, current.y),
                            Mth.lerp(pticks, oldPos.z, current.z)
                    );


                    matrices.pushPose();
                    matrices.translate(n.x, n.y, n.z);
                    this.renderRay(handler, matrices, src, pticks);
                    matrices.popPose();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void renderRay(DungeonRayHandler handler,PoseStack matrices,MultiBufferSource src,float pticks){
        matrices.pushPose();
        Direction direction = handler.rayDir;
        float length = handler.rayLength + 0.5f;
        Vec3i normal = direction.getNormal();
        Vec3 vn = new Vec3(normal.getX(),normal.getY(),normal.getZ());
        VertexConsumer v = src.getBuffer(SCRenderTypes.LIGHTNING_NO_CULL);
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
        v = src.getBuffer(RenderType.lightning());
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

        if (Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            matrices.pushPose();
            matrices.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(), 180));
            matrices.translate(0.7, 0, 0);
            matrices.scale(0.03f, 0.03f, -1);
            GuiGraphics graphics = new GuiGraphics(Minecraft.getInstance(), matrices, Minecraft.getInstance().renderBuffers().bufferSource());

            graphics.drawCenteredString(Minecraft.getInstance().font, "%.3f".formatted(handler.movespeed), 0, 0, 0xff1111);

            matrices.popPose();
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

            Vec3i dir = handler.rayDir.getNormal();
            this.renderLine(matrices,v,c,c.add(dir.getX()*2,dir.getY()*2,dir.getZ()*2),1f,1f,0f,1f);
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
