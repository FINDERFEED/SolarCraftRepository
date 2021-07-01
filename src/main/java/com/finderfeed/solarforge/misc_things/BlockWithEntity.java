package com.finderfeed.solarforge.misc_things;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public abstract class BlockWithEntity extends Block {

    public final TileEntity tile;

    public BlockWithEntity(Properties p_i48440_1_, TileEntity a) {
        super(p_i48440_1_);
        this.tile = a;

    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tile;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
