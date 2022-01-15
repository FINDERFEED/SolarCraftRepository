package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.magic_items.blocks.primitive.DirectionBlock;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CrystallizedRunicEnergyCrystalsFeature extends Feature<NoneFeatureConfiguration> {
    public CrystallizedRunicEnergyCrystalsFeature(Codec<NoneFeatureConfiguration> p_65786_) {
        super(p_65786_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockState origin = level.getBlockState(ctx.origin());
        if (!origin.is(Blocks.AIR) ){
            return false;
        }
        Direction direction = getDirection(level,ctx.origin());
        if (direction == null ){
            return false;
        }

        BlockState state = BlocksRegistry.CRYSTALLIZED_RUNIC_ENERGY.get().defaultBlockState().setValue(DirectionBlock.PROP,direction.getOpposite());
        level.setBlock(ctx.origin(),state,2);
        return true;
    }

    @Nullable
    private Direction getDirection(WorldGenLevel level,BlockPos pos){

        List<Direction> dirs = new ArrayList<>();
        for (Direction dir : Direction.values()){
            BlockPos add = pos.relative(dir);
            if (level.getBlockState(add).is(Tags.Blocks.STONE)){
                dirs.add(dir);
            }
        }
        if (dirs.isEmpty()){
            return null;
        }
        return dirs.get(level.getRandom().nextInt(dirs.size()));
    }
}
