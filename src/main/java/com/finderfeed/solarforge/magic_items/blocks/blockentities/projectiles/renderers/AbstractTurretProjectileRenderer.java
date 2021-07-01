package com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.renderers;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.MortarProjectile;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class AbstractTurretProjectileRenderer extends EntityRenderer<AbstractTurretProjectile> {
    public final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/crossbow_shot_texture.png");
    public final ModelRenderer ray = new ModelRenderer(16,16,0,0);
    public AbstractTurretProjectileRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
        ray.addBox(-4,-4,-4,4,4,4);
        ray.setPos(2,2,2);
    }

    @Override
    public void render(AbstractTurretProjectile entity, float partialTicks, float idk, MatrixStack matrices, IRenderTypeBuffer buffer, int light) {
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
    public boolean shouldRender(AbstractTurretProjectile p_225626_1_, ClippingHelper p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractTurretProjectile p_110775_1_) {
        return new ResourceLocation("");
    }
}
