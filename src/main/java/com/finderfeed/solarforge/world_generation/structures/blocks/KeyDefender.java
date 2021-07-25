package com.finderfeed.solarforge.world_generation.structures.blocks;

import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

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
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return TileEntitiesRegistry.KEY_DEFENDER_TILE.get().create();
    }



}
