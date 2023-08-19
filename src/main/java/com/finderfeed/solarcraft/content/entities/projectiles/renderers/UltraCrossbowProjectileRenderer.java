package com.finderfeed.solarcraft.content.entities.projectiles.renderers;


import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.content.entities.projectiles.UltraCrossbowProjectile;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.ModelLayersRegistry;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;



public class UltraCrossbowProjectileRenderer extends EntityRenderer<UltraCrossbowProjectile> {
    public final ResourceLocation LOC = new ResourceLocation("solarcraft","textures/misc/ultra_crossbow_projectile_trail.png");
    public final ResourceLocation RAY = new ResourceLocation("solarcraft","textures/misc/crossbow_shot_texture.png");
    public final ModelPart ray;



    public static LayerDefinition createLayer(){
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("projectile", CubeListBuilder.create().addBox(-4,-4,-4,4,4,4), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition,16,16);
    }

    public UltraCrossbowProjectileRenderer(EntityRendererProvider.Context p_i46179_1_) {
        super(p_i46179_1_);
        ray = p_i46179_1_.bakeLayer(ModelLayersRegistry.SOLAR_CROSSBOW_LAYER);
    }
    @Override
    public void render(UltraCrossbowProjectile entity, float p_225623_2_, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light) {
        matrices.pushPose();

        RenderingTools.applyMovementMatrixRotations(matrices,entity.getDeltaMovement());

        ray.setPos(2,2,20);

        VertexConsumer vertex1 = buffer.getBuffer(SolarCraftRenderTypes.depthMaskedTextSeeThrough(RAY));
        ray.render(matrices,vertex1,light,light);
        matrices.popPose();
        matrices.pushPose();


        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),(entity.level.getGameTime()+partialTicks)*2%360));
        VertexConsumer vertex = buffer.getBuffer(SolarCraftRenderTypes.depthMaskedTextSeeThrough(LOC));
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
    public boolean shouldRender(UltraCrossbowProjectile p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(UltraCrossbowProjectile p_110775_1_) {
        return new ResourceLocation("");
    }
}
