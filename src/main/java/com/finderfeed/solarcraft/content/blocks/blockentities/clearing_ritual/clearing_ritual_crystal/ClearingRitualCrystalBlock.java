package com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.blocks.TileEntityDataSaverBlock;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
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
        super(props, SolarCraft.MOD_ID);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SolarcraftTileEntityTypes.CLEARING_RITUAL_CRYSTAL.get().create(pos,state);
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

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        return !level.getBlockState(pos.below(2)).is(SCBlocks.CRYSTAL_ENERGY_VINES.get());
    }
}
