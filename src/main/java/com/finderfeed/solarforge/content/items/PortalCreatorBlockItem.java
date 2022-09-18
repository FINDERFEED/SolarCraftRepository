package com.finderfeed.solarforge.content.items;

import com.finderfeed.solarforge.helpers.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;

public class PortalCreatorBlockItem extends BlockItem {
    public PortalCreatorBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }




    @Override
    protected boolean canPlace(BlockPlaceContext ctx, BlockState p_40612_) {

        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Direction dir = ctx.getClickedFace();

        if ((dir == Direction.UP) ){
            BlockPos normalPos = pos.above();
            Collection<BlockPos> positions = Helpers.getSurroundingBlockPositionsVertical(normalPos,Direction.SOUTH);
            for (BlockPos posi : positions){
                if (world.getBlockState(posi).getBlock() != Blocks.AIR){
                    return false;
                }
            }
            if (world.getBlockState(normalPos).getBlock() != Blocks.AIR){
                return false;
            }
        }else if (dir == Direction.NORTH){
            BlockPos normalPos = pos.north().above();
            Collection<BlockPos> positions = Helpers.getSurroundingBlockPositionsVertical(normalPos,Direction.SOUTH);
            for (BlockPos posi : positions){
                if (world.getBlockState(posi).getBlock() != Blocks.AIR){
                    return false;
                }
            }
            if (world.getBlockState(normalPos).getBlock() != Blocks.AIR){
                return false;
            }
        }else if (dir == Direction.SOUTH){
            BlockPos normalPos = pos.south().above();
            Collection<BlockPos> positions = Helpers.getSurroundingBlockPositionsVertical(normalPos,Direction.SOUTH);
            for (BlockPos posi : positions){
                if (world.getBlockState(posi).getBlock() != Blocks.AIR){
                    return false;
                }
            }
            if (world.getBlockState(normalPos).getBlock() != Blocks.AIR){
                return false;
            }
        }else{
            return false;
        }

        return true;
    }
}
