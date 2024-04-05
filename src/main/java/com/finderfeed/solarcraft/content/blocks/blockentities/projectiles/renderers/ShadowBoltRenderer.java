package com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.ShadowBolt;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class ShadowBoltRenderer extends EntityRenderer<ShadowBolt> {

    public static final ResourceLocation LOC = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/shadow_bolt.png");

    public ShadowBoltRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }


    @Override
    public void render(ShadowBolt bolt, float idk, float pticks, PoseStack matrices, MultiBufferSource buffer, int light) {
        super.render(bolt, idk, pticks, matrices, buffer, light);
        matrices.pushPose();

        VertexConsumer vertex = buffer.getBuffer(RenderType.text(LOC));
        RenderingTools.applyMovementMatrixRotations(matrices,bolt.getDeltaMovement().normalize());
//        matrices.mulPose(Vector3f.YP.rotationDegrees(RenderingTools.getTime(bolt.level,pticks)*20 % 360));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),RenderingTools.getTime(bolt.level,pticks)*20 % 360));
        Matrix4f mat = matrices.last().pose();
        int rCol = 70;
        int gCol = 0;
        int bCol = 255;
        RenderingTools.coloredBasicVertex(mat,vertex,-0.25,-0.5,0.001,0,0,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,-0.25,0.5,0.001,1,0,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0.25,0.5,0.001,1,1,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0.25,-0.5,0.001,0,1,rCol,gCol,bCol,255);

        RenderingTools.coloredBasicVertex(mat,vertex,0.25,-0.5,0,0,1,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0.25,0.5,0,1,1,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,-0.25,0.5,0,1,0,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,-0.25,-0.5,0,0,0,rCol,gCol,bCol,255);


        RenderingTools.coloredBasicVertex(mat,vertex,0.001,-0.5,-0.25,0,0,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0.001,0.5,-0.25,1,0,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0.001,0.5,0.25,1,1,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0.001,-0.5,0.25,0,1,rCol,gCol,bCol,255);

        RenderingTools.coloredBasicVertex(mat,vertex,0,-0.5,0.25,0,1,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0,0.5,0.25,1,1,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0,0.5,-0.25,1,0,rCol,gCol,bCol,255);
        RenderingTools.coloredBasicVertex(mat,vertex,0,-0.5,-0.25,0,0,rCol,gCol,bCol,255);

        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowBolt p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
