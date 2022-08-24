package com.finderfeed.solarforge.content.entities.projectiles.renderers;

import com.finderfeed.solarforge.content.entities.projectiles.SummoningProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class NullRenderer extends EntityRenderer<Entity> {
    public NullRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(Entity p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {

    }

    @Override
    public ResourceLocation getTextureLocation(Entity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
