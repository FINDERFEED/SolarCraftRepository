package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.TileEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class BeamReflectorRenderer extends TileEntityRenderer<BeamReflectorTile> {

    public static final ResourceLocation BEAM_OUTPUT = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/ray_output.png");

    public BeamReflectorRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BeamReflectorTile tile, float partialTicks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
        for (Direction dir : tile.getDirections()){
            renderBeamOutput(matrices,src,dir,light);
        }
    }

    private void renderBeamOutput(PoseStack matrices, MultiBufferSource src,Direction dir,int light){
        matrices.pushPose();
        VertexConsumer vertex = src.getBuffer(RenderType.text(BEAM_OUTPUT));
        Matrix4f m = matrices.last().pose();
        matrices.translate(0.5,0.5,0.5);
        int step;
        if (Math.abs(step = dir.getStepX()) == 1){
            if (step == 1){
                vertex.vertex(m,0.501f,0.5f,-0.5f).color(1f,1f,1f,1f).uv(1,0).uv2(light).endVertex();
                vertex.vertex(m,0.501f,0.5f,0.5f).color(1f,1f,1f,1f).uv(1,1).uv2(light).endVertex();
                vertex.vertex(m,0.501f,-0.5f,0.5f).color(1f,1f,1f,1f).uv(0,1).uv2(light).endVertex();
                vertex.vertex(m,0.501f,-0.5f,-0.5f).color(1f,1f,1f,1f).uv(0,0).uv2(light).endVertex();
            }else{
                vertex.vertex(m,-0.501f,-0.5f,-0.5f).color(1f,1f,1f,1f).uv(0,0).uv2(light).endVertex();
                vertex.vertex(m,-0.501f,-0.5f,0.5f).color(1f,1f,1f,1f).uv(0,1).uv2(light).endVertex();
                vertex.vertex(m,-0.501f,0.5f,0.5f).color(1f,1f,1f,1f).uv(1,1).uv2(light).endVertex();
                vertex.vertex(m,-0.501f,0.5f,-0.5f).color(1f,1f,1f,1f).uv(1,0).uv2(light).endVertex();
            }
        }else if (Math.abs(step = dir.getStepY()) == 1){
            if (step == 1){


                vertex.vertex(m,-0.5f,0.501f,-0.5f).color(1f,1f,1f,1f).uv(0,0).uv2(light).endVertex();
                vertex.vertex(m,-0.5f,0.501f,0.5f).color(1f,1f,1f,1f).uv(0,1).uv2(light).endVertex();
                vertex.vertex(m,0.5f,0.501f,0.5f).color(1f,1f,1f,1f).uv(1,1).uv2(light).endVertex();
                vertex.vertex(m,0.5f,0.501f,-0.5f).color(1f,1f,1f,1f).uv(1,0).uv2(light).endVertex();

            }else{
                vertex.vertex(m,0.5f,-0.501f,-0.5f).color(1f,1f,1f,1f).uv(1,0).uv2(light).endVertex();
                vertex.vertex(m,0.5f,-0.501f,0.5f).color(1f,1f,1f,1f).uv(1,1).uv2(light).endVertex();
                vertex.vertex(m,-0.5f,-0.501f,0.5f).color(1f,1f,1f,1f).uv(0,1).uv2(light).endVertex();
                vertex.vertex(m,-0.5f,-0.501f,-0.5f).color(1f,1f,1f,1f).uv(0,0).uv2(light).endVertex();

            }
        }else if (Math.abs(step = dir.getStepZ()) == 1){
            if (step == 1){
                vertex.vertex(m,-0.5f,-0.5f,0.501f).color(1f,1f,1f,1f).uv(0,0).uv2(light).endVertex();
                vertex.vertex(m,0.5f,-0.5f,0.501f).color(1f,1f,1f,1f).uv(0,1).uv2(light).endVertex();
                vertex.vertex(m,0.5f,0.5f,0.501f).color(1f,1f,1f,1f).uv(1,1).uv2(light).endVertex();
                vertex.vertex(m,-0.5f,0.5f,0.501f).color(1f,1f,1f,1f).uv(1,0).uv2(light).endVertex();
            }else{
                vertex.vertex(m,-0.5f,0.5f,-0.501f).color(1f,1f,1f,1f).uv(1,0).uv2(light).endVertex();
                vertex.vertex(m,0.5f,0.5f,-0.501f).color(1f,1f,1f,1f).uv(1,1).uv2(light).endVertex();
                vertex.vertex(m,0.5f,-0.5f,-0.501f).color(1f,1f,1f,1f).uv(0,1).uv2(light).endVertex();
                vertex.vertex(m,-0.5f,-0.5f,-0.501f).color(1f,1f,1f,1f).uv(0,0).uv2(light).endVertex();
            }
        }


        matrices.popPose();
    }
}
