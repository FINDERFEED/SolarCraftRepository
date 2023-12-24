package com.finderfeed.solarcraft.content.world_generation.structures.blocks;

import com.finderfeed.solarcraft.content.world_generation.structures.blocks.tile_entities.KeyDefenderTile;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

public class KeyDefender extends Block implements EntityBlock {
    public KeyDefender(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SCTileEntities.KEY_DEFENDER_TILE.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            KeyDefenderTile.tick(level,blockPos,blockState,(KeyDefenderTile) t);
        });
    }
}
