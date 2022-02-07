package com.finderfeed.solarforge.magic.blocks.render;

import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarforge.magic.blocks.render.abstracts.AbstractRunicEnergyContainerRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

public class EnchanterRenderer extends AbstractRunicEnergyContainerRenderer<EnchanterBlockEntity> {


    public EnchanterRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(EnchanterBlockEntity tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        ItemStack stack = tile.getStackInSlot(0);
        if (stack != null){
            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
            float time = RenderingTools.getTime(tile.getLevel(),pticks);
            matrices.pushPose();
            matrices.translate(0.5,1.3,0.5);
            matrices.mulPose(Vector3f.YN.rotationDegrees(time%360));
            renderer.render(stack, ItemTransforms.TransformType.FIXED,true,matrices,buffer,light, OverlayTexture.NO_OVERLAY,
                    renderer.getModel(stack,Minecraft.getInstance().level, Minecraft.getInstance().player,0));
            matrices.popPose();
        }

        super.render(tile, pticks, matrices, buffer, light, overlay);
    }
}
