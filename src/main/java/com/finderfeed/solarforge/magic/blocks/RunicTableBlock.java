package com.finderfeed.solarforge.magic.blocks;

import com.finderfeed.solarforge.magic.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarforge.magic.blocks.blockentities.containers.RunicTableContainer;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.RunePattern;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.UpdateRunePattern;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkHooks;


public class RunicTableBlock extends Block implements EntityBlock {
    public RunicTableBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }




    @Override
    public void onRemove(BlockState p_196243_1_, Level p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {
        if (!p_196243_2_.isClientSide){
            BlockEntity tile = p_196243_2_.getBlockEntity(p_196243_3_);
            if (tile instanceof RunicTableTileEntity t){
//                Containers.dropContents(p_196243_2_,p_196243_3_,(RunicTableTileEntity)tile);
                Containers.dropContents(p_196243_2_, p_196243_3_,new PhantomInventory(t.getInventory()));
            }
        }
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player pe, InteractionHand hand, BlockHitResult trace) {
        if (!world.isClientSide && (world.getBlockEntity(pos) instanceof RunicTableTileEntity tile) && (pe instanceof ServerPlayer serverPlayer) ){

            RunePattern pattern = new RunePattern(pe);
            if (!pattern.isPresent()){
                pattern = new RunePattern();
                pattern.generate();
                pattern.save(pe);
            }

            SolarForgePacketHandler.INSTANCE.sendTo(new UpdateRunePattern(pe,false),serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            boolean b = ProgressionHelper.getAllUnlockableFragments(pe) == null ;

            NetworkHooks.openGui((ServerPlayer) pe, new RunicTableContainer.Provider(tile,b),
                    (buf) -> {
                buf.writeBlockPos(pos);
                buf.writeBoolean(b);
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
