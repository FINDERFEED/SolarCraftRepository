package com.finderfeed.solarforge.for_future_library.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.CallbackI;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

public class FinderfeedMathHelper {

    public static final Function<Double,Double> SQUARE = (x)->x*x;
    public static final Function<Double,Double> FLIP = (x)->1-x;

    public static double[] polarToCartesian(double radius,double angle){
        return new double[]{radius*Math.cos(angle),radius*Math.sin(angle)};
    }


    public static double[] rotatePointRadians(double x, double y, double angle){
        return new double[]{
                x * Math.cos(angle) - y * Math.sin(angle),
                x * Math.sin(angle) + y * Math.cos(angle)
        };
    }
    public static double[] rotatePointDegrees(double x, double y, double angle){
        double g = Math.toRadians(angle);
        return new double[]{
                x * Math.cos(g) - y * Math.sin(g),
                x * Math.sin(g) + y * Math.cos(g)
        };
    }


    public static int randomPlusMinus(){
        Random rnd = new Random();
        return rnd.nextInt(2) == 0 ? 1 : -1;
    }


    public static double clamp(double min,double main, double max){
        if (main < min){
            return min;
        }else {
            return Math.min(main, max);
        }
    }

    public static int clamp(int min,int main, int max){
        if (main < min){
            return min;
        }else {
            return Math.min(main, max);
        }
    }

    public static int[] intToRgba(int color){
        int r = (color >> 16) & 0xff;
        int g = (color >>  8) & 0xff;
        int b = (color      ) & 0xff;
        int a = (color >> 24) & 0xff;
        return new int[]{r,g,b,a};
    }
    public static int rgbaToInt(int[] rgba){
        int color = (rgba[3] & 0xff) << 24 | (rgba[2] & 0xff) << 16 | (rgba[1] & 0xff) << 8 | (rgba[0] & 0xff);
        return color;
    }

    public static double getDistanceBetween(Vec3 tile,Vec3 start){
        return new Vec3(tile.x - start.x,tile.y - start.y,tile.z - start.z).length();
    }

    public static double getDistanceBetween(BlockPos tile,BlockPos start){
        Vec3 startPos = new Vec3(start.getX()+0.5,start.getY()+0.5,start.getZ()+0.5);
        Vec3 tileEntityPos = new Vec3(tile.getX()+0.5,tile.getY()+0.5,tile.getZ()+0.5);
        return new Vec3(tileEntityPos.x - startPos.x,tileEntityPos.y - startPos.y,tileEntityPos.z - startPos.z).length();
    }

    public static double getDistanceBetween(BlockEntity tile,BlockEntity start){
        Vec3 startPos = new Vec3(start.getBlockPos().getX()+0.5,start.getBlockPos().getY()+0.5,start.getBlockPos().getZ()+0.5);
        Vec3 tileEntityPos = new Vec3(tile.getBlockPos().getX()+0.5,tile.getBlockPos().getY()+0.5,tile.getBlockPos().getZ()+0.5);
        return new Vec3(tileEntityPos.x - startPos.x,tileEntityPos.y - startPos.y,tileEntityPos.z - startPos.z).length();
    }


    public static boolean canSeeTileEntity(BlockEntity tile, BlockEntity start,double radius){
        Vec3 startPos = new Vec3(start.getBlockPos().getX()+0.5,start.getBlockPos().getY()+0.5,start.getBlockPos().getZ()+0.5);
        Vec3 tileEntityPos = new Vec3(tile.getBlockPos().getX()+0.5,tile.getBlockPos().getY()+0.5,tile.getBlockPos().getZ()+0.5);

        Vec3 between = new Vec3(tileEntityPos.x - startPos.x,tileEntityPos.y - startPos.y,tileEntityPos.z - startPos.z);


        if (between.length() <= radius) {
            ClipContext ctx = new ClipContext(startPos.add(between.normalize().x,between.normalize().y,between.normalize().z), tileEntityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null);
            BlockHitResult res = start.getLevel().clip(ctx);
            if (equalsBlockPos(tile.getBlockPos(), res.getBlockPos())) {
                return true;
            }
        }
        return false;
    }


