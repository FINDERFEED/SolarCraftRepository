package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.entities.ShadowZombie;
import com.finderfeed.solarforge.entities.models.ShadowZombieModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShadowZombieRenderer extends MobRenderer<ShadowZombie, ShadowZombieModel> {
    public ShadowZombieRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,new ShadowZombieModel(ctx.bakeLayer(ModelLayers.ZOMBIE)), 0.0f);
        this.addLayer(new ShadowZombieModel.ShadowZombieLayer(this));
    }

    @Override
    public void render(ShadowZombie zombie, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
        if (!zombie.isDeadOrDying()) {
            super.render(zombie, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowZombie p_114482_) {
        return ShadowZombieModel.MODEL;
    }
}
