package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.entities.RunicElementalBoss;
import com.finderfeed.solarforge.entities.models.RunicElementalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RunicElementalRenderer extends MobRenderer<RunicElementalBoss, RunicElementalModel> {

    public static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/entities/runic_elemental.png");

    public RunicElementalRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new RunicElementalModel(ctx.bakeLayer(RunicElementalModel.LAYER_LOCATION)), 0.3f);
    }


    @Override
    public void render(RunicElementalBoss boss, float something, float pticks, PoseStack matrices, MultiBufferSource buffer, int light) {
        super.render(boss, something, pticks, matrices, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(RunicElementalBoss p_114482_) {
        return LOC;
    }
}
