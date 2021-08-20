package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.misc_things.ParticlesList;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;

import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RunicLeavesBlock extends LeavesBlock {
    public RunicLeavesBlock(Properties p_i48370_1_) {
        super(p_i48370_1_);
    }




    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public void animateTick(BlockState p_180655_1_, Level p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        super.animateTick(p_180655_1_,p_180655_2_,p_180655_3_,p_180655_4_);
        if (p_180655_4_.nextInt(40) == 0) {
            Direction direction = Direction.getRandom(p_180655_4_);
            if (direction != Direction.UP) {
                BlockPos blockpos = p_180655_3_.relative(direction);
                BlockState blockstate = p_180655_2_.getBlockState(blockpos);
                if (!p_180655_1_.canOcclude() || !blockstate.isFaceSturdy(p_180655_2_, blockpos, direction.getOpposite())) {
                    double d0 = direction.getStepX() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepX() * 0.6D;
                    double d1 = direction.getStepY() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepY() * 0.6D;
                    double d2 = direction.getStepZ() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepZ() * 0.6D;
                    p_180655_2_.addParticle(ParticlesList.RUNE_PARTICLE.get(), (double)p_180655_3_.getX() + d0, (double)p_180655_3_.getY() + d1, (double)p_180655_3_.getZ() + d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
