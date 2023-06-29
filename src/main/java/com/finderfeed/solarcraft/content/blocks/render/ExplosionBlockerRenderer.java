package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.blocks.blockentities.ExplosionBlockerBlockEntity;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.TileEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class ExplosionBlockerRenderer extends TileEntityRenderer<ExplosionBlockerBlockEntity> {
    public static final ResourceLocation LOC = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/explosion_shield.png");
    public ExplosionBlockerRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ExplosionBlockerBlockEntity tile, float pticks, PoseStack matrices, MultiBufferSource bufferSource, int light, int overlay) {
        matrices.pushPose();

        if (tile.shouldRenderShield() && tile.getLevel() != null){
            VertexConsumer vertex = bufferSource.getBuffer(RenderType.text(LOC));
            float time = RenderingTools.getTime(tile.getLevel(),pticks)/4;
            int m = 20;
            for (int i = 0; i < m;i++){
                matrices.pushPose();
//                matrices.mulPose(Vector3f.YP.rotationDegrees(360f/m * i + (time % 360) ));
                matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),360f/m * i + (time % 360) ));
                Matrix4f mat = matrices.last().pose();
                RenderingTools.coloredBasicVertex(mat,vertex,ExplosionBlockerBlockEntity.DEFENDING_RADIUS,-2,-4,0,0,255,255,255,255);
                RenderingTools.coloredBasicVertex(mat,vertex,ExplosionBlockerBlockEntity.DEFENDING_RADIUS,2,-4 ,0,1,255,255,255,255);
                RenderingTools.coloredBasicVertex(mat,vertex,ExplosionBlockerBlockEntity.DEFENDING_RADIUS,2,4  ,1,1,255,255,255,255);
                RenderingTools.coloredBasicVertex(mat,vertex,ExplosionBlockerBlockEntity.DEFENDING_RADIUS,-2,4 ,1,0,255,255,255,255);

                RenderingTools.coloredBasicVertex(mat,vertex,ExplosionBlockerBlockEntity.DEFENDING_RADIUS,-2,4 ,1,0,255,255,255,255);
                RenderingTools.coloredBasicVertex(mat,vertex,ExplosionBlockerBlockEntity.DEFENDING_RADIUS,2,4  ,1,1,255,255,255,255);
                RenderingTools.coloredBasicVertex(mat,vertex,ExplosionBlockerBlockEntity.DEFENDING_RADIUS,2,-4 ,0,1,255,255,255,255);
                RenderingTools.coloredBasicVertex(mat,vertex,ExplosionBlockerBlockEntity.DEFENDING_RADIUS,-2,-4,0,0,255,255,255,255);
                matrices.popPose();
            }

        }

        matrices.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(ExplosionBlockerBlockEntity p_112306_) {
        return true;
    }

}
