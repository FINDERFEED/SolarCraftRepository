package com.finderfeed.solarforge.for_future_library.helpers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class CompoundNBTHelper {

    public static void writeVec3(String id, Vec3 vec, CompoundTag tag){
        tag.putDouble(id+"1",vec.x);
        tag.putDouble(id+"2",vec.y);
        tag.putDouble(id+"3",vec.z);
    }

    public static Vec3 getVec3(String id,CompoundTag tag){
        return new Vec3(tag.getDouble(id+"1"),tag.getDouble(id+"2"),tag.getDouble(id+"3"));
    }


}
