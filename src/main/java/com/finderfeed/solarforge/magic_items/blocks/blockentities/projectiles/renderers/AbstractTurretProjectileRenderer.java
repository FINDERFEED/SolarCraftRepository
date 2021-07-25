package com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.renderers;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

import ResourceLocation;

public class AbstractTurretProjectileRenderer extends EntityRenderer<AbstractTurretProjectile> {
    public final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/crossbow_shot_texture.png");
    public final ModelPart ray = new ModelPart(16,16,0,0);
    public AbstractTurretProjectileRenderer(EntityRenderDispatcher p_i46179_1_) {
        super(p_i46179_1_);
        ray.addBox(-4,-4,-4,4,4,4);
        ray.setPos(2,2,2);
    }

    @Override
    public void render(AbstractTurretProjectile entity, float partialTicks, float idk, PoseStack matrices, MultiBufferSource buffer, int light) {
        float time = (entity.level.getGameTime() + partialTicks);
//        matrices.mulPose(Vector3f.XN.rotationDegrees(time%360));
//        matrices.mulPose(Vector3f.ZN.rotationDegrees(time%360));
        matrices.pushPose();
        matrices.translate(0,0.12,0);
        ray.render(matrices, buffer.getBuffer(RenderType.text(RAY)),light,getPackedLightCoords(entity,idk));
        matrices.popPose();
        super.render(entity,partialTicks,idk,matrices,buffer,light);
    }

    @Override
    public boolean shouldRender(AbstractTurretProjectile p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractTurretProjectile p_110775_1_) {
        return new ResourceLocation("");
    }
}
