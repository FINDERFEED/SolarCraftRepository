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
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntitiesRegistry.RUNIC_TABLE_TILE.get().create(blockPos,blockState);
    }


}
