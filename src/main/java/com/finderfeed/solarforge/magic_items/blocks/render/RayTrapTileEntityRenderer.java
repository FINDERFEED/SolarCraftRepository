package com.finderfeed.solarforge.magic_items.blocks.render;

import com.finderfeed.solarforge.RenderingTools;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RayTrapTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class RayTrapTileEntityRenderer implements BlockEntityRenderer<RayTrapTileEntity> {

    public int ticker = 0;

    public RayTrapTileEntityRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(RayTrapTileEntity tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {

        if (tile.CLIENT_TRIGGER_INTEGER != 0 ){
            RenderingTools.renderRay(matrices,buffer,0.25f,5, Direction.byName(tile.direction),true,3,partialTicks);

        }

    }

    @Override
    public boolean shouldRenderOffScreen(RayTrapTileEntity p_188185_1_) {
        return true;
    }
}
