package com.finderfeed.solarforge.structures.blocks;

import com.finderfeed.solarforge.misc_things.BlockWithEntity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.structures.blocks.tile_entities.KeyDefenderTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class KeyDefender extends Block {
    public KeyDefender(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntitiesRegistry.KEY_DEFENDER_TILE.get().create();
    }



}
