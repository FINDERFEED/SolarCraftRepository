package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Random;

public class SolarFlower extends GrowableBush {
    public SolarFlower(Properties p_i48418_1_) {
        super(p_i48418_1_);
    }

    @Override
    public ItemStack getDroppedItem(RandomSource source) {
        ItemStack itemStack = SCItems.ENERGY_DUST.get().getDefaultInstance();
        itemStack.setCount(source.nextInt(3) + 2);
        return itemStack;
    }

    @Override
    protected boolean mayPlaceOn(BlockState p_51042_, BlockGetter p_51043_, BlockPos p_51044_) {
        return p_51042_.is(BlockTags.DEAD_BUSH_MAY_PLACE_ON);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(9) == 0) {
            Direction direction = Direction.getRandom(random);
            if (direction != Direction.UP) {
                BlockPos blockpos = pos.relative(direction);
                BlockState blockstate = level.getBlockState(blockpos);
                if (!state.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, direction.getOpposite())) {

                    level.addParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), (double)pos.getX()+0.5f +(random.nextFloat()*0.4)-0.2, (double)pos.getY() +0.5+(random.nextFloat()*0.4)-0.2, (double)pos.getZ()+0.5f +(random.nextFloat()*0.4)-0.2, 0.0D, 0.01D, 0.0D);
                }
            }
        }
    }


}
