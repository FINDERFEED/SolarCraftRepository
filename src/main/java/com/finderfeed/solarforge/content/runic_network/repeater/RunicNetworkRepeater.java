package com.finderfeed.solarforge.content.runic_network.repeater;

import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;


//AND HURT YOU!(rickroll by a minecraft mod source code, no one expects it :D)
public class RunicNetworkRepeater extends Block implements EntityBlock {
    public RunicNetworkRepeater(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SolarcraftTileEntityTypes.REPEATER.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            BaseRepeaterTile.tick(level,blockPos,blockState,(BaseRepeaterTile) t);
        });
    }



//
//    @Override
//    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult res) {
//        if (!level.isClientSide && hand.equals(InteractionHand.MAIN_HAND)){
//            if (level.getBlockEntity(blockPos) instanceof BaseRepeaterTile repeater) {
//                List<BlockPos> path = new RunicEnergyPath(repeater.getEnergyType()).build(repeater);
//                System.out.println(path);
//            }
//        }
//        return InteractionResult.SUCCESS;
//    }


}
