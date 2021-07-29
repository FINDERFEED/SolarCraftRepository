package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool;

import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.SolarWandItem;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
import net.minecraftforge.event.world.BlockEvent;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import javax.annotation.Nullable;

public class InfusingPool extends Block implements EntityBlock {
    public InfusingPool(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }



    @Override
    public void onRemove(BlockState p_196243_1_, Level p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {

        BlockEntity te = p_196243_2_.getBlockEntity(p_196243_3_);

        if (te instanceof InfusingPoolTileEntity){
            InfusingPoolTileEntity ent = (InfusingPoolTileEntity) te;
            ItemStack stacks = ent.getItems().get(0);
            popResource(p_196243_2_,p_196243_3_,stacks);
        }
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player user, InteractionHand hand, BlockHitResult rayTraceResult) {
        if (!world.isClientSide()) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof InfusingPoolTileEntity) {
                InfusingPoolTileEntity tile = (InfusingPoolTileEntity) entity;
                if (!(user.getItemInHand(hand).getItem() instanceof SolarWandItem) && hand == InteractionHand.MAIN_HAND && !user.isCrouching()) {
                    if (tile.isEmpty() ) {

                        ItemStack stack = user.getItemInHand(hand);
                        ItemStack stacktoplace = stack.copy();
                        stacktoplace.setCount(1);
                        tile.getItems().set(0,stacktoplace);
                        stack.grow(-1);

                        return InteractionResult.SUCCESS;
                    }else{
                        ItemStack stack = tile.getItem(0);
                        tile.getItems().clear();
                        popResource(world,pos,stack);

                        return InteractionResult.SUCCESS;
                    }

                }

            }
        }
        return InteractionResult.CONSUME;
    }

    public static void placeBlockEvent(final BlockEvent.EntityPlaceEvent event){

        if ((event.getPlacedAgainst().getBlock() instanceof InfusingPool) && event.getEntity() instanceof Player){
            if (!((Player)(event.getEntity())).isCrouching()) {
                event.setCanceled(true);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntitiesRegistry.INFUSING_POOL_BLOCKENTITY.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (((level, blockPos, blockState, t) -> {
            InfusingPoolTileEntity.tick(level,blockPos,blockState,(InfusingPoolTileEntity) t);
        }));
    }
}
