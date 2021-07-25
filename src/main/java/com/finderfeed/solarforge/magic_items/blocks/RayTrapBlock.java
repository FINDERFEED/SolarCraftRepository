package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.RayTrapTileEntity;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RayTrapBlock extends Block implements EntityBlock {
    public RayTrapBlock(Properties p_i48415_1_) {
        super(p_i48415_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.FACING, Direction.NORTH));
    }



    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, p_196258_1_.getNearestLookingDirection().getOpposite());
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(BlockStateProperties.FACING);
        super.createBlockStateDefinition(p_206840_1_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntitiesRegistry.RAY_TRAP_TILE_ENTITY.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            RayTrapTileEntity.tick(level,blockPos,blockState,(RayTrapTileEntity) t);
        });
    }
}
