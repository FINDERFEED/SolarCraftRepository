package com.finderfeed.solarforge.magic_items.items.projectiles.renderers;

import com.finderfeed.solarforge.magic_items.items.projectiles.RandomBadEffectProjectile;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class RandomBadEffectProjectileRenderer extends EntityRenderer<RandomBadEffectProjectile> {


    protected RandomBadEffectProjectileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(RandomBadEffectProjectile p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
