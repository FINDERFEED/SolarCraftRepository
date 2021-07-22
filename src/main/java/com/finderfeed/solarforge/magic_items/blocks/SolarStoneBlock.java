package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.misc_things.IProgressionBlock;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SolarStoneBlock extends Block implements IProgressionBlock {
    public SolarStoneBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public Block getUnlockedBlock() {
        return BlocksRegistry.SOLAR_STONE.get();
    }

    @Override
    public Block getLockedBlock() {
        return Blocks.STONE;
    }



    @Override
    public Achievement getRequiredProgression() {
        return Achievement.ENTER_NETHER;
    }
    @Override
    public void playerDestroy(World p_180657_1_, PlayerEntity p_180657_2_, BlockPos p_180657_3_, BlockState p_180657_4_, @Nullable TileEntity p_180657_5_, ItemStack p_180657_6_) {

        super.playerDestroy(p_180657_1_, p_180657_2_, p_180657_3_, IProgressionBlock.super.getHarvestState(p_180657_2_,p_180657_4_), p_180657_5_, p_180657_6_);
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
        return false;
    }
}
