package com.finderfeed.solarcraft.misc_things;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface IBindable {
    void bindPos(BlockPos pos);

    default void update(BlockEntity be){
        if ((be.getLevel()) != null && (!be.getLevel().isClientSide)) {
            BlockState state = be.getLevel().getBlockState(be.getBlockPos());
            be.setChanged();
            be.getLevel().sendBlockUpdated(be.getBlockPos(), state, state, 3);
        }
    }
}
