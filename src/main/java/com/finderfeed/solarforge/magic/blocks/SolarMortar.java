package com.finderfeed.solarforge.magic.blocks;

import com.finderfeed.solarforge.magic.blocks.blockentities.MortarTileEntity;
import com.finderfeed.solarforge.misc_things.AbstractMortarTileEntity;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;


import javax.annotation.Nullable;



public class SolarMortar extends Block implements EntityBlock {
    public SolarMortar(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }




    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SolarcraftTileEntityTypes.MORTAR_TILE_ENTITY.get().create(blockPos,blockState);

    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            MortarTileEntity.tick(level,blockPos,blockState,(AbstractMortarTileEntity) t);
        });
    }
}
