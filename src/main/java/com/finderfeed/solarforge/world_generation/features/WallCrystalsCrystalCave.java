package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.registries.blocks.SolarcraftBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WallCrystalsCrystalCave extends Feature<NoneFeatureConfiguration> {


    public WallCrystalsCrystalCave(Codec<NoneFeatureConfiguration> p_65786_) {
        super(p_65786_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        BlockPos origin = ctx.origin();
        WorldGenLevel world = ctx.level();
        int randomRadius = 3+world.getRandom().nextInt(3);
        origin = origin.offset(randomRadius,0,randomRadius);

        int remainingSpace = 16 - randomRadius * 2;
        origin = origin.offset(world.getRandom().nextInt(remainingSpace), 0, world.getRandom().nextInt(remainingSpace));

        for (int x = -randomRadius; x <= randomRadius;x++){
            for (int y = -randomRadius; y <= randomRadius;y++){
                for (int z = -randomRadius; z <= randomRadius;z++){
                    BlockPos pos = origin.offset(x,y,z);
                    if (isInsideSphere(origin,pos,randomRadius)){
                        if ((   world.getBlockState(pos.above()).isAir() || world.getBlockState(pos.below()).isAir() ||
                                world.getBlockState(pos.north()).isAir() || world.getBlockState(pos.east()).isAir() ||
                                world.getBlockState(pos.west()).isAir() || world.getBlockState(pos.south()).isAir())

                                && OreFeatures.NATURAL_STONE.test(world.getBlockState(pos),world.getRandom())){
                            world.setBlock(pos, SolarcraftBlocks.RADIANT_CRYSTAL.get().defaultBlockState(),3);
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isInsideSphere(Vec3i center, Vec3i point, double radius){
        int X = point.getX() - center.getX();
        int Y = point.getY() - center.getY();
        int Z = point.getZ() - center.getZ();
        return Math.sqrt(X*X + Y*Y + Z*Z) <= radius;
    }


}
