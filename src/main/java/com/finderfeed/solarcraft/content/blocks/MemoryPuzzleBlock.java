package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle.MemoryPuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle.MemoryPuzzleOpenScreenPacket;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MemoryPuzzleBlock extends Block implements EntityBlock {

    public MemoryPuzzleBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND && level.getBlockEntity(pos) instanceof MemoryPuzzleBlockEntity tile){
            tile.onUse((ServerPlayer)player);
        }
        return super.use(state, level, pos, player, hand, p_60508_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SCTileEntities.MEMORY_PUZZLE.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (a,b,c,d)->{
            MemoryPuzzleBlockEntity.tick((MemoryPuzzleBlockEntity) d,a);
        };
    }
}
