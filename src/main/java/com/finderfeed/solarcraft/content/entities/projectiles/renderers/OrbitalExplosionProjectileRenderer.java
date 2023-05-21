package com.finderfeed.solarcraft.content.entities.projectiles.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.content.entities.projectiles.OrbitalExplosionProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class OrbitalExplosionProjectileRenderer extends EntityRenderer<OrbitalExplosionProjectile> {

    public static final ResourceLocation LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/particle/spark_three.png");

    public OrbitalExplosionProjectileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(OrbitalExplosionProjectile entity, float p_114486_, float pTicks, PoseStack matrices, MultiBufferSource src, int smth) {
        matrices.pushPose();
        VertexConsumer vertex = src.getBuffer(SolarCraftRenderTypes.depthMaskedTextSeeThrough(LOCATION));
        Quaternion quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        matrices.mulPose(quaternion);


        Matrix4f m = matrices.last().pose();

        vertex.vertex(m,-5,5,0).color(1,1,0,1f).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(m,5,5,0).color(1,1,0,1f).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(m,5,-5,0).color(1,1,0,1f).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();
        vertex.vertex(m,-5,-5,0).color(1,1,0,1f).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).endVertex();

        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(OrbitalExplosionProjectile p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public boolean shouldRender(OrbitalExplosionProjectile p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }
}
