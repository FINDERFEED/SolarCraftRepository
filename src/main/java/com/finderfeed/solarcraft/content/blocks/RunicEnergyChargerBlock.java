package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.RunicEnergyChargerTileEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.RunicEnergyChargerContainer;
import com.finderfeed.solarcraft.content.blocks.primitive.RunicEnergySaverBlock;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RunicEnergyChargerBlock extends RunicEnergySaverBlock implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public RunicEnergyChargerBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SCTileEntities.RUNIC_ENERGY_CHARGER.get().create(pos,state);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(FACING,ctx.getHorizontalDirection());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
       if (!world.isClientSide && hand == InteractionHand.MAIN_HAND){
            if (world.getBlockEntity(pos) instanceof RunicEnergyChargerTileEntity tile) {
                Helpers.updateTile(tile);
                NetworkHooks.openScreen((ServerPlayer) player, new RunicEnergyChargerContainer.Provider(pos), (buf) -> {
                    buf.writeBlockPos(pos);
                });
                return InteractionResult.SUCCESS;
            }

       }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (a,b,c,d)->{
            RunicEnergyChargerTileEntity.tick((RunicEnergyChargerTileEntity) d,a,c,b);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> def) {
        super.createBlockStateDefinition(def);
        def.add(FACING);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder context) {
        List<ItemStack> drops = super.getDrops(state,context);

        if (context.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof RunicEnergyChargerTileEntity charger){
            ItemStack i;
//            charger.reviveCaps();
            if (!(i = charger.getStackInSlot(0)).isEmpty()){
                drops.add(i);
            }
            ItemStack i1;
            if (!(i1 = charger.getStackInSlot(1)).isEmpty()){
                drops.add(i1);
            }
//            charger.invalidateCaps();
        }
        return drops;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
        super.onRemove(state, level, pos, p_60518_, p_60519_);
    }
}
