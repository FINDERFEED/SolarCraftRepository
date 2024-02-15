package com.finderfeed.solarcraft.local_library.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
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
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

public class FDMathHelper {

    public static final Random RANDOM = new Random();
    public static final Function<Double,Double> SQUARE = (x)->x*x;
    public static final Function<Double,Double> FLIP = (x)->1-x;


    //.........x1.........x2.......
    //.........x2.........x1.......
    public static float lerpBetweenInDirection(float x1,float x2,float percent){
        if (x1 < x2){
            return lerp(x1,x2,percent);
        }else{
            return lerp(x2,x1,percent);
        }
    }

    //-y1/z1 = *z2;
    public static Vec3 findRandomNormalVec(Vec3 v){
        double z = -v.y/v.z;
        return new Vec3(0,1,z).normalize();
    }

    public static boolean isBetweenValues(float value,float val1,float val2){
        return value >= val1 && value <= val2;
    }
    public static boolean isBetweenValuesBeginInclusive(float value,float val1,float val2){
        return value >= val1 && value < val2;
    }

    public static float easeInOut(float p){
        if (p <= 0.5){
            return 2 * p * p;
        }else{
            return -2 * (p - 1) * (p - 1) + 1;
        }
    }

    public static boolean isInEllipse(float x,float y,float z,float ellipseRad,float ellipseDepth){
        if (ellipseRad == 0 || ellipseDepth == 0){
            return false;
        }
        boolean a = (x*x) / (ellipseRad * ellipseRad) +
                (y*y) / (ellipseDepth * ellipseDepth) +
                (z*z) / (ellipseRad * ellipseRad) <= 1;
        return a;
    }
    public static boolean isOutOfEllipse(float x,float y,float z,float ellipseRad,float ellipseDepth){
        if (ellipseRad == 0 || ellipseDepth == 0){
            return true;
        }
        boolean a = (x*x) / (ellipseRad * ellipseRad) +
                (y*y) / (ellipseDepth * ellipseDepth) +
                (z*z) / (ellipseRad * ellipseRad) > 1;
        return a;
    }
    public static boolean isBetweenEllipses(float x,float y,float z,float inRad,float inDepth,float outRad,float outDepth){
        return isOutOfEllipse(x,y,z,inRad,inDepth) && isInEllipse(x,y,z,outRad,outDepth);
    }

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
        return RANDOM.nextInt(2) == 0 ? 1 : -1;
    }


    public static double clamp(double min,double main, double max){
        if (main < min){
            return min;
        }else {
            return Math.min(main, max);
        }
    }
    public static float clamp(float min,float main, float max){
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

    public static int getOverflow(int min, int main, int max){
        if (main < min){
            return min - main;
        }else if (main > max){
            return main - max;
        }else{
            return 0;
        }
    }

    /**
     * returns how much is left to the boundaries if main + delta exceeds one of them, else returns the delta
     */
    //   min        x         max
    public static int getUnderflow(int min, int main, int max,int delta){
        if (main > max || main < min) return 0;
        int value = main + delta;
        if (value > max){
            return max - main;
        }else if (value < min){
            return min - main;
        }else{
            return delta;
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

    public static int[] colorInterpolationRGB(int initColor,int endColor,float f1){
        int[] rgba = intToRgba(initColor);
        int[] frgba = intToRgba(endColor);
        int[] newColor = {0,0,0,255};
        newColor[0] = (int)Mth.lerp(f1,rgba[0],frgba[0]);
        newColor[1] = (int)Mth.lerp(f1,rgba[1],frgba[1]);
        newColor[2] = (int)Mth.lerp(f1,rgba[2],frgba[2]);
        return newColor;
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
            ClipContext ctx = new ClipContext(startPos.add(between.normalize().x,between.normalize().y,between.normalize().z), tileEntityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
            BlockHitResult res = start.getLevel().clip(ctx);
            if (equalsBlockPos(tile.getBlockPos(), res.getBlockPos())) {
                return true;
            }
        }
        return false;
    }
    public static boolean canSeeBlock(BlockPos tile, Player player){
        Vec3 playerHeadPos = player.position().add(0,player.getStandingEyeHeight(player.getPose(),player.getDimensions(player.getPose())),0);
        Vec3 tileEntityPos = new Vec3(tile.getX()+0.5,tile.getY()+0.5,tile.getZ()+0.5);
        ClipContext ctx = new ClipContext(playerHeadPos,tileEntityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,CollisionContext.empty());
        BlockHitResult res = player.level().clip(ctx);
        if (equalsBlockPos(tile,res.getBlockPos())){
            return true;
        }
        return false;
    }


    public static boolean canSeeTileEntity(BlockPos tile, BlockPos start,double radius,Level world){
        Vec3 startPos = new Vec3(start.getX()+0.5,start.getY()+0.5,start.getZ()+0.5);
        Vec3 tileEntityPos = new Vec3(tile.getX()+0.5,tile.getY()+0.5,tile.getZ()+0.5);

        Vec3 between = new Vec3(tileEntityPos.x - startPos.x,tileEntityPos.y - startPos.y,tileEntityPos.z - startPos.z);


        if (between.length() <= radius) {
            ClipContext ctx = new ClipContext(startPos.add(between.normalize().x,between.normalize().y,between.normalize().z), tileEntityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,CollisionContext.empty());
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
            ClipContext ctx = new ClipContext(startPos.add(between.normalize().x,between.normalize().y,between.normalize().z), tileEntityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,CollisionContext.empty());
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
        ClipContext ctx = new ClipContext(playerHeadPos,tileEntityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,CollisionContext.empty());
        BlockHitResult res = player.level().clip(ctx);
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

    public static boolean isInCone(double x,double y,double z,double coneRadius,double coneHeight){
        if (y > coneHeight) return false;
        double rad = (coneRadius*(1-y/coneHeight));
        return x*x + z*z <= rad*rad;
    }

    public static double lerp(double i,double e,double f){
        return i + (e - i) * f;
    }
    public static float lerp(float i,float e,float f){
        return i + (e - i) * f;
    }

    public static float lerpThroughBoundaries(float bVal,float tVal,float percent,float bottom,float top){
        float fullDist = (bVal - bottom) + (top - tVal);
        float dist = fullDist * percent;
        float diff = dist - bVal;
        if (diff > 0){
            float d = top - diff;
            return d;
        }else{
            return bVal - dist;
        }
    }

    public static Vec3 lerpv3(Vec3 init,Vec3 end,float i){
        return new Vec3(
                lerp(init.x,end.x,i),
                lerp(init.y,end.y,i),
                lerp(init.z,end.z,i)
        );
    }

    public static float[] rgbToHsv(int[] rgb){
        return rgbToHsv(rgb[0]/255f,rgb[1]/255f,rgb[2]/255f);
    }

    public static float[] rgbToHsv(int rgb){
        int r = rgb & 0xff0000 >> 16;
        int g = rgb & 0x00ff00 >> 8;
        int b = rgb & 0x0000ff;
        return rgbToHsv(r/255f,g/255f,b/255f);
    }

    public static float[] rgbToHsv(float r,float g,float b){
       float max = Math.max(r,Math.max(g,b));
       float min = Math.min(r,Math.min(g,b));
       float delta = max - min;
       float h;
       if (delta == 0){
           h = 0;
       }else if (max == r){
           h = 60 * ((g-b) / delta % 6);
       }else if (max == g){
           h = 60 * ((b-r) / delta + 2);
       }else {
           h = 60 * ((r-g) / delta + 4);
       }
       if (h < 0) h += 360;
       float s = max == 0 ? 0 : delta/max;
       return new float[]{h,s,max};
    }

    public static int[] hsvToRgb(float[] hsv){
        return hsvToRgb(hsv[0],hsv[1],hsv[2]);
    }

    public static int[] hsvToRgb(float h,float s,float v){
        float c = v * s;
        float x = c * (1 - Math.abs((h/60) % 2 - 1));
        float m = v - c;
        float r;
        float g;
        float b;
        if (h >= 0 && h < 60){
            r = c;g = x;b = 0;
        }else if (h >= 60 && h < 120){
            r = x;g = c;b = 0;
        }else if (h >= 120 && h < 180){
            r = 0;g = c;b = x;
        }else if (h >= 180 && h < 240){
            r = 0;g = x;b = c;
        }else if (h >= 240 && h < 300){
            r = x;g = 0;b = c;
        }else {
            r = c;g = 0;b = x;
        }

        return new int[]{
                Math.round((r + m) * 255),
                Math.round((g + m) * 255),
                Math.round((b + m) * 255)
        };
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
                        return canSee(new BlockPos((int)Math.floor(entity.getX()), (int)Math.floor(entity.getY() + entity.getBbHeight()*1.1 / 2), (int)Math.floor(entity.getZ())),
                                new BlockPos((int)Math.floor(pos.x), (int)Math.floor(pos.y), (int)Math.floor(pos.z)), radius, world);
                    }
                }
                return false;
            };
            return world.getEntitiesOfClass(toFind,new AABB(-radius,-radius,-radius,radius,radius,radius).move(pos),sort);
        }
    }
}
