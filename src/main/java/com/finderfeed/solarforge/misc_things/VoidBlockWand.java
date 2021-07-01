package com.finderfeed.solarforge.misc_things;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class VoidBlockWand extends Item {

    public BlockState state;
    public BlockPos posOne;
    public BlockPos posTwo;
    public int mode = 1;
    public VoidBlockWand(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResultType useOn(ItemUseContext ctx) {
        World world = ctx.getLevel();
        PlayerEntity player = ctx.getPlayer();

        if (!world.isClientSide){

            if (world.getBlockState(ctx.getClickedPos()).getBlock() != Blocks.DIRT && world.getBlockState(ctx.getClickedPos()).getBlock() != Blocks.COARSE_DIRT && world.getBlockState(ctx.getClickedPos()).getBlock() != Blocks.BEDROCK) {
                if (player.isCrouching()) {
                    posTwo = ctx.getClickedPos();
                    System.out.println(posTwo);
                    state = world.getBlockState(ctx.getClickedPos());
                } else {
                    posOne = ctx.getClickedPos();
                    System.out.println(posOne);
                }
            }else if (world.getBlockState(ctx.getClickedPos()).getBlock() == Blocks.COARSE_DIRT ) {

                int xm = Math.min(posOne.getX(), posTwo.getX());
                int ym = Math.min(posOne.getY(), posTwo.getY());
                int zm = Math.min(posOne.getZ(), posTwo.getZ());
                int xmax = Math.max(posOne.getX(), posTwo.getX());
                int ymax = Math.max(posOne.getY(), posTwo.getY());
                int zmax = Math.max(posOne.getZ(), posTwo.getZ());
                for (int x = xm; x <= xmax; x++) {

                    for (int y = ym; y <= ymax; y++) {

                        for (int z = zm; z <= zmax; z++) {
                            if (mode == 1) {
                                if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.AIR) {
                                    world.setBlock(new BlockPos(x, y, z), Blocks.STRUCTURE_VOID.defaultBlockState(), Constants.BlockFlags.DEFAULT);

                                }
                            } else if (mode == 2) {
                                //if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.STRUCTURE_VOID) {
                                    world.destroyBlock(new BlockPos(x, y, z), false);

                                //}
                            }else if (mode == 3){
                                    world.setBlock(new BlockPos(x, y, z), state, Constants.BlockFlags.DEFAULT);



                            }
                        }
                    }
                }
            }
            else if (world.getBlockState(ctx.getClickedPos()).getBlock() == Blocks.BEDROCK ) {

                if (mode == 1){
                    mode = 2;
                }else if (mode == 2){
                    mode = 3;
                }else if (mode == 3){
                    mode = 1;
                }
                System.out.println(mode);
            } else {
                posOne = new BlockPos(0,0,0);
                posTwo = new BlockPos(0,0,0);

            }
        }
        return ActionResultType.CONSUME;
    }
}
