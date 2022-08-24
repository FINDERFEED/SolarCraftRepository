package com.finderfeed.solarforge.content.items.solar_disc_gun;

import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.registries.items.SolarcraftItems;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.client.renderer.block.model.ItemTransforms;


import net.minecraft.resources.ResourceLocation;

import com.mojang.math.Vector3f;


public class SolarDiscProjectileRenderer extends EntityRenderer<SolarDiscProjectile> {
    public ResourceLocation SOLAR_DISC = new ResourceLocation("solarforge","textures/misc/solar_disc.png");


    public SolarDiscProjectileRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);

    }
    @Override
    public void render(SolarDiscProjectile entity, float p_225623_2_, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light) {
        matrices.pushPose();
        float time = (entity.level.getGameTime() + partialTicks)*30;
//        Vector3d vec = entity.getDeltaMovement().normalize();
//        matrices.translate(0,0.25,0);
//        matrices.mulPose(Vector3f.YP.rotationDegrees(time%360));
//        IVertexBuilder vertex = buffer.getBuffer(RenderType.text(SOLAR_DISC));
//        int mod = 1;
//        Matrix4f marix = matrices.last().pose();
//        vertex.vertex(marix,-0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//        vertex.vertex(marix,0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//        vertex.vertex(marix,0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//        vertex.vertex(marix,-0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//
//        vertex.vertex(marix,-0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//        vertex.vertex(marix,0.5F*mod,0,0.5F*mod).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//        vertex.vertex(marix,0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
//        vertex.vertex(marix,-0.5F*mod,0,-0.5F*mod).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        //matrices.mulPose(Vector3f.YN.rotationDegrees(time));
        //matrices.mulPose(Vector3f.XP.rotationDegrees(90));
        //matrices.mulPose(Vector3f.ZP.rotationDegrees(time));
        //(float) Math.toDegrees(Math.acos(entity.getDeltaMovement().normalize().x))
//        System.out.println(Math.toDegrees(Math.acos(entity.getDeltaMovement().normalize().x)));


//        matrices.mulPose(Vector3f.YN.rotationDegrees(90));
//        matrices.mulPose(Vector3f.YP.rotationDegrees((float) -Math.toDegrees(Math.atan(entity.getDeltaMovement().normalize().z/entity.getDeltaMovement().normalize().x))));
//
//        if (entity.getDeltaMovement().normalize().x >= 0) {
//            matrices.mulPose(Vector3f.XN.rotationDegrees((float) Math.toDegrees(Math.acos(entity.getDeltaMovement().normalize().y))));
//        }else{
//            matrices.mulPose(Vector3f.XP.rotationDegrees((float) Math.toDegrees(Math.acos(entity.getDeltaMovement().normalize().y))));
//        }
//        matrices.mulPose(Vector3f.ZN.rotationDegrees(time));
        RenderingTools.applyMovementMatrixRotations(matrices,entity.getDeltaMovement());
        matrices.mulPose(Vector3f.ZP.rotationDegrees(time % 360));

        Minecraft.getInstance().getItemRenderer().render(SolarcraftItems.SOLAR_DISC.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED,false,
                matrices,buffer,light,getPackedLightCoords(entity,light),Minecraft.getInstance().getItemRenderer().getModel(SolarcraftItems.SOLAR_DISC.get().getDefaultInstance(),null,null,0));

        matrices.popPose();
    }

    @Override
    public boolean shouldRender(SolarDiscProjectile p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(SolarDiscProjectile p_110775_1_) {
        return SOLAR_DISC;
    }


}
