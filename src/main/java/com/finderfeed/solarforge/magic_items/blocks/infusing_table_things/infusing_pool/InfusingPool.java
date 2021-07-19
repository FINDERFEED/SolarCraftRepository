package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool;

import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.SolarWandItem;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class InfusingPool extends Block {
    public InfusingPool(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return TileEntitiesRegistry.INFUSING_POOL_BLOCKENTITY.get().create();
    }

    @Override
    public void onRemove(BlockState p_196243_1_, World p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {

        TileEntity te = p_196243_2_.getBlockEntity(p_196243_3_);

        if (te instanceof InfusingPoolTileEntity){
            InfusingPoolTileEntity ent = (InfusingPoolTileEntity) te;
            ItemStack stacks = ent.getItems().get(0);
            popResource(p_196243_2_,p_196243_3_,stacks);
        }
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity user, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isClientSide()) {
            TileEntity entity = world.getBlockEntity(pos);
            if (entity instanceof InfusingPoolTileEntity) {
                InfusingPoolTileEntity tile = (InfusingPoolTileEntity) entity;
                if (!(user.getItemInHand(hand).getItem() instanceof SolarWandItem) && hand == Hand.MAIN_HAND && !user.isCrouching()) {
                    if (tile.isEmpty() ) {

                        ItemStack stack = user.getItemInHand(hand);
                        ItemStack stacktoplace = stack.copy();
                        stacktoplace.setCount(1);
                        tile.getItems().set(0,stacktoplace);
                        stack.grow(-1);

                        return ActionResultType.SUCCESS;
                    }else{
                        ItemStack stack = tile.getItem(0);
                        tile.getItems().clear();
                        popResource(world,pos,stack);

                        return ActionResultType.SUCCESS;
                    }

                }

            }
        }
        return ActionResultType.CONSUME;
    }

    public static void placeBlockEvent(final BlockEvent.EntityPlaceEvent event){

        if ((event.getPlacedAgainst().getBlock() instanceof InfusingPool) && event.getEntity() instanceof PlayerEntity){
            if (!((PlayerEntity)(event.getEntity())).isCrouching()) {
                event.setCanceled(true);
            }
        }
    }

}
