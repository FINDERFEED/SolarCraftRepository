package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.misc_things.IProgressionBlock;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.block.*;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;


import javax.annotation.Nullable;

public class UldoradiumOre extends OreBlock implements IProgressionBlock {
    public UldoradiumOre(Properties p_i48357_1_) {
        super(p_i48357_1_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(UNLOCKED,false));
    }

    @Override
    public Block getUnlockedBlock() {

        return BlocksRegistry.ULDORADIUM_ORE.get();
    }

    @Override
    public Block getLockedBlock() {
        return Blocks.STONE;
    }

    @Override
    public Achievement getRequiredProgression() {
        return Achievement.TRANSMUTE_GEM;
    }

    @Override
    public void playerDestroy(World p_180657_1_, PlayerEntity p_180657_2_, BlockPos p_180657_3_, BlockState p_180657_4_, @Nullable TileEntity p_180657_5_, ItemStack p_180657_6_) {

        super.playerDestroy(p_180657_1_, p_180657_2_, p_180657_3_, IProgressionBlock.super.getHarvestState(p_180657_2_,p_180657_4_), p_180657_5_, p_180657_6_);
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
        return false;
    }

    @Override
    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {

        return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(UNLOCKED);
        super.createBlockStateDefinition(p_206840_1_);
    }

//    @Override
//    public String getDescriptionId() {
//        if (Helpers.hasPlayerUnlocked(getRequiredProgression(),ClientHelpers.getClientPlayer())) {
//            return super.getDescriptionId();
//        }
//        return getLockedBlock().getDescriptionId();
//    }


}
