package com.finderfeed.solarforge.magic.blocks;

import com.finderfeed.solarforge.magic.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.misc_things.IProgressionBlock;
import com.finderfeed.solarforge.registries.blocks.SolarcraftBlocks;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;


import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;

public class UldoradiumOre extends OreBlock implements IProgressionBlock {
    public UldoradiumOre(Properties p_i48357_1_) {
        super(p_i48357_1_);
    }

    @Override
    public Block getUnlockedBlock() {

        return SolarcraftBlocks.ULDORADIUM_ORE.get();
    }

    @Override
    public Block getLockedBlock() {
        return Blocks.STONE;
    }

    @Override
    public Progression getRequiredProgression() {
        return Progression.TRANSMUTE_GEM;
    }

    @Override
    public void playerDestroy(Level p_180657_1_, Player p_180657_2_, BlockPos p_180657_3_, BlockState p_180657_4_, @Nullable BlockEntity p_180657_5_, ItemStack p_180657_6_) {

        super.playerDestroy(p_180657_1_, p_180657_2_, p_180657_3_, IProgressionBlock.super.getHarvestState(p_180657_2_,p_180657_4_), p_180657_5_, p_180657_6_);
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
        return false;
    }

    @Override
    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, LevelAccessor p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {

        return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }



//    @Override
//    public String getDescriptionId() {
//        if (Helpers.hasPlayerUnlocked(getRequiredProgression(),ClientHelpers.getClientPlayer())) {
//            return super.getDescriptionId();
//        }
//        return getLockedBlock().getDescriptionId();
//    }


}
