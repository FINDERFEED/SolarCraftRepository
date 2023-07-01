package com.finderfeed.solarcraft.content.entities.projectiles.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.entities.projectiles.ThrownLightProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ThrownLightProjectileRenderer extends EntityRenderer<ThrownLightProjectile> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(SolarCraft.MOD_ID,"textures/particle/solar_strike_particle.png");

    public ThrownLightProjectileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(ThrownLightProjectile e, float idk, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        super.render(e, idk, pticks, matrices, src, light);
        VertexConsumer vertex = src.getBuffer(RenderType.text(TEXTURE));
        matrices.pushPose();
        Quaternion quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        matrices.translate(0,0.125,0);
        matrices.mulPose(quaternion);
        Matrix4f m = matrices.last().pose();

        vertex.vertex(m, -0.5f,0.5f,0).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(m,  0.5f,0.5f,0).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(m,  0.5f,-0.5f,0).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(m,  -0.5f,-0.5f,0).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();


        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownLightProjectile p_114482_) {
        return TEXTURE;
    }
}
