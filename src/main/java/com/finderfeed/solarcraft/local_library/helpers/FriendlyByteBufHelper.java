package com.finderfeed.solarcraft.local_library.helpers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class FriendlyByteBufHelper {


    public static void writeVec3(FriendlyByteBuf buf, Vec3 vec){
        buf.writeDouble(vec.x);
        buf.writeDouble(vec.y);
        buf.writeDouble(vec.z);
    }

    public static Vec3 readVec3(FriendlyByteBuf buf){
        return new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
    }

}
