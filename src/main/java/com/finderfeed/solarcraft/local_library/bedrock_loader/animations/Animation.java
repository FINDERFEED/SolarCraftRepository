package com.finderfeed.solarcraft.local_library.bedrock_loader.animations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.fml.common.Mod;

import java.io.BufferedReader;
import java.util.*;

public class Animation {

    private static Gson GSON = new GsonBuilder().create();


    private Map<String,AnimationData> boneData = new HashMap<>();
    private float animTime;
    private Mode mode;

    public Animation(List<AnimationData> data, Mode mode,float animTime){
        for (AnimationData d : data){
            boneData.put(d.getBoneName(),d);
        }
        this.animTime = animTime;
        this.mode = mode;
    }





    public static Animation loadAnimation(ResourceLocation location, String animationName){
        JsonObject object = loadJsonFile(location).getAsJsonObject();
        String format = object.get("format_version").getAsString();
        if (!format.equals("1.8.0")){
            throw new IllegalStateException("Animation format " + format + " in animation " + location + " is not supported!");
        }
        JsonObject sanimation = object.getAsJsonObject(animationName);
        Animation animation = parseAnimation(sanimation);
        return animation;
    }

    private static Animation parseAnimation(JsonObject sanimation){
        float length = sanimation.get("animation_length").getAsFloat();
        JsonObject sbones = sanimation.getAsJsonObject("bones");



        List<AnimationData> datas = new ArrayList<>();
        for (var entry : sbones.entrySet()){
            String boneName = entry.getKey();
            JsonObject boneData = entry.getValue().getAsJsonObject();

            List<KeyFrame> rotation;
            List<KeyFrame> position;
            List<KeyFrame> scale;
            if (boneData.has("rotation")) {
                JsonElement srotation = boneData.get("rotation");
                rotation = KeyFrame.parseKeyFrameList(srotation);
            }else{
                rotation = new ArrayList<>();
            }
            if (boneData.has("position")) {
                JsonElement sposition = boneData.get("position");
                position = KeyFrame.parseKeyFrameList(sposition);
            }else{
                position = new ArrayList<>();
            }
            if (boneData.has("scale")) {
                JsonElement sscale = boneData.get("scale");
                scale = KeyFrame.parseKeyFrameList(sscale);
            }else{
                scale = new ArrayList<>();
            }
            datas.add(new AnimationData(boneName,rotation,position,scale));
        }

        Mode mode;
        if (sanimation.has("loop")){
            JsonElement element = sanimation.get("loop");
            String value = element.getAsString();
            if (value.equals("true")){
                mode = Mode.LOOP;
            }else if (value.equals("hold_on_last_frame")){
                mode = Mode.HOLD_ON_LAST_FRAME;
            }else{
                mode = Mode.PLAY_ONCE;
            }
        }else{
            mode = Mode.PLAY_ONCE;
        }
        return new Animation(datas,mode,length);
    }


    private static JsonElement loadJsonFile(ResourceLocation location){
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        Optional<Resource> modelJson = manager.getResource(location);
        try{
            if (modelJson.isPresent()){

                BufferedReader stream = modelJson.get().openAsReader();
                JsonElement element = GSON.fromJson(stream,JsonElement.class);
                stream.close();
                return element;
            }else{
                throw new RuntimeException("Couldn't find animation: " + location);
            }
        }catch (Exception e){
            throw new RuntimeException("Error loading animation file: " + location,e);
        }
    }


    public static enum Mode{
        PLAY_ONCE,
        HOLD_ON_LAST_FRAME,
        LOOP
    }

}
