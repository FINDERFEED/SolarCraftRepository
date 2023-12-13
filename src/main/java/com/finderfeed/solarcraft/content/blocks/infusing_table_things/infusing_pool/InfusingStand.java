package com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool;

import com.finderfeed.solarcraft.content.items.solar_wand.SolarWandItem;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.BlockEvent;


import javax.annotation.Nullable;

public class InfusingStand extends Block implements EntityBlock {
    public InfusingStand(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }



    @Override
    public void onRemove(BlockState p_196243_1_, Level p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {

        BlockEntity te = p_196243_2_.getBlockEntity(p_196243_3_);

        if (te instanceof InfusingStandTileEntity){
            InfusingStandTileEntity ent = (InfusingStandTileEntity) te;
            ItemStack stacks = ent.getStackInSlot(0);
            popResource(p_196243_2_,p_196243_3_,stacks);
        }
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player user, InteractionHand hand, BlockHitResult rayTraceResult) {
        if (!world.isClientSide()) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof InfusingStandTileEntity) {
                InfusingStandTileEntity tile = (InfusingStandTileEntity) entity;
                if (!(user.getItemInHand(hand).getItem() instanceof SolarWandItem) && hand == InteractionHand.MAIN_HAND && !user.isCrouching()) {

                    handleContainerClick(state,world,pos,user,hand,tile);
                    return InteractionResult.SUCCESS;
                }


            }
        }
        return InteractionResult.CONSUME;
    }



    private static void handleContainerClick(BlockState state, Level world, BlockPos pos, Player user, InteractionHand hand, InfusingStandTileEntity tile){
        if (tile.getStackInSlot(0).isEmpty()) {
            ItemStack stack = user.getItemInHand(hand);
            ItemStack stacktoplace = stack.copy();
            stacktoplace.setCount(1);
            tile.setStackInSlot(0,stacktoplace);
            stack.grow(-1);
        }else{
            ItemStack stack = user.getItemInHand(hand);
            if (tile.getStackInSlot(0).getItem() == stack.getItem()){
                ItemStack containerStack = tile.getStackInSlot(0);
                if (containerStack.getMaxStackSize() != 1){
                    if (containerStack.getCount() > 1){
                        popResource(world,pos,containerStack);
                        tile.setStackInSlot(0,ItemStack.EMPTY);
                    }else{
                        if (containerStack.getCount() + stack.getCount() <= containerStack.getMaxStackSize()){
                            containerStack.grow(stack.getCount());
                            stack.grow(-stack.getCount());
                        }else{
                            int raznitsa = containerStack.getMaxStackSize() - containerStack.getCount();
                            stack.grow(-raznitsa);
                            containerStack.setCount(containerStack.getMaxStackSize());
                        }
                    }
                }else{
                    popResource(world,pos,containerStack);
                    tile.setStackInSlot(0,ItemStack.EMPTY);
                }
            }else{
                popResource(world,pos,tile.getStackInSlot(0));
                tile.setStackInSlot(0,ItemStack.EMPTY);
            }

        }
        tile.setChanged();
        world.sendBlockUpdated(pos, state, state, 3);
    }

    public static void placeBlockEvent(final BlockEvent.EntityPlaceEvent event){


        if ((event.getPlacedAgainst().getBlock() instanceof InfusingStand) && event.getEntity() instanceof Player){
            if (!((Player)(event.getEntity())).isCrouching()) {
                event.setCanceled(true);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SolarcraftTileEntityTypes.INFUSING_POOL_BLOCKENTITY.get().create(blockPos,blockState);
    }

//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
//        return (((level, blockPos, blockState, t) -> {
//            if (level.getGameTime() % 20 == 1) {
//                InfusingStandTileEntity.tick(level, blockPos, blockState, (InfusingStandTileEntity) t);
//            }
//        }));
//    }
}
