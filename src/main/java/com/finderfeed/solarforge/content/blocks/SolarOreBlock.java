package com.finderfeed.solarforge.content.blocks;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.misc_things.IProgressionBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class SolarOreBlock extends OreBlock implements IProgressionBlock {
    public SolarOreBlock(Properties p_i48357_1_) {
        super(p_i48357_1_);
    }

    @Override
    public Block getUnlockedBlock() {
        return SolarForge.SOLAR_ORE.get();
    }

    @Override
    public Block getLockedBlock() {
        return Blocks.STONE;
    }

    @Override
    public Progression getRequiredProgression() {
        return Progression.ENTER_NETHER;
    }
    @Override
    public void playerDestroy(Level p_180657_1_, Player p_180657_2_, BlockPos p_180657_3_, BlockState p_180657_4_, @Nullable BlockEntity p_180657_5_, ItemStack p_180657_6_) {

        super.playerDestroy(p_180657_1_, p_180657_2_, p_180657_3_, IProgressionBlock.super.getHarvestState(p_180657_2_,p_180657_4_), p_180657_5_, p_180657_6_);
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
        return false;
    }
}
