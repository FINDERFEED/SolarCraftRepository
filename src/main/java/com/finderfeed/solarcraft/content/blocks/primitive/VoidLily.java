package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class VoidLily extends DeadBushBlock {

    public VoidLily(Properties p_52417_) {
        super(p_52417_);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rnd) {
        if (rnd.nextInt(9) == 0) {
            Direction direction = Direction.getRandom(rnd);
            if (direction != Direction.UP) {
                BlockPos blockpos = pos.relative(direction);
                BlockState blockstate = level.getBlockState(blockpos);
                if (!state.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, direction.getOpposite())) {
                    ClientHelpers.Particles.createParticle(
                            SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                            (double)pos.getX()+0.5f +(level.random.nextFloat()*0.4)-0.2, (double)pos.getY() +0.5+(level.random.nextFloat()*0.4)-0.2, (double)pos.getZ()+0.5f +(level.random.nextFloat()*0.4)-0.2, 0.0D, 0.01D, 0.0D,
                            ()->72,()->0,()->255,0.25f
                    );
                }
            }
        }
    }

}
