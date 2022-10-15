package com.finderfeed.solarcraft.content.entities.projectiles.renderers;

import com.finderfeed.solarcraft.content.entities.projectiles.RunicWarriorSummoningRocket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class RunicWarriorSummoningRocketRenderer extends EntityRenderer<RunicWarriorSummoningRocket> {
    public RunicWarriorSummoningRocketRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }


    @Override
    public void render(RunicWarriorSummoningRocket rocket, float hz, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        super.render(rocket, hz, pticks, matrices, src, light);
    }

    @Override
    public ResourceLocation getTextureLocation(RunicWarriorSummoningRocket p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
