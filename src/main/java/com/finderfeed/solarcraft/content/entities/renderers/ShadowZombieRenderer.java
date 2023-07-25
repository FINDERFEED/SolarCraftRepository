package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.content.entities.ShadowZombie;
import com.finderfeed.solarcraft.content.entities.models.ShadowZombieModel;
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
    public void render(ShadowZombie zombie, float p_115456_, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        if (!zombie.isDeadOrDying()) {
                super.render(zombie, p_115456_, pticks, matrices, src, light);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowZombie p_114482_) {
        return ShadowZombieModel.MODEL;
    }
}
