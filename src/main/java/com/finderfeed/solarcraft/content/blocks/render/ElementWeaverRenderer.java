package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.content.blocks.blockentities.ElementWeaverTileEntity;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.AbstractRunicEnergyContainerRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class ElementWeaverRenderer extends AbstractRunicEnergyContainerRenderer<ElementWeaverTileEntity> {
    public ElementWeaverRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }


    @Override
    public void render(ElementWeaverTileEntity tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        super.render(tile, pticks, matrices, buffer, light, overlay);
    }
}
