package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

import java.util.Optional;

public class CeilingDripstoneLikeCrystals extends Feature<NoneFeatureConfiguration> {
    public CeilingDripstoneLikeCrystals(Codec<NoneFeatureConfiguration> p_65786_) {
        super(p_65786_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos origin = context.origin();
        WorldGenLevel world = context.level();
        Optional<Column> optionalColumn = Column.scan(world,origin,30, BlockBehaviour.BlockStateBase::isAir,state -> state.is(Tags.Blocks.STONE));
        if (optionalColumn.isPresent()){
               Column column = optionalColumn.get();
               if (column.getCeiling().isPresent() && column.getFloor().isPresent()){
                   int floor = column.getFloor().getAsInt();
                   BlockPos pos = new BlockPos(origin.getX(),column.getCeiling().getAsInt(),origin.getZ());
                   int height = pos.getY() - floor;
                   if (height > 10) {
                       int crystalHeight = Math.round(height * 0.5f);
                       int maxRandomCrystalHeight = FinderfeedMathHelper.clamp(7, crystalHeight, 15);
                       crystalHeight = 3 + world.getRandom().nextInt(maxRandomCrystalHeight - 3);
                       int maxRad = crystalHeight / 2;
                       maxRad = FinderfeedMathHelper.clamp(1,maxRad,3);

                       for (int x = -maxRad; x <= maxRad; x++) {
                           for (int y = 0; y <= crystalHeight; y++) {
                               for (int z = -maxRad; z <= maxRad; z++) {
                                   BlockPos test = pos.offset(x, -y, z);
                                   if (isValidPlace(world, pos, test, crystalHeight)) {
                                       world.setBlock(test, BlocksRegistry.RADIANT_CRYSTAL.get().defaultBlockState(), 3);
                                   }
                               }
                           }
                       }
                       return true;
                   }else{
                       return false;
                   }
               }
        }else{
            return false;
        }
        return false;
    }

    private boolean isValidPlace(WorldGenLevel world,BlockPos origin,BlockPos posToCheck,int maxheight){
        if (world.getBlockState(posToCheck).isAir()){
            Vec3 horizontal = new Vec3(posToCheck.getX() - origin.getX(),0,posToCheck.getZ() - origin.getZ());
            double height = Math.abs(posToCheck.getY() - origin.getY());
            return horizontal.length()+1 < (maxheight*1.5f) / height;
        }else{
            return false;
        }
    }
}
