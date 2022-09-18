package com.finderfeed.solarforge.content.blocks.render;

import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.content.blocks.blockentities.SolarEnergyRepeaterBlockEntity;
import com.finderfeed.solarforge.misc_things.AbstractSolarCore;
import com.finderfeed.solarforge.misc_things.AbstractSolarNetworkRepeater;
import com.finderfeed.solarforge.misc_things.IEnergyUser;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import com.mojang.math.Matrix4f;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;

public class SolarRepeaterRenderer implements BlockEntityRenderer<SolarEnergyRepeaterBlockEntity> {
    public final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/ray_into_sky.png");

    public SolarRepeaterRenderer(BlockEntityRendererProvider.Context ctx) {


    }

    @Override
    public void render(SolarEnergyRepeaterBlockEntity entity, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light1, int light2) {

        if (!entity.connectedTo.equals(new BlockPos(0,-100,0))) {
            if (entity.getLevel().getBlockEntity(entity.connectedTo) instanceof AbstractSolarNetworkRepeater ||
                    entity.getLevel().getBlockEntity(entity.connectedTo) instanceof AbstractSolarCore || (entity.getLevel().getBlockEntity(entity.connectedTo) instanceof IEnergyUser
                    && ((IEnergyUser)entity.getLevel().getBlockEntity(entity.connectedTo)).requriesEnergy())) {

            matrices.translate(0.5f, 0.5f, 0.5f);
            Vec3 parentPos = new Vec3(entity.getBlockPos().getX() - 0.5d, entity.getBlockPos().getY() + 0.5d, entity.getBlockPos().getZ() - 0.5d);
            Vec3 childPos = new Vec3(entity.connectedTo.getX() - 0.5d, entity.connectedTo.getY() + 0.5d, entity.connectedTo.getZ() - 0.5d);
            Vec3 vector = new Vec3((childPos.x - parentPos.x), (childPos.y - parentPos.y), (childPos.z - parentPos.z));
            Vec3 horizontalVector = new Vec3(childPos.x - parentPos.x, 0, childPos.z - parentPos.z);
            Vec3 verticalVector = new Vec3(0, childPos.y - parentPos.y, 0).normalize();

            if (horizontalVector.x >= 0) {
                matrices.mulPose(Vector3f.YN.rotationDegrees((float) Math.toDegrees(Math.acos(-horizontalVector.normalize().z))));
            } else {
                matrices.mulPose(Vector3f.YN.rotationDegrees(180 + (float) Math.toDegrees(Math.acos(horizontalVector.normalize().z))));
            }

            matrices.mulPose(Vector3f.XN.rotationDegrees((float) Math.toDegrees(Math.acos(vector.normalize().y))));


            float percent = (float) (Helpers.getGipotenuza(Helpers.getGipotenuza(vector.x, vector.z), vector.y));

            VertexConsumer vertex = buffer.getBuffer(RenderType.text(RAY));
            Matrix4f matrix = matrices.last().pose();
            float mod = 0.15f;

            //1
            vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            //2
            vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            //3
            vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            //4
            vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        }
        }

    }

    @Override
    public boolean shouldRenderOffScreen(SolarEnergyRepeaterBlockEntity p_188185_1_) {
        return true;
    }


}
