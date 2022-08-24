package com.finderfeed.solarforge.content.entities.projectiles.renderers;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarforge.content.entities.projectiles.SolarFireballProjectile;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class SolarFireballRenderer extends EntityRenderer<SolarFireballProjectile> {

    public static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/particle/solar_strike_particle_small.png");

    public SolarFireballRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }


    @Override
    public void render(SolarFireballProjectile fireball, float p_114486_, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        matrices.pushPose();
        matrices.translate(0,0.125,0);
        matrices.scale(1.5f,1.5f,1.5f);
        Quaternion q = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();

        VertexConsumer vertex = src.getBuffer(SolarCraftRenderTypes.depthMaskedTextSeeThrough(LOC));
        matrices.mulPose(q);
        Matrix4f mat = matrices.last().pose();
        RenderingTools.coloredBasicVertex(mat,vertex,-0.5,-0.5,0,0,0,255,255,0,255);
        RenderingTools.coloredBasicVertex(mat,vertex,-0.5,0.5,0,0,1,255,255,0,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0.5, 0.5,0,1,1,255,255,0,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0.5,-0.5,0,1,0,255,255,0,255);

        matrices.popPose();


        super.render(fireball, p_114486_, pticks, matrices, src, light);
    }

    @Override
    public ResourceLocation getTextureLocation(SolarFireballProjectile projectile) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
