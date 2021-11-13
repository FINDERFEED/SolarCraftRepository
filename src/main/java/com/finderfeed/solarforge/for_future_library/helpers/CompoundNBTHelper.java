package com.finderfeed.solarforge.for_future_library.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class CompoundNBTHelper {

    public static void writeVec3(String id, Vec3 vec, CompoundTag tag){
        tag.putDouble(id+"1",vec.x);
        tag.putDouble(id+"2",vec.y);
        tag.putDouble(id+"3",vec.z);
    }

    public static Vec3 getVec3(String id,CompoundTag tag){
        return new Vec3(tag.getDouble(id+"1"),tag.getDouble(id+"2"),tag.getDouble(id+"3"));
    }


    public static void writeBlockPos(String id, BlockPos vec, CompoundTag tag){
        tag.putInt(id+"1",vec.getX());
        tag.putInt(id+"2",vec.getY());
        tag.putInt(id+"3",vec.getZ());
    }

    public static BlockPos getBlockPos(String id,CompoundTag tag){
        return new BlockPos(tag.getInt(id+"1"),tag.getInt(id+"2"),tag.getInt(id+"3"));
    }

    public static void writeBlockPosList(String id, List<BlockPos> positions, CompoundTag tag){
        tag.putInt("poslistsize" + id,positions.size());
        for (int i = 0;i < positions.size();i++){
            writeBlockPos(id+i,positions.get(i),tag);
        }
    }

    public static List<BlockPos> getBlockPosList(String id,CompoundTag tag){
        List<BlockPos> positions = new ArrayList<>();
        int size = tag.getInt("poslistsize" + id);
        for (int i = 0; i < size;i++){
            positions.add(getBlockPos(id+i,tag));
        }
        return positions;
    }



}
