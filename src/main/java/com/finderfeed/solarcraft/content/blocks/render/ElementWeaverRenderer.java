package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.content.blocks.blockentities.ElementWeaverTileEntity;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.AbstractRunicEnergyContainerRenderer;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ElementWeaverRenderer extends AbstractRunicEnergyContainerRenderer<ElementWeaverTileEntity> {
    public ElementWeaverRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }


    @Override
    public void render(ElementWeaverTileEntity tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        super.render(tile, pticks, matrices, buffer, light, overlay);
        ItemStack item = tile.inputSlot();
        if (item != null){
            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
            matrices.pushPose();
            matrices.translate(0.5,0.375,0.5);
//            matrices.mulPose(Vector3f.YP.rotationDegrees(
//                    RenderingTools.getTime(tile.getLevel(),pticks)
//            ));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),RenderingTools.getTime(tile.getLevel(),pticks)));
            renderer.render(item, ItemDisplayContext.GROUND,true,matrices,buffer, LightTexture.FULL_BRIGHT,OverlayTexture.NO_OVERLAY,
                    Minecraft.getInstance().getItemRenderer().getModel(item,tile.getLevel(),null,0));
            matrices.popPose();
        }
    }
}