    public static boolean canSeeTileEntity(BlockPos tile, BlockPos start,double radius,Level world){
        Vec3 startPos = new Vec3(start.getX()+0.5,start.getY()+0.5,start.getZ()+0.5);
        Vec3 tileEntityPos = new Vec3(tile.getX()+0.5,tile.getY()+0.5,tile.getZ()+0.5);

        Vec3 between = new Vec3(tileEntityPos.x - startPos.x,tileEntityPos.y - startPos.y,tileEntityPos.z - startPos.z);


        if (between.length() <= radius) {
            ClipContext ctx = new ClipContext(startPos.add(between.normalize().x,between.normalize().y,between.normalize().z), tileEntityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null);
            BlockHitResult res = world.clip(ctx);
            if (equalsBlockPos(tile, res.getBlockPos())) {
                return true;
            }
        }
        return false;
    }
    public static boolean canSee(BlockPos tile, BlockPos start, double radius, Level world){
        Vec3 startPos = new Vec3(start.getX()+0.5,start.getY()+0.5,start.getZ()+0.5);
        Vec3 tileEntityPos = new Vec3(tile.getX()+0.5,tile.getY()+0.5,tile.getZ()+0.5);

        Vec3 between = new Vec3(tileEntityPos.x - startPos.x,tileEntityPos.y - startPos.y,tileEntityPos.z - startPos.z);


        if (between.length() <= radius) {
            ClipContext ctx = new ClipContext(startPos.add(between.normalize().x,between.normalize().y,between.normalize().z), tileEntityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null);
            BlockHitResult res = world.clip(ctx);
            if (equalsBlockPos(tile, res.getBlockPos())) {
                return true;
            }
        }
        return false;
    }


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

    public static boolean isInCircle(double x,double y,double radius){
        return x*x + y*y <= radius*radius;
    }


    public static class TileEntityThings{
        public static Vec3 getTileEntityCenter(BlockEntity entity){
            return new Vec3(entity.getBlockPos().getX() + 0.5,entity.getBlockPos().getY() + 0.5,entity.getBlockPos().getZ() + 0.5);
        }

        public static Vec3 getTileEntityCenter(BlockPos entity){
            return new Vec3(entity.getX() + 0.5,entity.getY() + 0.5,entity.getZ() + 0.5);
        }
    }




    public static class PlayerThings{
        public static boolean doPlayerHasItem(Inventory inv, ItemStack item){
            Item item1 = item.getItem();
            for (int i = 0; i < inv.getContainerSize();i++){
                if (inv.getItem(i).getItem() == item1){
                    return true;
                }
            }
            return false;
        }
        public static boolean doPlayerHasItem(Inventory inv, Item item1){
            for (int i = 0; i < inv.getContainerSize();i++){
                if (inv.getItem(i).getItem() == item1){
                    return true;
                }
            }
            return false;
        }
    }

    public static class TargetFinding{

        public static <V extends Entity> List<V> getAllValidTargetsFromBP(Class<V> toFind,double radius,Level world,BlockPos pos,Predicate<V> more){
            Predicate<V> sort = (entity)->{
                if (more.test(entity)) {
                    if (getDistanceBetween(pos, entity.blockPosition()) <= radius) {
                        return canSee(entity.blockPosition(), pos, radius, world);
                    }
                }
                return false;
            };
            return world.getEntitiesOfClass(toFind,new AABB(-radius,-radius,-radius,radius,radius,radius).move(TileEntityThings.getTileEntityCenter(pos)),sort);
        }

        public static <V extends Entity> List<V> getAllValidTargetsFromVec(Class<V> toFind,double radius,Level world,Vec3 pos,Predicate<V> more){
            Predicate<V> sort = (entity)->{
                if (more.test(entity)) {
                    if (getDistanceBetween(pos, entity.position().add(0, entity.getBbHeight() / 2, 0)) <= radius) {
                        return canSee(new BlockPos(Math.floor(entity.getX()), Math.floor(entity.getY() + entity.getBbHeight()*1.1 / 2), Math.floor(entity.getZ())),
                                new BlockPos(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z)), radius, world);
                    }
                }
                return false;
            };
            return world.getEntitiesOfClass(toFind,new AABB(-radius,-radius,-radius,radius,radius,radius).move(pos),sort);
        }
    }
}
