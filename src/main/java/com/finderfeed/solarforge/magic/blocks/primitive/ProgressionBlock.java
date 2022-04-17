package com.finderfeed.solarforge.magic.blocks.primitive;

import com.finderfeed.solarforge.magic.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.misc_things.IProgressionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ProgressionBlock extends Block implements IProgressionBlock {

    private final Supplier<Progression> PROG;
    private final Block locked;
    public ProgressionBlock(Properties p_49795_, Supplier<Progression> prog,Block locked) {
        super(p_49795_);
        this.PROG = prog;
        this.locked = locked;
    }

    @Override
    public Block getUnlockedBlock() {
        return this;
    }

    @Override
    public Block getLockedBlock() {
        return locked;
    }

    @Override
    public Progression getRequiredProgression() {
        return PROG.get();
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
