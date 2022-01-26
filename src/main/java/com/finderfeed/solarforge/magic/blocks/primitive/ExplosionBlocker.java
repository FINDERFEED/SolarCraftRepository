package com.finderfeed.solarforge.magic.blocks.primitive;

import com.finderfeed.solarforge.magic.blocks.blockentities.ExplosionBlockerBlockEntity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ExplosionBlocker extends Block implements EntityBlock {
    public ExplosionBlocker(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileEntitiesRegistry.EXPLOSTION_BLOCKER.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        if (!world.isClientSide){
            return null;
        }
        return (a,b,c,d)->{
            if (a.isClientSide) {
                ExplosionBlockerBlockEntity.tick((ExplosionBlockerBlockEntity) d, a);
            }
        };
    }


    //    @Override
//    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitRes) {
//        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND){
//            if (world.getBlockEntity(pos) instanceof ExplosionBlockerBlockEntity blocker){
//                blocker.setShieldRenderingState(!blocker.shouldRenderShield());
//                blocker.setChanged();
//                world.sendBlockUpdated(pos,state,state,3);
//                return InteractionResult.SUCCESS;
//            }
//        }
//        return super.use(state, world, pos, player, hand, hitRes);
//    }
}
