package com.finderfeed.solarforge.content.entities.projectiles.renderers;

import com.finderfeed.solarforge.content.entities.projectiles.CrystalBossAttackHoldingMissile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class HoldingMissileRenderer extends EntityRenderer<CrystalBossAttackHoldingMissile> {

    private final ResourceLocation MAIN = new ResourceLocation("solarforge:textures/entities/holding_missile.png");

    public HoldingMissileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(CrystalBossAttackHoldingMissile p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        renderProj(p_114488_,p_114489_,p_114487_);
        super.render(p_114485_, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
    }

    private void renderProj(PoseStack matrices,MultiBufferSource buffer,float partialTicks){
        matrices.pushPose();
        VertexConsumer vertex = buffer.getBuffer(RenderType.text(MAIN));
        matrices.translate(0,0.125,0);
        Quaternion quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        matrices.mulPose(quaternion);
        Matrix4f matrix = matrices.last().pose();
        vertex.vertex(matrix, -0.5f,0.5f,0).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  0.5f,0.5f,0).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  0.5f,-0.5f,0).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  -0.5f,-0.5f,0).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        matrices.popPose();


    }

    @Override
    public ResourceLocation getTextureLocation(CrystalBossAttackHoldingMissile p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
