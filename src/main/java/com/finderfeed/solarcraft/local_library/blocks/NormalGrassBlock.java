package com.finderfeed.solarcraft.local_library.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IPlantable;

public class NormalGrassBlock extends GrassBlock {
    public NormalGrassBlock(Properties p_53685_) {
        super(p_53685_);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return !(plantable.getPlant(world,pos).getBlock() instanceof DeadBushBlock);
    }


}
