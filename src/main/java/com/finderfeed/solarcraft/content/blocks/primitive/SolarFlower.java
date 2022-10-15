package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Random;

public class SolarFlower extends DeadBushBlock {
    public SolarFlower(Properties p_i48418_1_) {
        super(p_i48418_1_);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(9) == 0) {
            Direction direction = Direction.getRandom(random);
            if (direction != Direction.UP) {
                BlockPos blockpos = pos.relative(direction);
                BlockState blockstate = level.getBlockState(blockpos);
                if (!state.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, direction.getOpposite())) {

                    level.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), (double)pos.getX()+0.5f +(random.nextFloat()*0.4)-0.2, (double)pos.getY() +0.5+(random.nextFloat()*0.4)-0.2, (double)pos.getZ()+0.5f +(random.nextFloat()*0.4)-0.2, 0.0D, 0.01D, 0.0D);
                }
            }
        }
    }

    public void animateTick(BlockState p_180655_1_, Level p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        if (p_180655_4_.nextInt(9) == 0) {
            Direction direction = Direction.getRandom(RandomSource.create());
            if (direction != Direction.UP) {
                BlockPos blockpos = p_180655_3_.relative(direction);
                BlockState blockstate = p_180655_2_.getBlockState(blockpos);
                if (!p_180655_1_.canOcclude() || !blockstate.isFaceSturdy(p_180655_2_, blockpos, direction.getOpposite())) {

                    p_180655_2_.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), (double)p_180655_3_.getX()+0.5f +(p_180655_2_.random.nextFloat()*0.4)-0.2, (double)p_180655_3_.getY() +0.5+(p_180655_2_.random.nextFloat()*0.4)-0.2, (double)p_180655_3_.getZ()+0.5f +(p_180655_2_.random.nextFloat()*0.4)-0.2, 0.0D, 0.01D, 0.0D);
                }
            }
        }
    }
}
