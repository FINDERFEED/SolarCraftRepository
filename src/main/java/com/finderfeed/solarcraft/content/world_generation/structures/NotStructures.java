package com.finderfeed.solarcraft.content.world_generation.structures;


import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;


public class NotStructures {

    public static List<BlockEntity> checkInfusingStandStructure(BlockPos pos, Level world){
        List<BlockEntity> toReturn = new ArrayList<>();
        toReturn.add(blockGet(pos.offset(-4,0,-4 ),world));
        toReturn.add(blockGet(pos.offset(0,0,-4  ),world));
        toReturn.add(blockGet(pos.offset(4,0,-4  ),world));
        toReturn.add(blockGet(pos.offset(-2,0,-2 ),world));
        toReturn.add(blockGet(pos.offset(2,0,-2  ),world));
        toReturn.add(blockGet(pos.offset(-4,0,0  ),world));
        toReturn.add(blockGet(pos.offset(4,0,0   ),world));
        toReturn.add(blockGet(pos.offset(-2,0,2  ),world));
        toReturn.add(blockGet(pos.offset(2,0,2   ),world));
        toReturn.add(blockGet(pos.offset(-4,0,4  ),world));
        toReturn.add(blockGet(pos.offset(0,0,4   ),world));
        toReturn.add(blockGet(pos.offset(4,0,4   ),world));

        return toReturn;

    }
    public static BlockPos[] infusingPoolsPositions(BlockPos pos){
        return new BlockPos[]{
                pos.offset(-4,0,-4 ),
                pos.offset(0,0,-4  ),
                pos.offset(4,0,-4  ),
                pos.offset(-2,0,-2 ),
                pos.offset(2,0,-2  ),
                pos.offset(-4,0,0  ),
                pos.offset(4,0,0   ),
                pos.offset(-2,0,2  ),
                pos.offset(2,0,2   ),
                pos.offset(-4,0,4  ),
                pos.offset(0,0,4   ),
                pos.offset(4,0,4   )
        };
    }
    public static BlockEntity blockGet(BlockPos pos,Level world){
        return world.getBlockEntity(pos);
    }
}
