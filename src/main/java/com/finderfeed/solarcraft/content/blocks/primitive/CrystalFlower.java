package com.finderfeed.solarcraft.content.blocks.primitive;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CrystalFlower extends FlowerBlock {
    public CrystalFlower(Properties p_53514_) {
        super(MobEffects.NIGHT_VISION,60, p_53514_);
    }



    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter p_51043_, BlockPos p_51044_) {

        return p_51043_.getBlockState(p_51044_).is(BlockTags.BASE_STONE_OVERWORLD);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {

        return level.getBlockState(pos.below()).is(BlockTags.BASE_STONE_OVERWORLD);
    }
}
