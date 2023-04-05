package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.BeamData;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.TileEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BeamGeneratorRenderer extends TileEntityRenderer<BeamGenerator> {

    public BeamGeneratorRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BeamGenerator tile, float partialTicks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
        BeamData beamData = tile.getBeamData();
        if (beamData != null){
            for (BeamData.BeamPath path : beamData.getPaths()){
                renderRayBetween(tile.getBlockPos(),matrices,src,path.beamStart(),path.beamEnd());
            }
        }
    }


    private void renderRayBetween(BlockPos basePos,PoseStack matrices, MultiBufferSource src, BlockPos p1,BlockPos p2){
        matrices.pushPose();

        VertexConsumer vertex = src.getBuffer(SolarCraftRenderTypes.LIGHTING_NO_CULL);
        float x1 = p1.getX() - basePos.getX();
        float y1 = p1.getY() - basePos.getY();
        float z1 = p1.getZ() - basePos.getZ();
        float x2 = x1 + p2.getX() - p1.getX();
        float z2 = z1 + p2.getZ() - p1.getZ();

        float deltaX = p2.getX() - p1.getX();
        float deltaY = p2.getY() - p1.getY();
        float deltaZ = p2.getZ() - p1.getZ();

        Matrix4f m = matrices.last().pose();
        float scale = 0.25f;
        matrices.translate(x1+0.375,y1+0.375,z1+0.375);
        if (Math.abs(x1) != Math.abs(x2)){

            matrices.scale(1,scale,scale);
            vertex.vertex(m,0,0,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,deltaX,0,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,deltaX,1,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,1,0).color(1,1,0,0.5f).endVertex();

            vertex.vertex(m,0,0,1).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,deltaX,0,1).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,deltaX,1,1).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,1,1).color(1,1,0,0.5f).endVertex();

            vertex.vertex(m,0,0,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,deltaX,0,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,deltaX,0,1).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,0,1).color(1,1,0,0.5f).endVertex();

            vertex.vertex(m,0,1,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,deltaX,1,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,deltaX,1,1).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,1,1).color(1,1,0,0.5f).endVertex();
        }else if (Math.abs(z1) != Math.abs(z2)){
            matrices.scale(scale,scale,1f);
            vertex.vertex(m,0,0,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,0,deltaZ).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,1,deltaZ).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,1,0).color(1,1,0,0.5f).endVertex();

            vertex.vertex(m,1,0,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,0,deltaZ).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,1,deltaZ).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,1,0).color(1,1,0,0.5f).endVertex();

            vertex.vertex(m,0,0,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,0,deltaZ).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,0,deltaZ).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,0,0).color(1,1,0,0.5f).endVertex();

            vertex.vertex(m,0,1,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,1,deltaZ).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,1,deltaZ).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,1,0).color(1,1,0,0.5f).endVertex();
        }else{
            matrices.scale(scale,1,scale);
            vertex.vertex(m,0,0,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,deltaY,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,deltaY,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,0,0).color(1,1,0,0.5f).endVertex();

            vertex.vertex(m,0,0,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,deltaY,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,deltaY,1).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,0,1).color(1,1,0,0.5f).endVertex();


            vertex.vertex(m,0,0,1).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,0,deltaY,1).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,deltaY,1).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,0,1).color(1,1,0,0.5f).endVertex();

            vertex.vertex(m,1,0,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,deltaY,0).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,deltaY,1).color(1,1,0,0.5f).endVertex();
            vertex.vertex(m,1,0,1).color(1,1,0,0.5f).endVertex();
        }



        matrices.popPose();
    }


}
