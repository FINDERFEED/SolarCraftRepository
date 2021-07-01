package com.finderfeed.solarforge.structures;


import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;


public class Structures {

    public static List<TileEntity> checkInfusingStandStructure(BlockPos pos, World world){
        List<TileEntity> list = new ArrayList<>(0);
                    list.add(blockGet(pos.offset(4,0,0),world));
                    list.add(blockGet(pos.offset(3,0,-3),world));
                    list.add(blockGet(pos.offset(0,0,-4),world)) ;
                    list.add(blockGet(pos.offset(-3,0,-3),world)) ;
                    list.add(blockGet(pos.offset(-4,0,0),world));
                    list.add(blockGet(pos.offset(-3,0,3),world));
                    list.add(blockGet(pos.offset(0,0,4),world));
                    list.add(blockGet(pos.offset(3,0,3),world));

                    /*
                    Recipe:
                              N
                              *
                            *   *
                          *       *^
                            *   *
                              *
                              S
                     */
        return list;

    }
    public static TileEntity blockGet(BlockPos pos,World world){
        return world.getBlockEntity(pos);
    }
}
