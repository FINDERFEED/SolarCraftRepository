package com.finderfeed.solarforge.world_generation.features.foliage_placers;

import com.finderfeed.solarforge.Helpers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;
import java.util.Set;

public class BurntTreeFoliagePlacer extends FoliagePlacer {
    public static final Codec<BurntTreeFoliagePlacer> CODEC = RecordCodecBuilder.create((p_236737_0_) -> {
        return foliagePlacerParts(p_236737_0_).apply(p_236737_0_, BurntTreeFoliagePlacer::new);
    });
    public BurntTreeFoliagePlacer(FeatureSpread p_i241999_1_, FeatureSpread p_i241999_2_) {
        super(p_i241999_1_, p_i241999_2_);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return FoliagePlacerRegistry.BURNT_TREE_FOLIAGE_PLACER_FOLIAGE_PLACER_TYPE.get();
    }

    @Override
    protected void createFoliage(IWorldGenerationReader reader,
                                 Random random,
                                 BaseTreeFeatureConfig cfg,
                                 int idk,
                                 Foliage foliage,
                                 int idk1,
                                 int idk2,
                                 Set<BlockPos> setPos,
                                 int idk3,
                                 MutableBoundingBox box) {

        BlockPos foliagePos = foliage.foliagePos();
        BlockPos foliagePosBelow = foliagePos.below();
        BlockPos foliagePosAbove = foliagePos.above();
        BlockPos foliagePosAboveX2 = foliagePos.above().above();
        Helpers.getSurroundingBlockPositionsHorizontal(foliagePos).forEach((pos)->{
            reader.setBlock(pos,cfg.leavesProvider.getState(random,pos).setValue(BlockStateProperties.DISTANCE,1),19);
            reader.setBlock(pos.above(),cfg.leavesProvider.getState(random,pos.above()).setValue(BlockStateProperties.DISTANCE,1),19);
        });
//        reader.setBlock(foliagePos,cfg.leavesProvider.getState(random,foliagePos),19);
//        reader.setBlock(foliagePos.above(),cfg.leavesProvider.getState(random,foliagePos.above()),19);

        reader.setBlock(foliagePosAboveX2,cfg.leavesProvider.getState(random,foliagePosAboveX2).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosAboveX2.east(),cfg.leavesProvider.getState(random,foliagePosAboveX2.east()).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosAboveX2.west(),cfg.leavesProvider.getState(random,foliagePosAboveX2.west()).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosAboveX2.north(),cfg.leavesProvider.getState(random,foliagePosAboveX2.north()).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosAboveX2.south(),cfg.leavesProvider.getState(random,foliagePosAboveX2.south()).setValue(BlockStateProperties.DISTANCE,1),19);

        reader.setBlock(foliagePosBelow.east(),cfg.leavesProvider.getState(random,foliagePosBelow.east()).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosBelow.west(),cfg.leavesProvider.getState(random,foliagePosBelow.west()).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosBelow.north(),cfg.leavesProvider.getState(random,foliagePosBelow.north()).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosBelow.south(),cfg.leavesProvider.getState(random,foliagePosBelow.south()).setValue(BlockStateProperties.DISTANCE,1),19);

        reader.setBlock(foliagePosBelow.east(3),cfg.leavesProvider.getState(random,foliagePosBelow.east(3)).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosBelow.west(3),cfg.leavesProvider.getState(random,foliagePosBelow.west(3)).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosBelow.north(3),cfg.leavesProvider.getState(random,foliagePosBelow.north(3)).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosBelow.south(3),cfg.leavesProvider.getState(random,foliagePosBelow.south(3)).setValue(BlockStateProperties.DISTANCE,1),19);


        reader.setBlock(foliagePosAbove.east(2),cfg.leavesProvider.getState(random,foliagePosAbove.east(2)).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosAbove.west(2),cfg.leavesProvider.getState(random,foliagePosAbove.west(2)).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosAbove.north(2),cfg.leavesProvider.getState(random,foliagePosAbove.north(2)).setValue(BlockStateProperties.DISTANCE,1),19);
        reader.setBlock(foliagePosAbove.south(2),cfg.leavesProvider.getState(random,foliagePosAbove.south(2)).setValue(BlockStateProperties.DISTANCE,1),19);

        for (int i = 2;i <= 3;i++){
            reader.setBlock(foliagePos.east(i),cfg.leavesProvider.getState(random,foliagePos.east(i)).setValue(BlockStateProperties.DISTANCE,1),19);
            reader.setBlock(foliagePos.west(i),cfg.leavesProvider.getState(random,foliagePos.west(i)).setValue(BlockStateProperties.DISTANCE,1),19);
            reader.setBlock(foliagePos.north(i),cfg.leavesProvider.getState(random,foliagePos.north(i)).setValue(BlockStateProperties.DISTANCE,1),19);
            reader.setBlock(foliagePos.south(i),cfg.leavesProvider.getState(random,foliagePos.south(i)).setValue(BlockStateProperties.DISTANCE,1),19);
        }

    }

    @Override
    public int foliageHeight(Random p_230374_1_, int p_230374_2_, BaseTreeFeatureConfig p_230374_3_) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(Random p_230373_1_, int p_230373_2_, int p_230373_3_, int p_230373_4_, int p_230373_5_, boolean p_230373_6_) {
        return false;
    }



}
