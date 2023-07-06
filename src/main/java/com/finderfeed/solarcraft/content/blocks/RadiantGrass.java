package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.local_library.blocks.NormalGrassBlock;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class RadiantGrass extends NormalGrassBlock {

    public RadiantGrass(Properties p_53685_) {
        super(p_53685_);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource p_221271_, BlockPos p_221272_, BlockState p_221273_) {
        BlockPos blockpos = p_221272_.above();
        BlockState blockstate = SolarcraftBlocks.RADIANT_GRASS.get().defaultBlockState();

        label46:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockpos1 = blockpos;

            for(int j = 0; j < i / 16; ++j) {
                blockpos1 = blockpos1.offset(p_221271_.nextInt(3) - 1, (p_221271_.nextInt(3) - 1) * p_221271_.nextInt(3) / 2, p_221271_.nextInt(3) - 1);
                if (!level.getBlockState(blockpos1.below()).is(this) || level.getBlockState(blockpos1).isCollisionShapeFullBlock(level, blockpos1)) {
                    continue label46;
                }
            }

            BlockState blockstate1 = level.getBlockState(blockpos1);
            if (blockstate1.is(blockstate.getBlock()) && p_221271_.nextInt(10) == 0) {
                ((BonemealableBlock)blockstate.getBlock()).performBonemeal(level, p_221271_, blockpos1, blockstate1);
            }

            if (blockstate1.isAir()) {
                Holder<PlacedFeature> holder;
                if (p_221271_.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = level.getBiome(blockpos1).value().getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }

                    holder = ((RandomPatchConfiguration)list.get(0).config()).feature();
                } else {
                    holder = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolderOrThrow(VegetationPlacements.GRASS_BONEMEAL);
                }

                holder.value().place(level, level.getChunkSource().getGenerator(), p_221271_, blockpos1);
            }
        }
    }


}
