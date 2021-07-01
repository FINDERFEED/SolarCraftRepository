package com.finderfeed.solarforge.magic_items.blocks.render;

import com.finderfeed.solarforge.RenderingTools;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RayTrapTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;

public class RayTrapTileEntityRenderer extends TileEntityRenderer<RayTrapTileEntity> {

    public int ticker = 0;

    public RayTrapTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(RayTrapTileEntity tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int light, int overlay) {

        if (tile.CLIENT_TRIGGER_INTEGER != 0 ){
            RenderingTools.renderRay(matrices,buffer,0.25f,5, Direction.byName(tile.direction),true,3,partialTicks);

        }

    }

    @Override
    public boolean shouldRenderOffScreen(RayTrapTileEntity p_188185_1_) {
        return true;
    }
}
