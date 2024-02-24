package com.finderfeed.solarcraft.content.runic_network.repeater;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

//AND DESERT YOU! NEVER GONNA MAKE YOU CRY, NEVER GONNA SAY GOODBYE, NEVER GONNA TELL A LIE
public class RepeaterRenderer implements BlockEntityRenderer<BaseRepeaterTile> {


    public RepeaterRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(BaseRepeaterTile tile, float v, PoseStack matrices, MultiBufferSource multiBufferSource, int i, int i1) {
        matrices.pushPose();


        if (true) {
            matrices.pushPose();

            Matrix4f m = matrices.last().pose();
            ResourceLocation t = new ResourceLocation(SolarCraft.MOD_ID, "textures/frame0.png");
            VertexConsumer c = multiBufferSource.getBuffer(SCRenderTypes.TEXT_GLOW.apply(t));
            c.vertex(m, 0, 0, 0).color(1f, 1f, 1f, 1f).uv(0, 0).uv2(LightTexture.FULL_BRIGHT).endVertex();
            c.vertex(m, 0, 1, 0).color(1f, 1f, 1f, 1f).uv(0, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
            c.vertex(m, 1, 1, 0).color(1f, 1f, 1f, 1f).uv(1, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
            c.vertex(m, 1, 0, 0).color(1f, 1f, 1f, 1f).uv(1, 0).uv2(LightTexture.FULL_BRIGHT).endVertex();
            matrices.popPose();
        }

        if (true) {
            matrices.pushPose();

            Matrix4f m = matrices.last().pose();
            ResourceLocation t = new ResourceLocation(SolarCraft.MOD_ID, "textures/frame0.png");
            VertexConsumer c = multiBufferSource.getBuffer(SCRenderTypes.TEXT_GLOW_NO_SHARD.apply(t));
            float a = (float)((Math.sin(tile.getLevel().getGameTime()) + 1)/2 );
            c.vertex(m, 0, 0, 0).color(1f, 1f, 1f, a).uv(0, 0).uv2(LightTexture.FULL_BRIGHT).endVertex();
            c.vertex(m, 0, 1, 0).color(1f, 1f, 1f, a).uv(0, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
            c.vertex(m, 1, 1, 0).color(1f, 1f, 1f, a).uv(1, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
            c.vertex(m, 1, 0, 0).color(1f, 1f, 1f, a).uv(1, 0).uv2(LightTexture.FULL_BRIGHT).endVertex();
            matrices.popPose();
        }
        if (tile.getConnections() != null){
            tile.getConnections().forEach((pos)->{
                Vec3 tilepos = new Vec3(tile.getBlockPos().getX() +0.5,tile.getBlockPos().getY() +0.5,tile.getBlockPos().getZ() +0.5);
                Vec3 vector = Helpers.getBlockCenter(pos).subtract(tilepos);

                RenderingTools.renderRay(matrices,multiBufferSource,0.25f,(float)vector.length(),(mat)->{
                    RenderingTools.applyMovementMatrixRotations(mat,vector.normalize());
                },false,0,v);
            });
        }
        matrices.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(BaseRepeaterTile blockEntity) {
        return new AABB(Helpers.posToVec(blockEntity.getBlockPos().offset(-16,-16,-16)),
                Helpers.posToVec(blockEntity.getBlockPos().offset(16,16,16)));
    }
}
