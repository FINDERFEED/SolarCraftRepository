package com.finderfeed.solarforge.for_future_library;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class FinderfeedMathHelper {

    public static boolean canSeeTileEntity(BlockEntity tile, Player player){
        Vec3 playerHeadPos = player.position().add(0,player.getStandingEyeHeight(player.getPose(),player.getDimensions(player.getPose())),0);
        Vec3 tileEntityPos = new Vec3(tile.getBlockPos().getX()+0.5,tile.getBlockPos().getY()+0.5,tile.getBlockPos().getZ()+0.5);
        ClipContext ctx = new ClipContext(playerHeadPos,tileEntityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null);
        BlockHitResult res = player.level.clip(ctx);
        if (equalsBlockPos(tile.getBlockPos(),res.getBlockPos())){
            return true;
        }
        return false;
    }

    public static boolean equalsBlockPos(BlockPos pos1, BlockPos pos2){
        return (pos1.getX() == pos2.getX()) && (pos1.getY() == pos2.getY()) && (pos1.getZ() == pos2.getZ());
    }

}
