package com.finderfeed.solarcraft.local_library.bedrock_loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class JsonHelper {


    public static Vec3 parseVec3(JsonElement element){
        JsonArray array = element.getAsJsonArray();
        return new Vec3(array.get(0).getAsDouble(),array.get(1).getAsDouble(),array.get(2).getAsDouble());
    }

    public static Vec3 parseVec3(JsonObject element,String memberName){
        if (!element.has(memberName)){
            return Vec3.ZERO;
        }
        JsonArray array = element.getAsJsonArray(memberName);
        return new Vec3(array.get(0).getAsDouble(),array.get(1).getAsDouble(),array.get(2).getAsDouble());
    }

    public static Vec2 parseVec2(JsonElement element){
        JsonArray array = element.getAsJsonArray();
        return new Vec2(array.get(0).getAsFloat(),array.get(1).getAsFloat());
    }

}
