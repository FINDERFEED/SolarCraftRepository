package com.finderfeed.solarforge.content.abilities.meteorite;

import com.finderfeed.solarforge.registries.ModelLayersRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;


public class MeteoriteProjectileRenderer extends EntityRenderer<MeteoriteProjectile> {
    public ResourceLocation METEORITE = new ResourceLocation("solarforge","textures/misc/solar_meteorite.png");
    public MeteoriteModel meteorite;

    public MeteoriteProjectileRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);

        this.meteorite = new MeteoriteModel(ctx.bakeLayer(ModelLayersRegistry.METEORITE_LAYER));

    }
    @Override
    public void render(MeteoriteProjectile entity, float p_225623_2_, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light) {
    matrices.pushPose();


        float time = (entity.level.getGameTime() + partialTicks)*2;
        matrices.mulPose(Vector3f.XN.rotationDegrees(time%360));
        matrices.mulPose(Vector3f.ZN.rotationDegrees(time%360));

        VertexConsumer vertex = buffer.getBuffer(RenderType.entityCutout(METEORITE));
        meteorite.renderToBuffer(matrices,vertex,light, OverlayTexture.NO_OVERLAY,1,1,1,1);
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

