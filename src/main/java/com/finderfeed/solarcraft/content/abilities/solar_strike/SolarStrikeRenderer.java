package com.finderfeed.solarcraft.content.abilities.solar_strike;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.local_library.client.GlowShaderInit;
import com.finderfeed.solarcraft.local_library.client.delayed_renderer.DelayedRenderer;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Random;


public class SolarStrikeRenderer extends EntityRenderer<SolarStrikeEntity> {
    private static final ResourceLocation SOLAR_STRIKE = new ResourceLocation("solarcraft","textures/misc/solar_strike.png");
    private static final ResourceLocation SOLAR_STRIKE_RAY = new ResourceLocation("solarcraft","textures/misc/solar_strike_ray.png");
    public SolarStrikeRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }
    @Override
    public void render(SolarStrikeEntity entity, float p_225623_2_, float pticks, PoseStack matrices, MultiBufferSource buffer, int p_225623_6_) {
        if (entity.tickCount < SolarStrikeEntity.TIME_UNTIL_EXPLOSION) {
            this.renderRays(entity, matrices, DelayedRenderer.SRC, pticks);
        }else{
            this.renderStrike(entity,matrices,DelayedRenderer.SRC,pticks);
        }

    }

    private void renderStrike(SolarStrikeEntity entity,PoseStack matrices,MultiBufferSource src,float pticks){
        float alpha = (entity.tickCount - SolarStrikeEntity.TIME_UNTIL_EXPLOSION + pticks) / (float)SolarStrikeEntity.AFTER_EXPLOSION_TIME;
        alpha = 1 - (float)Math.pow(alpha,6);
        matrices.pushPose();
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),90));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.XN(),camera.getYRot()));
        for (int i = 0; i < 4;i++) {
            Random r = new Random(entity.level.getGameTime() + i * 94034);
            RenderingTools.Lightning2DRenderer.renderLightning(matrices, src, 20, 4f, 1f,
                    Vec3.ZERO, Vec3.ZERO.add(200, 0, 0), r, 1f, 1f, 0.6f + i * 0.1f,alpha);
        }
        matrices.popPose();

        matrices.pushPose();
        Matrix4f m = matrices.last().pose();
        float raySize = 3f;
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),camera.getYRot()));
        VertexConsumer vertex = src.getBuffer(SolarCraftRenderTypes.LIGHTNING_NO_CULL);
        vertex.vertex(m,-raySize,0,0f).color(0f,0f,0f,alpha).endVertex();
        vertex.vertex(m,-raySize,200,0f).color(0f,0f,0f,alpha).endVertex();
        vertex.vertex(m,0,200,0f).color(1f,1f,0f,alpha).endVertex();
        vertex.vertex(m,0,0,0f).color(1f,1f,0f,alpha).endVertex();
        vertex.vertex(m,raySize,0,0f).color(0f,0f,0f,alpha).endVertex();
        vertex.vertex(m,raySize,200,0f).color(0f,0f,0f,alpha).endVertex();
        vertex.vertex(m,0,200,0f).color(1f,1f,0f,alpha).endVertex();
        vertex.vertex(m,0,0,0f).color(1f,1f,0f,alpha).endVertex();
        matrices.popPose();
    }

    private void renderRays(SolarStrikeEntity entity,PoseStack matrices,MultiBufferSource src,float pticks){
        List<Vec3> rays = entity.getRayPositions(SolarStrikeEntity.RAYS_COUNT,pticks);
        float alpha = entity.getExplosionCompletionPercent(pticks);
        alpha = (float) Mth.clamp(Math.sqrt(alpha)*1.5f,0,1);
        Vec3 ePos = entity.position();
        VertexConsumer vertex = src.getBuffer(SolarCraftRenderTypes.LIGHTNING_NO_CULL);
        float raySize = 1f;
        float r = 1f;
        float g = 1f;
        float b = 0.1f;
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        for (int i = 0; i < rays.size();i++){
            Vec3 pos = rays.get(i);
            matrices.pushPose();
            Vec3 translation = pos.subtract(ePos);

            matrices.translate(translation.x,translation.y,translation.z);
            Matrix4f m = matrices.last().pose();
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),camera.getYRot()));


            vertex.vertex(m,-raySize,0,0).color(0,0,0,alpha).endVertex();
            vertex.vertex(m,0,0,0).color(r,g,b,alpha).endVertex();
            vertex.vertex(m,0,200,0).color(r,g,b,alpha).endVertex();
            vertex.vertex(m,-raySize,200,0).color(0,0,0,alpha).endVertex();
            vertex.vertex(m,raySize,0,0).color(0,0,0,alpha).endVertex();
            vertex.vertex(m,0,0,0).color(r,g,b,alpha).endVertex();
            vertex.vertex(m,0,200,0).color(r,g,b,alpha).endVertex();
            vertex.vertex(m,raySize,200,0).color(0,0,0,alpha).endVertex();

            matrices.popPose();

            matrices.pushPose();
            Random rand = new Random(entity.level.getGameTime() + i * 2343L);
            matrices.translate(translation.x,translation.y,translation.z);
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),90));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.XN(),camera.getYRot()));
            RenderingTools.Lightning2DRenderer.renderLightning(matrices, src, 20, 2.5f, 1f,
                    Vec3.ZERO, Vec3.ZERO.add(200, 0, 0),rand,1f, 1f, 0.6f,alpha);
            matrices.popPose();
        }
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
