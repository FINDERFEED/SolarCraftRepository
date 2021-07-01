package com.finderfeed.solarforge.world_generation.features.trunk_placers;

import com.finderfeed.solarforge.Helpers;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import jdk.nashorn.internal.runtime.Property;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class BurntTreeTrunkPlacer extends AbstractTrunkPlacer {
    public static final Codec<BurntTreeTrunkPlacer> CODEC = RecordCodecBuilder.create((p_236904_0_) -> {
        return trunkPlacerParts(p_236904_0_).apply(p_236904_0_, BurntTreeTrunkPlacer::new);
    });

    public BurntTreeTrunkPlacer(int p_i232060_1_, int p_i232060_2_, int p_i232060_3_) {
        super(p_i232060_1_, p_i232060_2_, p_i232060_3_);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return TrunkPlacersRegistry.BURNT_TREE_TRUNK_PLACER_TRUNK_PLACER_TYPE;
    }

    @Override
    public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader world, Random rnd, int height, BlockPos pos, Set<BlockPos> set, MutableBoundingBox box, BaseTreeFeatureConfig cfg) {
        setDirtAt(world,pos.below());

        placeLogsInDirection(world,pos,Direction.WEST,4,rnd,0,cfg.trunkProvider.getState(rnd,pos),true,box,true);
        placeLogsInDirection(world,pos,Direction.SOUTH,4,rnd,0,cfg.trunkProvider.getState(rnd,pos),true,box,true);
        placeLogsInDirection(world,pos,Direction.EAST,4,rnd,0,cfg.trunkProvider.getState(rnd,pos),true,box,true);
        placeLogsInDirection(world,pos,Direction.NORTH,4,rnd,0,cfg.trunkProvider.getState(rnd,pos),true,box,true);

        placeLogsInDirection(world,pos.above(),Direction.WEST,1,rnd,0,cfg.trunkProvider.getState(rnd,pos),false,box,false);
        placeLogsInDirection(world,pos.above(),Direction.SOUTH,1,rnd,0,cfg.trunkProvider.getState(rnd,pos),false,box,false);
        placeLogsInDirection(world,pos.above(),Direction.EAST,1,rnd,0,cfg.trunkProvider.getState(rnd,pos),false,box,false);
        placeLogsInDirection(world,pos.above(),Direction.NORTH,1,rnd,0,cfg.trunkProvider.getState(rnd,pos),false,box,false);

        for (int i = 0;i<= height+1;i++){
            setBlock(world,pos.above(i),cfg.trunkProvider.getState(rnd,pos.above(i)),box);
        }

        return ImmutableList.of(new FoliagePlacer.Foliage(pos.above(height), 0, false));
    }

    public void placeLogsInDirection(IWorldGenerationReader world,BlockPos pos,Direction dir, int length, Random rnd, int iterator, BlockState state,boolean rotate,MutableBoundingBox box,boolean placeDirt){
        if (iterator < length){

            BlockState stateConf = state.getBlock().defaultBlockState();

                if (stateConf.hasProperty(BlockStateProperties.AXIS) && rotate) {
                    if (dir.equals(Direction.WEST) || dir.equals(Direction.EAST)) {
                        stateConf = stateConf.setValue(BlockStateProperties.AXIS, Direction.Axis.X);
                    } else if (dir.equals(Direction.SOUTH) || dir.equals(Direction.NORTH)) {
                        stateConf = stateConf.setValue(BlockStateProperties.AXIS, Direction.Axis.Z);
                    }
                }

            if (placeDirt){
                setDirtAt(world,Helpers.getBlockPositionsByDirection(dir,pos,1).get(1).below());
            }
            setBlock(world,Helpers.getBlockPositionsByDirection(dir,pos,1).get(1),stateConf,box);
            if (iterator != 0 && iterator%(Math.round(rnd.nextFloat()+1)) == 0) {
                placeLogsInDirection(world, Helpers.getBlockPositionsByDirection(dir, pos, 1).get(1), Helpers.getRandomHorizontalDirection(true, dir, rnd), length, rnd, iterator + 1, state, rotate, box, placeDirt);
            }else{
                placeLogsInDirection(world, Helpers.getBlockPositionsByDirection(dir, pos, 1).get(1), dir, length, rnd, iterator + 1, state, rotate, box, placeDirt);
            }
        }
    }
}
