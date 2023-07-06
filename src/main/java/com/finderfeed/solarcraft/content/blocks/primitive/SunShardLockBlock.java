package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity.SunShardPuzzleBlockEntity;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SunShardLockBlock extends GlazedTerracottaBlock implements EntityBlock {

    public SunShardLockBlock(Properties p_49795_) {
        super(p_49795_);
    }


//    @Override
//    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
//        return PushReaction.IGNORE;
//    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        if (!world.isClientSide && world.getBlockEntity(pos) instanceof SunShardPuzzleBlockEntity tile && hand == InteractionHand.MAIN_HAND){
            tile.onUse(player);
        }
        return super.use(state, world, pos, player, hand, p_60508_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SolarcraftTileEntityTypes.SUN_SHARD_PUZZLE_TILE.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return EntityBlock.super.getTicker(p_153212_, p_153213_, p_153214_);
    }
}
