package com.finderfeed.solarforge.content.blocks.infusing_table_things.infusing_pool;


import com.mojang.blaze3d.vertex.PoseStack;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;


import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;


public class InfusingStandRenderer implements BlockEntityRenderer<InfusingStandTileEntity> {


    public InfusingStandRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(InfusingStandTileEntity tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light1, int light2) {
        matrices.pushPose();
        if (!tile.getStackInSlot(0).isEmpty() && tile.isRenderingItem()) {
            matrices.translate(0.5, 0.4, 0.5);
            float time = (tile.getLevel().getGameTime() + partialTicks);
            matrices.mulPose(Vector3f.YP.rotationDegrees((time % 360)*2f));
            Minecraft.getInstance().getItemRenderer().render(tile.getStackInSlot(0), ItemTransforms.TransformType.GROUND, true,
                    matrices, buffer, light1, light2, Minecraft.getInstance().getItemRenderer().getModel(tile.getStackInSlot(0), null, null,0));
        }
        matrices.popPose();

    }
}
