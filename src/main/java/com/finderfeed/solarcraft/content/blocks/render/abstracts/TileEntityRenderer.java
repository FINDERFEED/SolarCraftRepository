package com.finderfeed.solarcraft.content.blocks.render.abstracts;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class TileEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {

    public TileEntityRenderer(BlockEntityRendererProvider.Context ctx){

    }


    @Override
    public abstract void render(T tile, float partialTicks, PoseStack matrices, MultiBufferSource src, int light, int overlay);
}
