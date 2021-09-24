package com.finderfeed.solarforge.for_future_library.blocks;

import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractFlowerFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.common.IPlantable;

import java.util.List;
import java.util.Random;

public class NormalGrassBlock extends GrassBlock {
    public NormalGrassBlock(Properties p_53685_) {
        super(p_53685_);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return !(plantable.getPlant(world,pos).getBlock() instanceof DeadBushBlock);
    }


}
