package com.finderfeed.solarforge.magic.runic_network.repeater;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

import javax.annotation.Nullable;
import java.util.List;

//NEVER GONNA RUN AROUND
public interface IRunicEnergyReciever {


    boolean requiresRunicEnergy(RunicEnergy.Type type);
    void requestEnergy(double amount,RunicEnergy.Type type);
    double getMaxRange();



    @Nullable
    default BlockEntity findNearestRepeaterOrPylon(BlockPos pos, Level world, RunicEnergy.Type type){
        List<LevelChunk> chunks = Helpers.getSurroundingChunks5Radius(pos,world);
        double minRange = getMaxRange()+1;
        BlockEntity tile = null;

        for (int i = 0; i < chunks.size();i++){
            List<BlockEntity> tiles = chunks.get(i).getBlockEntities().values().stream().toList();
            for (int g = 0; g < tiles.size();g++){
                if (tiles.get(g) instanceof BaseRepeaterTile repeater){

                    if ((repeater.getEnergyType() == type) /*&& !(tile instanceof RuneEnergyPylonTile)*/) {
                        if (FinderfeedMathHelper.canSee(repeater.getBlockPos(),pos,getMaxRange(),world)) {
                            double range = FinderfeedMathHelper.getDistanceBetween(repeater.getBlockPos(), pos);
                            if (range <= getMaxRange()) {
                                if (range <= minRange) {
                                    minRange = range;
                                    tile = repeater;
                                }
                            }
                        }
                    }
                }
//                else if (tiles.get(g) instanceof RuneEnergyPylonTile pylon){
//                    if (FinderfeedMathHelper.canSee(pylon.getBlockPos(),pos,getMaxRange(),world)) {
//                        if (pylon.getEnergyType() == type) {
//                            double range = FinderfeedMathHelper.getDistanceBetween(pylon.getBlockPos(), pos);
//                            if (range <= getMaxRange()) {
//                                if (range <= minRange) {
//                                    minRange = range;
//                                    tile = pylon;
//                                }
//                            }
//                        }
//                    }
//                }
            }
        }
        return tile;
    }

}
