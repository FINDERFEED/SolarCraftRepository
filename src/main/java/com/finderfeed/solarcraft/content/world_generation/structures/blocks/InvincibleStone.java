package com.finderfeed.solarcraft.content.world_generation.structures.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.PushReaction;

public class InvincibleStone extends Block {
    public InvincibleStone() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).pushReaction(PushReaction.IGNORE));
    }

//    @Override
//    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
//        return PushReaction.IGNORE;
//    }
}
