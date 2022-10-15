package com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile;

import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ClearingRitualMainBlock extends Block implements EntityBlock {
    public ClearingRitualMainBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SolarcraftTileEntityTypes.CLEARING_RITUAL_MAIN_BLOCK.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (level,pos,stat,tile)->ClearingRitualMainTile.tick((ClearingRitualMainTile) tile,pos,stat,level);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND && level.getBlockEntity(pos) instanceof ClearingRitualMainTile tile){
//            ClearingRitual.setRLState((ServerLevel) level,false,true);
//            for (int i = 0; i < RunicEnergy.Type.getAll().length;i++){
//                RunicEnergy.Type type = RunicEnergy.Type.getAll()[i];
//                tile.ritual.getAllCrystals().get(i).setREType(type);
//            }
            tile.startRitual(player);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, res);
    }
}
