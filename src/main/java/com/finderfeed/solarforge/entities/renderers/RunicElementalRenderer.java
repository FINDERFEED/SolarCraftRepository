package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.entities.RunicElementalBoss;
import com.finderfeed.solarforge.entities.models.RunicElementalModel;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RunicElementalRenderer extends MobRenderer<RunicElementalBoss, RunicElementalModel> {
    private static final ResourceLocation SOLAR_STRIKE = new ResourceLocation("solarforge","textures/misc/solar_strike.png");
    public static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/runic_elemental.png");

    public RunicElementalRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new RunicElementalModel(ctx.bakeLayer(RunicElementalModel.LAYER_LOCATION)), 0.3f);
    }


    @Override
    public void render(RunicElementalBoss boss, float something, float pticks, PoseStack matrices, MultiBufferSource buffer, int light) {

        int tick = boss.getAttackTick();
        if (boss.getAttackType() == RunicElementalBoss.AttackType.MAGIC_MISSILES && tick > 15 && tick < 205){
            float time = RenderingTools.getTime(boss.level,pticks);
            matrices.pushPose();
            matrices.translate(0,1.75,0);

            matrices.mulPose(Vector3f.YN.rotationDegrees(Mth.lerp(pticks,boss.yHeadRotO,boss.yHeadRot)));
            matrices.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(pticks,boss.xRotO,boss.getXRot()) + 90));
            matrices.mulPose(Vector3f.YN.rotationDegrees(time*10));
            PoseStack.Pose entry = matrices.last();

            Matrix4f matrix = entry.pose();
            VertexConsumer vertex = buffer.getBuffer(RenderType.text(SOLAR_STRIKE));
            float mod = 1f;
            vertex.vertex(matrix,-0.5F*mod,1.75f,-0.5F*mod).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix,0.5F*mod,1.75f,-0.5F*mod).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix,0.5F*mod,1.75f,0.5F*mod).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix,-0.5F*mod,1.75f,0.5F*mod).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(matrix,-0.5F*mod,1.75f,0.5F*mod).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix,0.5F*mod,1.75f,0.5F*mod).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix,0.5F*mod,1.75f,-0.5F*mod).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix,-0.5F*mod,1.75f,-0.5F*mod).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            matrices.popPose();
        }
        matrices.pushPose();
        super.render(boss, something, pticks, matrices, buffer, light);
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(RunicElementalBoss p_114482_) {
        return LOC;
    }

    @Override
    public boolean shouldRender(RunicElementalBoss p_115468_, Frustum p_115469_, double p_115470_, double p_115471_, double p_115472_) {
        return true;
    }
}
