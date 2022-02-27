package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.entities.CrystalBossEntity;
import com.finderfeed.solarforge.entities.RunicElementalBoss;
import com.finderfeed.solarforge.entities.models.RunicElementalModel;
import com.finderfeed.solarforge.events.other_events.OBJModels;
import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.local_library.other.EaseIn;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class RunicElementalRenderer extends MobRenderer<RunicElementalBoss, RunicElementalModel> {
    private static final ResourceLocation SOLAR_STRIKE = new ResourceLocation("solarforge","textures/misc/solar_strike.png");
    public static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/runic_elemental.png");
    public static final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/shielding_ray.png");
    private static final RenderType BEAM = RenderType.text(RAY);
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
        } else if (boss.getAttackType() == RunicElementalBoss.AttackType.VARTH_DADER && tick > 15 && tick < 105) {
            Entity entity = boss.level.getEntity(boss.getVarthDaderTarget());
            matrices.pushPose();
            if (entity != null) {
                matrices.translate(0,0.3,0);
                renderRay(matrices, buffer.getBuffer(BEAM), RenderingTools.getTime(boss.level, pticks) / 60, boss.position().add(0, 1.75, 0),
                        entity.position().add(0, entity.getEyeHeight(entity.getPose()) * 0.8, 0), boss);
            }
            matrices.popPose();
        }

        if (boss.getEntityData().get(RunicElementalBoss.UPDATE_PUSH_WAVE_TICKER) == 1){
            float percent = 1-(float)(boss.getOrCreateAnimationValue("pushwave",new EaseIn(0,8,8)).getValue() + pticks)/8;
            matrices.pushPose();
            matrices.translate(0,1.5,0);
            matrices.scale(2,2,2);
            matrices.scale(1-percent,1-percent,1-percent);
            RenderingTools.renderObjModel(OBJModels.GET_OFF_MEEE,matrices,buffer,light,OverlayTexture.NO_OVERLAY,(d)->{});

            matrices.popPose();
        }
        matrices.pushPose();
        super.render(boss, something, pticks, matrices, buffer, light);
        matrices.popPose();
    }


    private void renderRay(PoseStack matrices, VertexConsumer vertex, float time, Vec3 firstPos, Vec3 secondPos, RunicElementalBoss boss){
        Vec3 direction = secondPos.subtract(firstPos);
        Vec3 yes = direction.normalize();
        double angleY = Math.toDegrees(Math.atan2(yes.x,yes.z));
        double angleX = Math.toDegrees(Math.atan2(Math.sqrt(yes.x*yes.x + yes.z*yes.z),yes.y) );
        matrices.pushPose();
        matrices.translate(0,boss.getBbHeight()/2,0);
        matrices.mulPose(Vector3f.YP.rotationDegrees((float)angleY));
        matrices.mulPose(Vector3f.XP.rotationDegrees((float)angleX));
        double length = direction.length();
        double factor = length/16;

        Matrix4f mat = matrices.last().pose();

        for (int i = 0; i < 6;i++){
            double[] xz = FinderfeedMathHelper.polarToCartesian(0.25f,Math.toRadians(i*60));
            double[] xz2 = FinderfeedMathHelper.polarToCartesian(0.25f,Math.toRadians(i*60+60));
            RenderingTools.basicVertex(mat,vertex,xz[0],0,xz[1],0,(time%2)*(float)factor);
            RenderingTools.basicVertex(mat,vertex,xz2[0],0,xz2[1],1,(time%2)*(float)factor);
            RenderingTools.basicVertex(mat,vertex,xz2[0],length,xz2[1],1,(1+time%2)*(float)factor);
            RenderingTools.basicVertex(mat,vertex,xz[0],length,xz[1],0,(1+time%2)*(float)factor);

            RenderingTools.basicVertex(mat,vertex,xz[0],length,xz[1],0,(1+time%2)*(float)factor);
            RenderingTools.basicVertex(mat,vertex,xz2[0],length,xz2[1],1,(1+time%2)*(float)factor);
            RenderingTools.basicVertex(mat,vertex,xz2[0],0,xz2[1],1,(time%2)*(float)factor);
            RenderingTools.basicVertex(mat,vertex,xz[0],0,xz[1],0,(time%2)*(float)factor);

        }
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
