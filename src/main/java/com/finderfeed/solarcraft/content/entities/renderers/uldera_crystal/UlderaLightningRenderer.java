package com.finderfeed.solarcraft.content.entities.renderers.uldera_crystal;

import com.finderfeed.solarcraft.content.entities.uldera_crystal.UlderaLightningEntity;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class UlderaLightningRenderer extends EntityRenderer<UlderaLightningEntity> {
    public UlderaLightningRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }


    @Override
    public void render(UlderaLightningEntity lightning, float idk, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        if (lightning.tickCount < lightning.getLightningDelay()) return;
        matrices.pushPose();
        RenderingTools.Lightning3DRenderer.renderLightning3D(src,matrices,
                Vec3.ZERO,Vec3.ZERO.add(0,lightning.getHeight(),0),
                (int)(lightning.level.getGameTime()/3*4923)
                + (int)lightning.position().length()*4309,2,0.4f,lightning.getHeight()/30,
                120,40,186,100);
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(UlderaLightningEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public boolean shouldRender(UlderaLightningEntity lightning, Frustum frustum, double p_114493_, double p_114494_, double p_114495_) {
        double w = lightning.getHeight()/30;
        AABB box = new AABB(
                lightning.position().add(-w,0,-w),
                lightning.position().add(-w,lightning.getHeight(),-w)
        );
        return frustum.isVisible(box);
    }
}
