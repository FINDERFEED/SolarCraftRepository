package com.finderfeed.solarforge.magic.blocks;

import com.finderfeed.solarforge.local_library.blocks.NormalGrassBlock;
import com.finderfeed.solarforge.registries.blocks.SolarcraftBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.List;
import java.util.Random;

public class RadiantGrass extends NormalGrassBlock {

    public RadiantGrass(Properties p_53685_) {
        super(p_53685_);
    }
    @Override
    public void performBonemeal(ServerLevel p_53687_, Random p_53688_, BlockPos p_53689_, BlockState p_53690_) {
        BlockPos var5 = p_53689_.above();
        BlockState var6 = SolarcraftBlocks.RADIANT_GRASS_NOT_BLOCK.get().defaultBlockState();

        label48:
        for(int var7 = 0; var7 < 128; ++var7) {
            BlockPos var8 = var5;

            for(int var9 = 0; var9 < var7 / 16; ++var9) {
                var8 = var8.offset(p_53688_.nextInt(3) - 1, (p_53688_.nextInt(3) - 1) * p_53688_.nextInt(3) / 2, p_53688_.nextInt(3) - 1);
                if (!p_53687_.getBlockState(var8.below()).is(this) || p_53687_.getBlockState(var8).isCollisionShapeFullBlock(p_53687_, var8)) {
                    continue label48;
                }
            }

            BlockState var12 = p_53687_.getBlockState(var8);
            if (var12.is(var6.getBlock()) && p_53688_.nextInt(10) == 0) {
                ((BonemealableBlock)var6.getBlock()).performBonemeal(p_53687_, p_53688_, var8, var12);
            }

            if (var12.isAir()) {
                BlockState var10;
                if (p_53688_.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> var11 = p_53687_.getBiome(var8).value().getGenerationSettings().getFlowerFeatures();
                    if (var11.isEmpty()) {
                        continue;
                    }

                    var10 = var6;
                } else {
                    var10 = var6;
                }

                if (var10.canSurvive(p_53687_, var8)) {
                    p_53687_.setBlock(var8, var10, 3);
                }
            }
        }
    }

//    private static <U extends FeatureConfiguration> BlockState getBlockStatem(Random p_153318_, BlockPos p_153319_, ConfiguredFeature<U, ?> p_153320_) {
//        AbstractFlowerFeature<U> var3 = (AbstractFlowerFeature)p_153320_.feature;
//        return var3.getRandomFlower(p_153318_, p_153319_, p_153320_.config());
//    }
}
