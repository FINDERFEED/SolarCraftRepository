package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;
//TODO:finish this
public class CorruptedCavesFeature extends Feature<NoneFeatureConfiguration> {
    public CorruptedCavesFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }




    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel world = ctx.level();
        BlockPos pos = ctx.origin();
        ChunkPos chunk = world.getChunk(pos).getPos();
        int xcpos = chunk.getMiddleBlockX();
        int zcpos = chunk.getMiddleBlockZ();
        int yHeight = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,xcpos,zcpos);
        int coneRad = 7;
        double coneHeight = 40+world.getRandom().nextInt(30);
        this.generateMainCone(world,coneRad,coneHeight,xcpos,zcpos,yHeight);
        this.generateRotatedCone(world,6,15,90,new BlockPos(xcpos,yHeight-10,zcpos));


        return true;
    }



    private void generateChildrenCones(WorldGenLevel world,int coneRad,double coneHeight,int xcpos,int zcpos,int yHeight){

    }

    private void generateRotatedCone(WorldGenLevel world,int radius,int height,double yRot,BlockPos initialPos){
        for (int x = -radius;x <= radius;x++){
            for (int y = -radius;y <= radius;y++){
                for (int z = 0;z <= height;z++){
                    if (FinderfeedMathHelper.isInCone(x,z,y,radius,height)){
                        double[] xzCoords = FinderfeedMathHelper.rotatePointDegrees(x,z,yRot);
                        int[] xyz = {(int)xzCoords[0], y,(int)xzCoords[1]};
                        BlockPos genPos = initialPos.offset(xyz[0],xyz[1],xyz[2]);
                        if (world.getBlockState(genPos).isAir()) continue;
                        if (!isOnConeBorderChildrenCaves(x,z,y,radius,height)){
                            world.setBlock(genPos, Blocks.CAVE_AIR.defaultBlockState(), 2);
                        }else{
                            if (world.getRandom().nextDouble() < 0.1){
                                world.setBlock(genPos, BlocksRegistry.CORRUPTED_STONE.get().defaultBlockState(),2);
                            }else{
                                world.setBlock(genPos, Blocks.OBSIDIAN.defaultBlockState(),2);
                            }
                        }
                    }
                }
            }
        }
    }



    private void generateMainCone(WorldGenLevel world,int coneRad,double coneHeight,int xcpos,int zcpos,int yHeight){
        for (int x = -coneRad-7;x <= coneRad + 7;x++ ){
            for (int z = -coneRad-7;z <= coneRad + 7;z++ ){
                if (world.getRandom().nextDouble() < 0.25){
                    if (x*x + z*z <= Math.pow(coneRad + 7,2)){
                        int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,x + xcpos,z + zcpos)-1;
                        if (world.getRandom().nextDouble() > 0.2) {
                            world.setBlock(new BlockPos(x + xcpos, y, z + zcpos), Blocks.OBSIDIAN.defaultBlockState(), 2);
                        }else{
                            world.setBlock(new BlockPos(x + xcpos, y, z + zcpos), BlocksRegistry.CORRUPTED_STONE.get().defaultBlockState(), 2);
                        }
                    }
                }
            }
        }
        for (int x = -coneRad;x <= coneRad;x++){
            for (int z = -coneRad;z <= coneRad;z++){
                for (double y = 0;y <= coneHeight;y++){
                    if (FinderfeedMathHelper.isInCone(x,y,z,coneRad,coneHeight)){
                        BlockPos carvePos = new BlockPos(xcpos+x,yHeight-y+3,zcpos+z);
                        if (world.getBlockState(carvePos).isAir()) continue;
                        if (!isOnConeBorder(x,y,z,coneRad,coneHeight)) {
                            world.setBlock(carvePos, Blocks.CAVE_AIR.defaultBlockState(), 2);
                        }else{
                            if (world.getRandom().nextDouble() < 0.1){
                                world.setBlock(carvePos, BlocksRegistry.CORRUPTED_STONE.get().defaultBlockState(),2);
                            }else{
                                world.setBlock(carvePos, Blocks.OBSIDIAN.defaultBlockState(),2);
                            }
                        }
                    }
                }
            }
        }
    }




    private boolean isOnConeBorder(double x,double y,double z,double coneRadius,double coneHeight){
        double rad = Math.ceil(coneRadius*(1-y/coneHeight));
        double len = Math.sqrt(x*x + z*z);
        int sm = (int)Math.ceil(len);
        return Math.abs(sm - (int)(rad)) < 2f + Math.round(new Random().nextDouble())*0.1;
    }


    private boolean isOnConeBorderChildrenCaves(double x,double y,double z,double coneRadius,double coneHeight){
        double rad = Math.ceil(coneRadius*(1-y/coneHeight));
        double len = Math.sqrt(x*x + z*z);
        int sm = (int)Math.ceil(len);
        return Math.abs(sm - (int)(rad)) < 2f;
    }


}
