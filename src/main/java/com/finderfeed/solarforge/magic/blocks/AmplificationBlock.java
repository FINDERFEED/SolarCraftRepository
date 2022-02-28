package com.finderfeed.solarforge.magic.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class AmplificationBlock extends Block {

    private Supplier<MobEffect> effect;

    public AmplificationBlock(Properties p_49795_, Supplier<MobEffect> effect) {
        super(p_49795_);
        this.effect = effect;
    }


    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(world, pos, state, entity);
    }
}
