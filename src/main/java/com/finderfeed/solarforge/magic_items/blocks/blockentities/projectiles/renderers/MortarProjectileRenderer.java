package com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.renderers;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.MortarProjectile;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class MortarProjectileRenderer extends EntityRenderer<MortarProjectile> {
    public final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/crossbow_shot_texture.png");
    public final ModelRenderer ray = new ModelRenderer(16,16,0,0);
    public MortarProjectileRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
        ray.addBox(-16,-16,-16,16,16,16);
        ray.setPos(8,8,8);
    }

    @Override
    public void render(MortarProjectile p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {


        float time = (p_225623_1_.level.getGameTime() + p_225623_2_);
        p_225623_4_.mulPose(Vector3f.XN.rotationDegrees(time%360));
        p_225623_4_.mulPose(Vector3f.ZN.rotationDegrees(time%360));
        ray.render(p_225623_4_, p_225623_5_.getBuffer(RenderType.text(RAY)),p_225623_6_,p_225623_6_);

        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    @Override
    public boolean shouldRender(MortarProjectile p_225626_1_, ClippingHelper p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(MortarProjectile p_110775_1_) {
        return new ResourceLocation("");
    }
}
