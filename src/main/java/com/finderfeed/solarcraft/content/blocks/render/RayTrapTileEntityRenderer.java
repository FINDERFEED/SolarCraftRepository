package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.blocks.blockentities.RayTrapTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

public class RayTrapTileEntityRenderer implements BlockEntityRenderer<RayTrapTileEntity> {

    public int ticker = 0;

    public RayTrapTileEntityRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(RayTrapTileEntity tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {

        if (tile.clientTicker != 0 ){
            RenderingTools.renderRay(matrices,buffer,0.25f,5, Direction.byName(tile.direction),true,3,partialTicks);

        }

    }

    @Override
    public AABB getRenderBoundingBox(RayTrapTileEntity blockEntity) {
        return new AABB(Helpers.posToVec(blockEntity.getBlockPos().offset(-6,-6,-6)),
                Helpers.posToVec(blockEntity.getBlockPos().offset(6,6,6)));
    }

    @Override
    public boolean shouldRenderOffScreen(RayTrapTileEntity p_188185_1_) {
        return true;
    }
}
