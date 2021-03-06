package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_crystal;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.local_library.blocks.TileEntityDataSaverBlock;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ClearingRitualCrystalBlock extends TileEntityDataSaverBlock implements EntityBlock {


    public ClearingRitualCrystalBlock(Properties props) {
        super(props, SolarForge.MOD_ID);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileEntitiesRegistry.CLEARING_RITUAL_CRYSTAL.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return ((level, blockPos, blockState, t) -> {
            ClearingRitualCrystalTile.superDuperTickYouCanNeverImagineHowSuperItIs(level,state,blockPos,(ClearingRitualCrystalTile) t);
        });
    }


    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
