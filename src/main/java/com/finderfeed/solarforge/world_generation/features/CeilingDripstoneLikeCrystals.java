package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.registries.blocks.SolarcraftBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.Optional;

public class CeilingDripstoneLikeCrystals extends Feature<NoneFeatureConfiguration> {
    public CeilingDripstoneLikeCrystals(Codec<NoneFeatureConfiguration> p_65786_) {
        super(p_65786_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos origin = context.origin();
        WorldGenLevel world = context.level();
        Optional<Column> optionalColumn = Column.scan(world,origin,40, BlockBehaviour.BlockStateBase::isAir,state -> state.is(Tags.Blocks.STONE));
        if (optionalColumn.isPresent()){
               Column column = optionalColumn.get();
               if (column.getCeiling().isPresent() && column.getFloor().isPresent()){
                   int floor = column.getFloor().getAsInt();
                   BlockPos pos = new BlockPos(origin.getX(),column.getCeiling().getAsInt(),origin.getZ());

                   int height = pos.getY() - floor;
                   if (height > 10) {
                       int crystalHeight = Math.round(height * 0.5f);
                       int maxRandomCrystalHeight = FDMathHelper.clamp(7, crystalHeight, 15);
                       crystalHeight = 6 + world.getRandom().nextInt(maxRandomCrystalHeight - 3);
                       int maxRad = crystalHeight / 2;
                       maxRad = FDMathHelper.clamp(1,maxRad,4);

                       ArrayList<BlockPos> fillPositions = new ArrayList<>();

                       for (int x = -maxRad; x <= maxRad; x++) {
                           for (int z = -maxRad; z <= maxRad; z++) {
                               BlockPos test = pos.offset(x, 0, z);
                               if (world.getBlockState(test).isAir()){
                                   boolean end = true;
                                   for (int i = 1; i <= 3;i++){
                                       BlockPos above = test.above(i);
                                       if (world.getBlockState(test.above(i)).is(Tags.Blocks.STONE)){
                                           end = false;
                                           break;
                                       }
                                       fillPositions.add(above);
                                   }
                                   if (end){
                                       fillPositions.clear();
                                       return false;
                                   }
                               }
                           }
                       }
                       for (BlockPos fillPos : fillPositions){
                           double X = (fillPos.getX() - pos.getX());
                           double Z = (fillPos.getZ() - pos.getZ());
                           double r = Math.sqrt(X*X + Z*Z);
                           if (r <= maxRad){
                               world.setBlock(fillPos, SolarcraftBlocks.RADIANT_CRYSTAL.get().defaultBlockState(),3);
                           }
                       }
                       for (int x = -maxRad; x <= maxRad; x++) {
                           for (int y = 0; y <= crystalHeight; y++) {
                               for (int z = -maxRad; z <= maxRad; z++) {
                                   BlockPos test = pos.offset(x, -y, z);
                                   if (isValidPlace(world, pos, test, crystalHeight, maxRad)) {
                                       world.setBlock(test, SolarcraftBlocks.RADIANT_CRYSTAL.get().defaultBlockState(), 3);
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

    private boolean isValidPlace(WorldGenLevel world,BlockPos origin,BlockPos posToCheck,int maxheight,int maxRad){
        if (world.getBlockState(posToCheck).isAir()){
            Vec3 horizontal = new Vec3(posToCheck.getX() - origin.getX(),0,posToCheck.getZ() - origin.getZ());
            double height = Math.abs(posToCheck.getY() - origin.getY());
            double logPos = maxheight - height + 1;
            double radius = Math.log(logPos);

            return horizontal.length() <= radius;
        }else{
            return false;
        }
    }
}
//Math.abs(((height - maxheight) * maxRad) / maxheight)
