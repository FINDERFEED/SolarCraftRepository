package com.finderfeed.solarforge.misc_things;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;


import net.minecraft.world.item.Item.Properties;

public class VoidBlockWand extends Item {

    public BlockState state;
    public BlockPos posOne;
    public BlockPos posTwo;
    public int mode = 1;
    public VoidBlockWand(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        Player player = ctx.getPlayer();

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
                                    world.setBlock(new BlockPos(x, y, z), Blocks.STRUCTURE_VOID.defaultBlockState(), 3);

                                }
                            } else if (mode == 2) {
                                //if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.STRUCTURE_VOID) {
                                    world.destroyBlock(new BlockPos(x, y, z), false);

                                //}
                            }else if (mode == 3){
                                    world.setBlock(new BlockPos(x, y, z), state, 3);



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
        return InteractionResult.CONSUME;
    }
}
