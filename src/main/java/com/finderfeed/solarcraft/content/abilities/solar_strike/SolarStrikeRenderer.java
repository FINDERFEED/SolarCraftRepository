package com.finderfeed.solarcraft.content.abilities.solar_strike;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.local_library.client.GlowShaderInit;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.List;


public class SolarStrikeRenderer extends EntityRenderer<SolarStrikeEntity> {
    private static final ResourceLocation SOLAR_STRIKE = new ResourceLocation("solarcraft","textures/misc/solar_strike.png");
    private static final ResourceLocation SOLAR_STRIKE_RAY = new ResourceLocation("solarcraft","textures/misc/solar_strike_ray.png");
    public SolarStrikeRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }
    @Override
    public void render(SolarStrikeEntity entity, float p_225623_2_, float pticks, PoseStack matrices, MultiBufferSource buffer, int p_225623_6_) {
        this.renderRays(entity,matrices, GlowShaderInit.TEST_SOURCE,pticks);

    }

    private void renderRays(SolarStrikeEntity entity,PoseStack matrices,MultiBufferSource src,float pticks){
        List<Vec3> rays = entity.getRayPositions(SolarStrikeEntity.RAYS_COUNT,pticks);
        Vec3 ePos = entity.position();
        VertexConsumer vertex = src.getBuffer(SolarCraftRenderTypes.myLightning());
        float raySize = 1f;
        float r = 1f;
        float g = 1f;
        float b = 0.1f;
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        for (Vec3 pos : rays){
            matrices.pushPose();
            Vec3 translation = pos.subtract(ePos);

            matrices.translate(translation.x,translation.y,translation.z);
            Matrix4f m = matrices.last().pose();
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),camera.getYRot()));


            vertex.vertex(m,-raySize,0,0).color(0,0,0,1).endVertex();
            vertex.vertex(m,0,0,0).color(r,g,b,1).endVertex();
            vertex.vertex(m,0,200,0).color(r,g,b,1).endVertex();
            vertex.vertex(m,-raySize,200,0).color(0,0,0,1).endVertex();
            vertex.vertex(m,raySize,0,0).color(0,0,0,1).endVertex();
            vertex.vertex(m,0,0,0).color(r,g,b,1).endVertex();
            vertex.vertex(m,0,200,0).color(r,g,b,1).endVertex();
            vertex.vertex(m,raySize,200,0).color(0,0,0,1).endVertex();

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
