package com.finderfeed.solarforge.world_generation.dimension_related.radiant_land;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.world_generation.features.foliage_placers.FoliagePlacerRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.function.BiConsumer;

public class RadiantSmallTreeFoliagePlacer extends FoliagePlacer {
    public static final Codec<RadiantSmallTreeFoliagePlacer> CODEC = RecordCodecBuilder.create((p_236737_0_) -> {
        return foliagePlacerParts(p_236737_0_).apply(p_236737_0_, RadiantSmallTreeFoliagePlacer::new);
    });

    public RadiantSmallTreeFoliagePlacer(IntProvider p_161411_, IntProvider p_161412_) {
        super(p_161411_, p_161412_);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return FoliagePlacerRegistry.RADIANT_TREE_SMALL_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> world, Random random, TreeConfiguration cfg, int i, FoliageAttachment place, int i1, int i2, int i3) {
        BlockPos mainpos = place.pos().below(3);

        for (int a = -3;a < 4;a++){
            for (int b = -3;b < 4;b++){
                for (int h = -4;h < 6;h++){
                    BlockPos toCheck = new BlockPos(mainpos.getX() + a,mainpos.getY() + h, mainpos.getZ() + b);
                    if (!reader.isStateAtPosition(toCheck,(state)->state.is(BlockTags.LOGS))) {
                        if (isValidPos(mainpos, toCheck)) {
                            world.accept(toCheck, cfg.foliageProvider.getState(random, toCheck).setValue(BlockStateProperties.DISTANCE, 1));
                        }
                    }
                }
            }
        }
    }

    private boolean isValidPos(BlockPos mainpos,BlockPos posToCheck){
        Vec3 mainposvec = Helpers.getBlockCenter(mainpos);
        Vec3 posToCheckVec = Helpers.getBlockCenter(posToCheck);
        Vec3 horizontalVector = new Vec3(posToCheckVec.x - mainposvec.x,0,posToCheckVec.z - mainposvec.z);
        float x;
        if (posToCheckVec.y < mainposvec.y){
            double razn  = mainposvec.y - posToCheckVec.y;
            x = (5-(float)(razn));
            if (Math.round(razn) != 1){
                x /= 1.5f;
            }
        }else{
            x =5 - (float)(posToCheckVec.y - mainposvec.y);
        }

        if (horizontalVector.length() <= Math.sqrt(2.3*x)){
            return true;
        }

        return false;
    }

    @Override
    public int foliageHeight(Random random, int i, TreeConfiguration treeConfiguration) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(Random random, int i, int i1, int i2, int i3, boolean b) {
        return false;
    }
}
