package com.finderfeed.solarforge.SolarAbilities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

import ResourceLocation;

public class MeteoriteProjectileRenderer extends EntityRenderer<MeteoriteProjectile> {
    public ResourceLocation METEORITE = new ResourceLocation("solarforge","textures/misc/solar_meteorite.png");
    public ModelPart meteorite = new ModelPart(64,64,0,0);

    public MeteoriteProjectileRenderer(EntityRenderDispatcher p_i46179_1_) {
        super(p_i46179_1_);
        meteorite.addBox(-32,-32,-32,64,64,64,1);
    }
    @Override
    public void render(MeteoriteProjectile entity, float p_225623_2_, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light) {
    matrices.pushPose();
    float time = (entity.level.getGameTime() + partialTicks)*2;
    matrices.mulPose(Vector3f.XN.rotationDegrees(time%360));
    matrices.mulPose(Vector3f.ZN.rotationDegrees(time%360));

        VertexConsumer vertex = buffer.getBuffer(RenderType.entityCutout(METEORITE));
    meteorite.render(matrices,vertex,light,light);
    matrices.popPose();
    }

    @Override
    public boolean shouldRender(MeteoriteProjectile p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(MeteoriteProjectile p_110775_1_) {
        return METEORITE;
    }
}
