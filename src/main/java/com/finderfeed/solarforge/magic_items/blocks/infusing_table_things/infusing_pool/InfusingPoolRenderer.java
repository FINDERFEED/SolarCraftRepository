package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool;


import com.mojang.blaze3d.vertex.PoseStack;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;


import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import com.mojang.math.Vector3f;


public class InfusingPoolRenderer extends BlockEntityRenderer<InfusingPoolTileEntity> {


    public InfusingPoolRenderer(BlockEntityRenderDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(InfusingPoolTileEntity tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light1, int light2) {
        matrices.pushPose();
        if (!tile.isEmpty()) {
            matrices.translate(0.5, 0.4, 0.5);
            float time = (tile.getLevel().getGameTime() + partialTicks);
            matrices.mulPose(Vector3f.YP.rotationDegrees((time % 360)*2f));
            Minecraft.getInstance().getItemRenderer().render(tile.getItem(0), ItemTransforms.TransformType.GROUND, true,
                    matrices, buffer, light1, light2, Minecraft.getInstance().getItemRenderer().getModel(tile.getItem(0), null, null));
        }
        matrices.popPose();

    }
}
