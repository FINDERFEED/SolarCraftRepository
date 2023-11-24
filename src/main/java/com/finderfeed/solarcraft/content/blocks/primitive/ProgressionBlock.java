package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.Helpers;
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
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ProgressionBlock extends Block {

    public static final Set<ProgressionBlock> PROGRESSION_BLOCKS = new HashSet<>();

    private final Supplier<Progression> PROG;
    private final Block locked;
    public ProgressionBlock(Properties p_49795_, Supplier<Progression> prog,Block locked) {
        super(p_49795_);
        this.PROG = prog;
        this.locked = locked;
        PROGRESSION_BLOCKS.add(this);
    }

    public Block getUnlockedBlock() {
        return this;
    }

    public Block getLockedBlock() {
        return locked;
    }

    public Progression getRequiredProgression() {
        return PROG.get();
    }

    public boolean isUnlocked(Player player){
        return Helpers.hasPlayerCompletedProgression(this.getRequiredProgression(),player);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos p_180657_3_, BlockState blockstate, @Nullable BlockEntity te, ItemStack p_180657_6_) {

        BlockState harvestState = Helpers.hasPlayerCompletedProgression(getRequiredProgression(),player) ? getUnlockedBlock().defaultBlockState():getLockedBlock().defaultBlockState();
        super.playerDestroy(level, player, p_180657_3_, harvestState, te, p_180657_6_);
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
        return false;
    }
}
