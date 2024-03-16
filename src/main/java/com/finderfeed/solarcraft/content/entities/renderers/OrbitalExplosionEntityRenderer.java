package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarcraft.content.entities.OrbitalCannonExplosionEntity;
import com.finderfeed.solarcraft.events.other_events.OBJModels;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.client.model.data.ModelData;

import java.util.List;
import java.util.Map;

public class OrbitalExplosionEntityRenderer extends EntityRenderer<OrbitalCannonExplosionEntity> {

    public static final ResourceLocation LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/orbital_explosion.png");

    public OrbitalExplosionEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(OrbitalCannonExplosionEntity entity, float p_114486_, float partialTicks, PoseStack matrices, MultiBufferSource src, int light) {
        matrices.pushPose();
        int radius = entity.getEntityData().get(OrbitalCannonExplosionEntity.RADIUS)*2;
        int depth = entity.getEntityData().get(OrbitalCannonExplosionEntity.DEPTH)*2;
        matrices.scale(radius,depth,radius);

        RenderingTools.renderEntityObjModel(OBJModels.ORBITAL_EXPLOSION_SPHERE,
                matrices,src,1,1,1,LightTexture.FULL_BRIGHT,OverlayTexture.NO_OVERLAY);
        RenderType t = SCRenderTypes.ORBITAL_EXPLOSION_BLOOM.apply(TextureAtlas.LOCATION_BLOCKS);
        RenderingTools.renderEntityObjModel(OBJModels.ORBITAL_EXPLOSION_SPHERE,
                matrices,t,src.getBuffer(t),
                1,1,1,LightTexture.FULL_BRIGHT,OverlayTexture.NO_OVERLAY);

        matrices.popPose();

    }


    @Override
    public ResourceLocation getTextureLocation(OrbitalCannonExplosionEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public boolean shouldRender(OrbitalCannonExplosionEntity p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }
}
