package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.TrapControllerTile;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;


import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TrapStructureController extends Block implements EntityBlock {
    public TrapStructureController(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }



    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntitiesRegistry.TRAP_STRUCT_CONTROLLER.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            TrapControllerTile.tick(level,blockPos,blockState,(TrapControllerTile) t);
        });
    }
}
