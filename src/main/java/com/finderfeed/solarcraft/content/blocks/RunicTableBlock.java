package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.RunicTableContainer;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.RunePattern;
import com.finderfeed.solarcraft.misc_things.PhantomInventory;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.UpdateRunePattern;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
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


public class RunicTableBlock extends Block implements EntityBlock {
    public RunicTableBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }




    @Override
    public void onRemove(BlockState state1, Level world, BlockPos pos, BlockState state, boolean p_196243_5_) {
        if (!world.isClientSide){
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof RunicTableTileEntity t){
                Containers.dropContents(world, pos,new PhantomInventory(t.getInventory()));
            }
        }
        super.onRemove(state1, world, pos, state, p_196243_5_);
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

            FDPacketUtil.sendToPlayer(serverPlayer,new UpdateRunePattern(pe,false));
//            SCPacketHandler.INSTANCE.sendTo(new UpdateRunePattern(pe,false),serverPlayer.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
            boolean b = AncientFragmentHelper.getAllUnlockableFragments(pe) == null ;

            serverPlayer.openMenu(new RunicTableContainer.Provider(tile,b),
                    (buf) -> {
                buf.writeBlockPos(pos);
                buf.writeBoolean(b);
            }
            );
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SCTileEntities.RUNIC_TABLE_TILE.get().create(blockPos,blockState);
    }


}
