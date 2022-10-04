package com.finderfeed.solarforge.content.blocks.infusing_table_things;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.blocks.primitive.RunicEnergySaverBlock;
import com.finderfeed.solarforge.content.items.SolarNetworkBinder;
import com.finderfeed.solarforge.misc_things.SolarcraftDebugStick;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Consumer;



import javax.annotation.Nullable;

public class InfuserBlock extends RunicEnergySaverBlock implements EntityBlock {
    public InfuserBlock(Properties prop) {
        super(prop);
    }


    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    }






    @Override
    public void onRemove(BlockState p_196243_1_, Level p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {

        BlockEntity te = p_196243_2_.getBlockEntity(p_196243_3_);

        if (te instanceof InfuserTileEntity){
            InfuserTileEntity ent = (InfuserTileEntity) te;
            ItemStack stacks = ent.getItem(ent.inputSlot());
            popResource(p_196243_2_,p_196243_3_,stacks);
            stacks = ent.getItem(ent.outputSlot());
            popResource(p_196243_2_,p_196243_3_,stacks);
            ent.onTileRemove();
        }
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }




    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player user, InteractionHand hand, BlockHitResult rayTraceResult) {

        if (!world.isClientSide()) {

            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof InfuserTileEntity) {
                InfuserTileEntity tile = (InfuserTileEntity) entity;

                if (tile.getTier() == null){tile.calculateTier();}

                if (tile.getOwner() != null && (world.getPlayerByUUID(tile.getOwner()) == user)) {
                    if (!(user.getMainHandItem().getItem() instanceof SolarWandItem) && !(user.getMainHandItem().getItem() instanceof SolarNetworkBinder) && !(user.getMainHandItem().getItem() instanceof SolarcraftDebugStick)) {
                        entity.setChanged();
                        world.sendBlockUpdated(pos, state, state, 3);
                        Consumer<FriendlyByteBuf> cons = x -> {
                            x.writeBlockPos(pos);
                        };
                        NetworkHooks.openGui((ServerPlayer) user, new InfuserContainer.Provider(pos), cons);

                    }
                }else{
                    user.sendMessage(Component.literal("You are not the owner!").withStyle(ChatFormatting.RED),user.getUUID());
                }

            };
        }else{
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof InfuserTileEntity tile) {
                tile.calculateTier();
            }
        }


        if ((user.getItemInHand(hand).getItem() instanceof  SolarWandItem) || (user.getItemInHand(hand).getItem() instanceof  SolarNetworkBinder)  || (user.getItemInHand(hand).getItem() instanceof  SolarcraftDebugStick)){
            return super.use(state,world,pos,user,hand,rayTraceResult);
        }else{
            return InteractionResult.SUCCESS;
        }

    }



    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            InfuserTileEntity.tick(level,blockPos,blockState,(InfuserTileEntity) t);
        });
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SolarForge.INFUSING_STAND_BLOCKENTITY.get().create(blockPos,blockState);
    }
}




