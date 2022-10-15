package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.content.entities.not_alive.RipRayGenerator;
import com.finderfeed.solarcraft.events.other_events.OBJModels;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class RipRayGeneratorRender extends EntityRenderer<RipRayGenerator> {
    public RipRayGeneratorRender(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }


    @Override
    public void render(RipRayGenerator entity, float idk, float pticks, PoseStack matrices, MultiBufferSource buffer, int light) {
        super.render(entity, idk, pticks, matrices, buffer, light);

        float time = RenderingTools.getTime(entity.level,pticks);
        if (!entity.isDeploying()) {
            matrices.pushPose();
            matrices.translate(0, 0.25, 0);
            matrices.scale(0.5f, 0.5f, 0.5f);
            matrices.mulPose(Vector3f.YN.rotationDegrees(time % 360));
            RenderingTools.renderObjModel(OBJModels.RIP_RAY_GENERATOR, matrices, buffer, light, OverlayTexture.NO_OVERLAY, (m) -> {
            });
            matrices.popPose();

            matrices.pushPose();
            matrices.translate(-0.5, -0.25, -0.5);
            RenderingTools.renderRay(matrices, buffer, 0.25f, entity.maxAttackLengthForClient, Direction.DOWN, true, -1, pticks);
            matrices.popPose();

            matrices.pushPose();

            matrices.translate(0, 1, 0);
            Quaternion quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
            matrices.mulPose(quaternion);
            matrices.scale(0.5f, 0.3f, 0.5f);
            RenderingTools.renderHpBar(matrices, buffer, entity.getHealth() / entity.getMaxHealth());
            matrices.popPose();
        }

    }

    @Override
    public boolean shouldRender(RipRayGenerator p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(RipRayGenerator p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
