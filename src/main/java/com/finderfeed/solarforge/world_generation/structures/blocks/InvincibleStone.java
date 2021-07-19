package com.finderfeed.solarforge.world_generation.structures.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;

public class InvincibleStone extends Block {
    public InvincibleStone() {
        super(AbstractBlock.Properties.copy(Blocks.BEDROCK));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.IGNORE;
    }
}
