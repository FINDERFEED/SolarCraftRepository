package com.finderfeed.solarcraft.local_library.bedrock_loader.animations;

import com.finderfeed.solarcraft.local_library.bedrock_loader.JsonHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class KeyFrame {

    private Vec3 post;
    private Vec3 pre;
    private float time;
    private LerpMode lerpMode;

    public KeyFrame(Vec3 pre,Vec3 post,LerpMode mode,float time){
        this.post = post;
        this.pre = pre;
        this.time = time;
        this.lerpMode = mode;
    }



    public static List<KeyFrame> parseKeyFrameList(JsonElement element){
        if (element.isJsonObject()) {
            return parseJsonObject(element.getAsJsonObject());
        }else if (element.isJsonPrimitive()){
            Vec3 value = new Vec3(element.getAsFloat(),element.getAsFloat(),element.getAsFloat());
            return List.of(new KeyFrame(value,value,LerpMode.LINEAR,0));
        }else if (element.isJsonArray()){
            Vec3 value = JsonHelper.parseVec3(element);
            return List.of(new KeyFrame(value,value,LerpMode.LINEAR,0));
        }else{
            throw new RuntimeException("Error reading keyframe list: " + element);
        }
    }

    private static List<KeyFrame> parseJsonObject(JsonObject object){
        List<KeyFrame> frames = new ArrayList<>();
        for (var entry : object.entrySet()) {
            String time = entry.getKey();
            JsonElement e = entry.getValue();
            KeyFrame keyFrame;
            if (e.isJsonObject()) {
                JsonObject sframe = e.getAsJsonObject();
                Vec3 pre = JsonHelper.parseVec3(sframe, "pre");
                Vec3 post = JsonHelper.parseVec3(sframe, "post");
                String lerpmode = sframe.get("lerp_mode").getAsString();
                keyFrame = new KeyFrame(pre, post, LerpMode.valueOf(lerpmode.toUpperCase()), Float.parseFloat(time));
            } else {
                Vec3 value = JsonHelper.parseVec3(object, time);
                keyFrame = new KeyFrame(value, value, LerpMode.LINEAR, Float.parseFloat(time));
            }
            frames.add(keyFrame);
        }
        return frames;
    }

    public float getTime() {
        return time;
    }

    public LerpMode getLerpMode() {
        return lerpMode;
    }

    public Vec3 getPost() {
        return post;
    }

    public static enum LerpMode{
        LINEAR,
        CATMULLROM;
    }
}
