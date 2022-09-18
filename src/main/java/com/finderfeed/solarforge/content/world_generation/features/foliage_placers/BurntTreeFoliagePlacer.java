package com.finderfeed.solarforge.content.world_generation.features.foliage_placers;

import com.finderfeed.solarforge.helpers.Helpers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Random;
import java.util.function.BiConsumer;

public class BurntTreeFoliagePlacer extends FoliagePlacer {
    public static final Codec<BurntTreeFoliagePlacer> CODEC = RecordCodecBuilder.create((p_236737_0_) -> {
        return foliagePlacerParts(p_236737_0_).apply(p_236737_0_, BurntTreeFoliagePlacer::new);
    });
    public BurntTreeFoliagePlacer(IntProvider p_i241999_1_, IntProvider p_i241999_2_) {
        super(p_i241999_1_, p_i241999_2_);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return FoliagePlacerRegistry.BURNT_TREE_PLACER.get();
    }


    @Override
    protected void createFoliage(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> world, Random random, TreeConfiguration cfg, int i12, FoliageAttachment foliageAttachment, int i1, int i2, int i3) {

        BlockPos foliagePos = foliageAttachment.pos();
        BlockPos foliagePosBelow = foliagePos.below();
        BlockPos foliagePosAbove = foliagePos.above();
        BlockPos foliagePosAboveX2 = foliagePos.above().above();
        Helpers.getSurroundingBlockPositionsHorizontal(foliagePos).forEach((pos)->{
            world.accept(pos,cfg.foliageProvider.getState(random,pos).setValue(BlockStateProperties.DISTANCE,1));
            world.accept(pos.above(),cfg.foliageProvider.getState(random,pos.above()).setValue(BlockStateProperties.DISTANCE,1));
        });
//        reader.setBlock(foliagePos,cfg.leavesProvider.getState(random,foliagePos),19);
//        reader.setBlock(foliagePos.above(),cfg.leavesProvider.getState(random,foliagePos.above()),19);

        world.accept(foliagePosAboveX2,cfg.foliageProvider.getState(random,foliagePosAboveX2).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosAboveX2.east(),cfg.foliageProvider.getState(random,foliagePosAboveX2.east()).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosAboveX2.west(),cfg.foliageProvider.getState(random,foliagePosAboveX2.west()).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosAboveX2.north(),cfg.foliageProvider.getState(random,foliagePosAboveX2.north()).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosAboveX2.south(),cfg.foliageProvider.getState(random,foliagePosAboveX2.south()).setValue(BlockStateProperties.DISTANCE,1));

        world.accept(foliagePosBelow.east(),cfg.foliageProvider.getState(random,foliagePosBelow.east()).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosBelow.west(),cfg.foliageProvider.getState(random,foliagePosBelow.west()).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosBelow.north(),cfg.foliageProvider.getState(random,foliagePosBelow.north()).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosBelow.south(),cfg.foliageProvider.getState(random,foliagePosBelow.south()).setValue(BlockStateProperties.DISTANCE,1));

        world.accept(foliagePosBelow.east(3),cfg.foliageProvider.getState(random,foliagePosBelow.east(3)).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosBelow.west(3),cfg.foliageProvider.getState(random,foliagePosBelow.west(3)).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosBelow.north(3),cfg.foliageProvider.getState(random,foliagePosBelow.north(3)).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosBelow.south(3),cfg.foliageProvider.getState(random,foliagePosBelow.south(3)).setValue(BlockStateProperties.DISTANCE,1));


        world.accept(foliagePosAbove.east(2),cfg.foliageProvider.getState(random,foliagePosAbove.east(2)).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosAbove.west(2),cfg.foliageProvider.getState(random,foliagePosAbove.west(2)).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosAbove.north(2),cfg.foliageProvider.getState(random,foliagePosAbove.north(2)).setValue(BlockStateProperties.DISTANCE,1));
        world.accept(foliagePosAbove.south(2),cfg.foliageProvider.getState(random,foliagePosAbove.south(2)).setValue(BlockStateProperties.DISTANCE,1));

        for (int i = 2;i <= 3;i++){
            world.accept(foliagePos.east(i),cfg.foliageProvider.getState(random,foliagePos.east(i)).setValue(BlockStateProperties.DISTANCE,1));
            world.accept(foliagePos.west(i),cfg.foliageProvider.getState(random,foliagePos.west(i)).setValue(BlockStateProperties.DISTANCE,1));
            world.accept(foliagePos.north(i),cfg.foliageProvider.getState(random,foliagePos.north(i)).setValue(BlockStateProperties.DISTANCE,1));
            world.accept(foliagePos.south(i),cfg.foliageProvider.getState(random,foliagePos.south(i)).setValue(BlockStateProperties.DISTANCE,1));
        }

    }



    @Override
    public int foliageHeight(Random p_230374_1_, int p_230374_2_, TreeConfiguration p_230374_3_) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
        return false;
    }



}
