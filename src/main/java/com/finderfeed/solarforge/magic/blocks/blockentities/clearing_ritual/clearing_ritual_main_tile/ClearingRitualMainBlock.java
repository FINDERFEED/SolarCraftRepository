package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile;

import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ClearingRitualMainBlock extends Block implements EntityBlock {
    public ClearingRitualMainBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileEntitiesRegistry.CLEARING_RITUAL_MAIN_BLOCK.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (level,pos,stat,tile)->ClearingRitualMainTile.tick((ClearingRitualMainTile) tile,pos,stat,level);
    }
}
