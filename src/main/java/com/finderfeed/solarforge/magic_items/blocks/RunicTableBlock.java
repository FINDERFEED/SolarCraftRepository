package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.network.NetworkHooks;


public class RunicTableBlock extends Block implements EntityBlock {
    public RunicTableBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }




    @Override
    public void onRemove(BlockState p_196243_1_, Level p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {
        if (!p_196243_2_.isClientSide){
            BlockEntity tile = p_196243_2_.getBlockEntity(p_196243_3_);
            if (tile instanceof RunicTableTileEntity){
                Containers.dropContents(p_196243_2_,p_196243_3_,(RunicTableTileEntity)tile);
            }
        }
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player pe, InteractionHand hand, BlockHitResult trace) {
        if (!world.isClientSide && (world.getBlockEntity(pos) instanceof RunicTableTileEntity) ){
            if (!ProgressionHelper.doPlayerAlreadyHasPattern(pe)){
                ProgressionHelper.generateRandomPatternForPlayer(pe);
            }
                NetworkHooks.openGui((ServerPlayer) pe, (RunicTableTileEntity) world.getBlockEntity(pos),
                        (buf) -> {
                            buf.writeBlockPos(pos);
                            for (int a : ProgressionHelper.getPlayerPattern(pe)){
                                buf.writeInt(a);
                            }
                        }
                );
                return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, pe, hand, trace);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntitiesRegistry.RUNIC_TABLE_TILE.get().create(blockPos,blockState);
    }


}
