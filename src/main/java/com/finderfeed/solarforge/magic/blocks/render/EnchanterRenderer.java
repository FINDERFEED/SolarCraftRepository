package com.finderfeed.solarforge.magic.blocks.render;

import com.finderfeed.solarforge.magic.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarforge.magic.blocks.render.abstracts.AbstractRunicEnergyContainerRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class EnchanterRenderer extends AbstractRunicEnergyContainerRenderer<EnchanterBlockEntity> {


    public EnchanterRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(EnchanterBlockEntity tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        super.render(tile, pticks, matrices, buffer, light, overlay);
    }
}
