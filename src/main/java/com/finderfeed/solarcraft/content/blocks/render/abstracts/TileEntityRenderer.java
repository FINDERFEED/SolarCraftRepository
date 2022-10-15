package com.finderfeed.solarcraft.content.blocks.render.abstracts;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class TileEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {

    public TileEntityRenderer(BlockEntityRendererProvider.Context ctx){

    }

}
