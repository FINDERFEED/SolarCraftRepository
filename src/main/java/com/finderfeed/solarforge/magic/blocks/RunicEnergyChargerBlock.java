package com.finderfeed.solarforge.magic.blocks;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.blocks.blockentities.RunicEnergyChargerTileEntity;
import com.finderfeed.solarforge.magic.blocks.blockentities.containers.RunicEnergyChargerContainer;
import com.finderfeed.solarforge.magic.blocks.primitive.RunicEnergySaverBlock;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RunicEnergyChargerBlock extends RunicEnergySaverBlock implements EntityBlock {
    public RunicEnergyChargerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileEntitiesRegistry.RUNIC_ENERGY_CHARGER.get().create(pos,state);
    }


    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
       if (!world.isClientSide && hand == InteractionHand.MAIN_HAND){
            if (world.getBlockEntity(pos) instanceof RunicEnergyChargerTileEntity tile) {
                Helpers.updateTile(tile);
                NetworkHooks.openGui((ServerPlayer) player, new RunicEnergyChargerContainer.Provider(pos), (buf) -> {
                    buf.writeBlockPos(pos);
                });
                return InteractionResult.CONSUME;
            }

       }
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (a,b,c,d)->{
            RunicEnergyChargerTileEntity.tick((RunicEnergyChargerTileEntity) d,a,c,b);
        };
    }



}
