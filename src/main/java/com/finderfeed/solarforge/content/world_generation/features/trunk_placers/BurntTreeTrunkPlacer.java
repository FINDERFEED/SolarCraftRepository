package com.finderfeed.solarforge.content.world_generation.features.trunk_placers;

import com.finderfeed.solarforge.helpers.Helpers;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;

import net.minecraft.core.BlockPos;

import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class BurntTreeTrunkPlacer extends TrunkPlacer {
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
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimulatedReader, BiConsumer<BlockPos, BlockState> world, Random rnd, int height, BlockPos pos, TreeConfiguration cfg) {
        setDirtAt(levelSimulatedReader,world,rnd,pos.below(),cfg);

        placeLogsInDirection(levelSimulatedReader,cfg,world,pos,Direction.WEST,4,rnd,0,cfg.trunkProvider.getState(rnd,pos),true,true);
        placeLogsInDirection(levelSimulatedReader,cfg,world,pos,Direction.SOUTH,4,rnd,0,cfg.trunkProvider.getState(rnd,pos),true,true);
        placeLogsInDirection(levelSimulatedReader,cfg,world,pos,Direction.EAST,4,rnd,0,cfg.trunkProvider.getState(rnd,pos),true,true);
        placeLogsInDirection(levelSimulatedReader,cfg,world,pos,Direction.NORTH,4,rnd,0,cfg.trunkProvider.getState(rnd,pos),true,true);

        placeLogsInDirection(levelSimulatedReader,cfg,world,pos.above(),Direction.WEST,1,rnd,0,cfg.trunkProvider.getState(rnd,pos),false,false);
        placeLogsInDirection(levelSimulatedReader,cfg,world,pos.above(),Direction.SOUTH,1,rnd,0,cfg.trunkProvider.getState(rnd,pos),false,false);
        placeLogsInDirection(levelSimulatedReader,cfg,world,pos.above(),Direction.EAST,1,rnd,0,cfg.trunkProvider.getState(rnd,pos),false,false);
        placeLogsInDirection(levelSimulatedReader,cfg,world,pos.above(),Direction.NORTH,1,rnd,0,cfg.trunkProvider.getState(rnd,pos),false,false);

        for (int i = 0;i<= height+1;i++){
            world.accept(pos.above(i),cfg.trunkProvider.getState(rnd,pos.above(i)));

        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(height), 0, false));
    }

    public void placeLogsInDirection(LevelSimulatedReader levelSimulatedReader,TreeConfiguration cfg,BiConsumer<BlockPos,BlockState> world,BlockPos pos,Direction dir, int length, Random rnd, int iterator, BlockState state,boolean rotate,boolean placeDirt){
        if (iterator < length){

            BlockState stateConf = state.getBlock().defaultBlockState();

                if (stateConf.hasProperty(BlockStateProperties.AXIS) && rotate) {
                    if (dir.equals(Direction.WEST) || dir.equals(Direction.EAST)) {
                        stateConf = stateConf.setValue(BlockStateProperties.AXIS, Direction.Axis.X);
                    } else if (dir.equals(Direction.SOUTH) || dir.equals(Direction.NORTH)) {
                        stateConf = stateConf.setValue(BlockStateProperties.AXIS, Direction.Axis.Z);
                    }
                }
                BlockPos poss = Helpers.getBlockPositionsByDirection(dir, pos, 1).get(1);
            if (levelSimulatedReader.isStateAtPosition(poss.below(),(state1 -> !state1.is(Blocks.AIR)))) {
                if (placeDirt) {

                    setDirtAt(levelSimulatedReader, world, rnd, poss.below(), cfg);
                }
                world.accept(poss, stateConf);
            }
            if (iterator != 0 && iterator%(Math.round(rnd.nextFloat()+1)) == 0) {
                placeLogsInDirection(levelSimulatedReader,cfg,world, Helpers.getBlockPositionsByDirection(dir, pos, 1).get(1), Helpers.getRandomHorizontalDirection(true, dir, rnd), length, rnd, iterator + 1, state, rotate, placeDirt);
            }else{
                placeLogsInDirection(levelSimulatedReader,cfg,world, Helpers.getBlockPositionsByDirection(dir, pos, 1).get(1), dir, length, rnd, iterator + 1, state, rotate, placeDirt);
            }
        }
    }




}
