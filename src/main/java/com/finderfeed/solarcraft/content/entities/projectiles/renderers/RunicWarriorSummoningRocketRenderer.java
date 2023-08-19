package com.finderfeed.solarcraft.content.entities.projectiles.renderers;

import com.finderfeed.solarcraft.content.entities.projectiles.RunicWarriorSummoningProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class RunicWarriorSummoningRocketRenderer extends EntityRenderer<RunicWarriorSummoningProjectile> {
    public RunicWarriorSummoningRocketRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }


    @Override
    public void render(RunicWarriorSummoningProjectile rocket, float hz, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        super.render(rocket, hz, pticks, matrices, src, light);
    }

    @Override
    public ResourceLocation getTextureLocation(RunicWarriorSummoningProjectile p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
