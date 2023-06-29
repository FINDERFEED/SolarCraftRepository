package com.finderfeed.solarcraft.content.entities.projectiles.renderers;

import com.finderfeed.solarcraft.content.entities.projectiles.MagicMissile;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class FallingMagicMissileRenderer extends EntityRenderer<MagicMissile> {

    private static final ResourceLocation LOC = new ResourceLocation("solarcraft","textures/entities/falling_star_projectile.png");

    public FallingMagicMissileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(MagicMissile star, float idk, float partialTicks, PoseStack matrices, MultiBufferSource buf, int light) {
        matrices.pushPose();

        VertexConsumer vertex = buf.getBuffer(RenderType.text(LOC));
        RenderingTools.applyMovementMatrixRotations(matrices,star.getDeltaMovement().normalize());
        matrices.scale(1.5f,1.5f,1.5f);
        Matrix4f mat = matrices.last().pose();
        RenderingTools.basicVertex(mat,vertex,-0.25,-0.5,0,0,0);
        RenderingTools.basicVertex(mat,vertex,-0.25,0.5,0,1,0);
        RenderingTools.basicVertex(mat,vertex,0.25,0.5,0,1,1);
        RenderingTools.basicVertex(mat,vertex,0.25,-0.5,0,0,1);

        RenderingTools.basicVertex(mat,vertex,0.25,-0.5,0,0,1);
        RenderingTools.basicVertex(mat,vertex,0.25,0.5,0,1,1);
        RenderingTools.basicVertex(mat,vertex,-0.25,0.5,0,1,0);
        RenderingTools.basicVertex(mat,vertex,-0.25,-0.5,0,0,0);


        RenderingTools.basicVertex(mat,vertex,0,-0.5,-0.25,0,0);
        RenderingTools.basicVertex(mat,vertex,0,0.5,-0.25,1,0);
        RenderingTools.basicVertex(mat,vertex,0,0.5,0.25,1,1);
        RenderingTools.basicVertex(mat,vertex,0,-0.5,0.25,0,1);

        RenderingTools.basicVertex(mat,vertex,0,-0.5,0.25,0,1);
        RenderingTools.basicVertex(mat,vertex,0,0.5,0.25,1,1);
        RenderingTools.basicVertex(mat,vertex,0,0.5,-0.25,1,0);
        RenderingTools.basicVertex(mat,vertex,0,-0.5,-0.25,0,0);

        matrices.popPose();
        super.render(star, idk, partialTicks, matrices, buf, light);
    }

    @Override
    public ResourceLocation getTextureLocation(MagicMissile p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
