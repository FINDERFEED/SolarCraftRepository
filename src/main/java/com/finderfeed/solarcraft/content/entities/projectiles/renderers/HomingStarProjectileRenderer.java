package com.finderfeed.solarcraft.content.entities.projectiles.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.entities.projectiles.HomingStarProjectile;
import com.finderfeed.solarcraft.content.entities.renderers.SingleQuadRenderer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class HomingStarProjectileRenderer extends SingleQuadRenderer<HomingStarProjectile> {

    public static final ResourceLocation LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/entities/projectiles/homing_star.png");
    public HomingStarProjectileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.r = 125/255f;
        this.g = 8/255f;
        this.b = 255/255f;
    }

    @Override
    public VertexConsumer getRenderType(MultiBufferSource source) {
        return source.getBuffer(RenderType.eyes(LOCATION));
    }

    @Override
    public float quadSize() {
        return 0.4f;
    }

    @Override
    public ResourceLocation getTextureLocation(HomingStarProjectile p_114482_) {
        return LOCATION;
    }
}
