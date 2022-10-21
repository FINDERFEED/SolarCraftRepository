package com.finderfeed.solarcraft.content.runic_network.repeater;

import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;


//AND HURT YOU!(rickroll by a minecraft mod source code, no one expects it :D)
public class RunicNetworkRepeater extends Block implements EntityBlock {
    public RunicNetworkRepeater(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SolarcraftTileEntityTypes.RUNIC_ENERGY_REPEATER.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            RunicEnergyRepeaterTile.tick(level,blockState,blockPos,(RunicEnergyRepeaterTile) t);
        });
    }

}
