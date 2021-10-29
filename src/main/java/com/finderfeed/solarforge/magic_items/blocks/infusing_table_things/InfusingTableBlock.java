package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.for_future_library.OwnedBlock;
import com.finderfeed.solarforge.magic_items.items.SolarNetworkBinder;
import com.finderfeed.solarforge.misc_things.SolarcraftDebugStick;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;

public class InfusingTableBlock extends Block implements EntityBlock {
    public InfusingTableBlock(Properties prop) {
        super(prop);
    }


    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    }






    @Override
    public void onRemove(BlockState p_196243_1_, Level p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {

        BlockEntity te = p_196243_2_.getBlockEntity(p_196243_3_);

        if (te instanceof InfusingTableTileEntity){
            InfusingTableTileEntity ent = (InfusingTableTileEntity) te;
            ItemStack stacks = ent.getItems().get(0);
            popResource(p_196243_2_,p_196243_3_,stacks);
            stacks = ent.getItems().get(9);
            popResource(p_196243_2_,p_196243_3_,stacks);
            ent.onTileRemove();
        }
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }




    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player user, InteractionHand hand, BlockHitResult rayTraceResult) {

        if (!world.isClientSide()) {

            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof InfusingTableTileEntity) {
                InfusingTableTileEntity tile = (InfusingTableTileEntity) entity;
                if (!(user.getMainHandItem().getItem() instanceof SolarWandItem) && !(user.getMainHandItem().getItem() instanceof SolarNetworkBinder) && !(user.getMainHandItem().getItem() instanceof SolarcraftDebugStick)) {
                    entity.setChanged();
                    world.sendBlockUpdated(pos,state,state,3);
                    Consumer<FriendlyByteBuf> cons = x -> { x.writeBlockPos(pos);
                    };
                    NetworkHooks.openGui((ServerPlayer) user, (InfusingTableTileEntity) entity, cons);

                }

            };
        }
        if ((user.getItemInHand(hand).getItem() instanceof  SolarWandItem) || (user.getItemInHand(hand).getItem() instanceof  SolarNetworkBinder)  || (user.getItemInHand(hand).getItem() instanceof  SolarcraftDebugStick)){
            return super.use(state,world,pos,user,hand,rayTraceResult);
        }else{
            return InteractionResult.CONSUME;
        }

    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            InfusingTableTileEntity.tick(level,blockPos,blockState,(InfusingTableTileEntity) t);
        });
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SolarForge.INFUSING_STAND_BLOCKENTITY.get().create(blockPos,blockState);
    }
}




