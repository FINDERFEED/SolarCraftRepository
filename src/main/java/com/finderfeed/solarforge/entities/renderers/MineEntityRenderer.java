package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.entities.MineEntityCrystalBoss;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class MineEntityRenderer extends EntityRenderer<MineEntityCrystalBoss> {

    public static final ResourceLocation MAIN = new ResourceLocation("solarforge","textures/entities/mine_entity.png");
    public static final ResourceLocation RAY = new ResourceLocation("solarforge","textures/entities/mine_entity_ray.png");


    public MineEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(MineEntityCrystalBoss mine, float idk, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light) {

        float percent = (float)mine.getEntityData().get(mine.TICKER)/60;
        float time = (mine.level.getGameTime() + partialTicks)*20 % 360;
        matrices.pushPose();
        matrices.mulPose(Vector3f.YP.rotationDegrees(time));
        matrices.translate(0,0.01,0);
        matrices.scale(percent,percent,percent);
        Matrix4f mat = matrices.last().pose();
        VertexConsumer vertex = buffer.getBuffer(RenderType.text(MAIN));

        RenderingTools.basicVertex(mat,vertex,-0.5,0,0.5,0,1);
        RenderingTools.basicVertex(mat,vertex,0.5,0,0.5,1,1);
        RenderingTools.basicVertex(mat,vertex,0.5,0,-0.5,1,0);
        RenderingTools.basicVertex(mat,vertex,-0.5,0,-0.5,0,0);

        RenderingTools.basicVertex(mat,vertex,-0.5,0,-0.5,0,0);
        RenderingTools.basicVertex(mat,vertex,0.5,0,-0.5,1,0);
        RenderingTools.basicVertex(mat,vertex,0.5,0,0.5,1,1);
        RenderingTools.basicVertex(mat,vertex,-0.5,0,0.5,0,1);

        vertex = buffer.getBuffer(RenderType.text(RAY));

        matrices.mulPose(Vector3f.ZN.rotationDegrees(180));
        matrices.translate(0,-1,0);
        RenderingTools.basicVertex(mat,vertex,-0.5,0,0,0,0);
        RenderingTools.basicVertex(mat,vertex,-0.5,1,0,0,1);
        RenderingTools.basicVertex(mat,vertex,0.5,1,0,1,1);
        RenderingTools.basicVertex(mat,vertex,0.5,0,0,1,0);

        RenderingTools.basicVertex(mat,vertex,0.5,0,0,1,0);
        RenderingTools.basicVertex(mat,vertex,0.5,1,0,1,1);
        RenderingTools.basicVertex(mat,vertex,-0.5,1,0,0,1);
        RenderingTools.basicVertex(mat,vertex,-0.5,0,0,0,0);

        RenderingTools.basicVertex(mat,vertex,0,0,-0.5,0,0);
        RenderingTools.basicVertex(mat,vertex,0,1,-0.5,0,1);
        RenderingTools.basicVertex(mat,vertex,0,1,0.5,1,1);
        RenderingTools.basicVertex(mat,vertex,0,0,0.5,1,0);

        RenderingTools.basicVertex(mat,vertex,0,0,0.5,1,0);
        RenderingTools.basicVertex(mat,vertex,0,1,0.5,1,1);
        RenderingTools.basicVertex(mat,vertex,0,1,-0.5,0,1);
        RenderingTools.basicVertex(mat,vertex,0,0,-0.5,0,0);

        matrices.popPose();
        super.render(mine, idk, partialTicks, matrices, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(MineEntityCrystalBoss p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
