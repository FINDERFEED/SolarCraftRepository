package com.finderfeed.solarforge.magic_items.items.projectiles.renderers;

import com.finderfeed.solarforge.magic_items.items.projectiles.BlockBoomerangProjectile;
import com.finderfeed.solarforge.magic_items.items.projectiles.UltraCrossbowProjectile;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class UltraCrossbowProjectileRenderer extends EntityRenderer<UltraCrossbowProjectile> {
    public final ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/ultra_crossbow_projectile_trail.png");
    public final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/crossbow_shot_texture.png");
    public final ModelRenderer ray = new ModelRenderer(16,16,0,0);
    public UltraCrossbowProjectileRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
        ray.addBox(-4,-4,-4,4,4,4);
    }
    @Override
    public void render(UltraCrossbowProjectile entity, float p_225623_2_, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int light) {
        matrices.pushPose();

        float yaw = entity.getEntityData().get(entity.YAW);
        float pitch = entity.getEntityData().get(entity.PITCH);
        ray.setPos(2,2,20);
        matrices.mulPose(Vector3f.YN.rotationDegrees(yaw));
        matrices.mulPose(Vector3f.XN.rotationDegrees(-pitch));
        IVertexBuilder vertex1 = buffer.getBuffer(RenderType.textSeeThrough(RAY));
        ray.render(matrices,vertex1,light,light);
        matrices.popPose();
        matrices.pushPose();
        matrices.mulPose(Vector3f.YP.rotationDegrees(180));
        matrices.mulPose(Vector3f.YN.rotationDegrees(yaw));
        matrices.mulPose(Vector3f.XN.rotationDegrees(pitch));

        matrices.mulPose(Vector3f.ZP.rotationDegrees((entity.level.getGameTime()+partialTicks)*2%360 ));
        IVertexBuilder vertex = buffer.getBuffer(RenderType.textSeeThrough(LOC));
        Matrix4f matrix = matrices.last().pose();
        float mod = 1;
        vertex.vertex(matrix,-0.5F*mod,0,-1F*mod).color(255,255,40,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0.5F*mod,0,-1F*mod).color(255,255,40,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0.5F*mod,0,1F*mod).color(255,255,40,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,-0.5F*mod,0,1F*mod).color(255,255,40,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix,-0.5F*mod,0,1F*mod).color(255,255,40,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0.5F*mod,0,1F*mod).color(255,255,40,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0.5F*mod,0,-1F*mod).color(255,255,40,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,-0.5F*mod,0,-1F*mod).color(255,255,40,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        mod = -1;
        vertex.vertex(matrix,0,0.5f*mod,1f*mod).color(255,255,40,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0,-0.5f*mod,1f*mod).color(255,255,40,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0,-0.5f*mod,-1f*mod).color(255,255,40,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0,0.5f*mod,-1f*mod).color(255,255,40,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        vertex.vertex(matrix,0,0.5f*mod,-1f*mod).color(255,255,40,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0,-0.5f*mod,-1f*mod).color(255,255,40,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0,-0.5f*mod,1f*mod).color(255,255,40,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,0,0.5f*mod,1f*mod).color(255,255,40,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        matrices.popPose();

    }

    @Override
    public boolean shouldRender(UltraCrossbowProjectile p_225626_1_, ClippingHelper p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(UltraCrossbowProjectile p_110775_1_) {
        return new ResourceLocation("");
    }
}
