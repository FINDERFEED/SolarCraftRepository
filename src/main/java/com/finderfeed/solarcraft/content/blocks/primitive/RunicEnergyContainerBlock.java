package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RunicEnergyContainerBlock extends Block {
    public RunicEnergyContainerBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
        if (!world.isClientSide && world.getBlockEntity(pos) instanceof AbstractRunicEnergyContainer container){
            container.resetAllRepeaters();
        }
        super.onRemove(state, world, pos, p_60518_, p_60519_);
    }
}
