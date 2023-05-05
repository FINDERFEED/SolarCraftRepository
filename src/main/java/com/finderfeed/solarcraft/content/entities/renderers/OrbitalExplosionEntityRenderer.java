package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.content.entities.OrbitalCannonExplosionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class OrbitalExplosionEntityRenderer extends EntityRenderer<OrbitalCannonExplosionEntity> {

    public OrbitalExplosionEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(OrbitalCannonExplosionEntity p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {

    }

    @Override
    public ResourceLocation getTextureLocation(OrbitalCannonExplosionEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
