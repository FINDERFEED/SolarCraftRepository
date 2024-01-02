package com.finderfeed.solarcraft.content.runic_network.repeater;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

//AND DESERT YOU! NEVER GONNA MAKE YOU CRY, NEVER GONNA SAY GOODBYE, NEVER GONNA TELL A LIE
public class RepeaterRenderer implements BlockEntityRenderer<BaseRepeaterTile> {


    public RepeaterRenderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(BaseRepeaterTile tile, float v, PoseStack matrices, MultiBufferSource multiBufferSource, int i, int i1) {
        matrices.pushPose();
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
        return new AABB(blockEntity.getBlockPos().offset(-16,-16,-16),blockEntity.getBlockPos().offset(16,16,16));
    }
}
