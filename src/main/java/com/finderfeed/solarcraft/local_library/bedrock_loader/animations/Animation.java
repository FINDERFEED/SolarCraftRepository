package com.finderfeed.solarcraft.local_library.bedrock_loader.animations;

import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModel;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModelPart;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.phys.Vec3;

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



    public void applyAnimation(FDModel model,int tickTime,float partialTicks){
        float time = FDMathHelper.clamp(0,tickTime + partialTicks,this.getAnimTimeInTicks()) / 20f;
        for (var entry  : boneData.entrySet()){
            FDModelPart modelPart = model.getModelPart(entry.getKey());
            this.applyToBone(entry.getValue(),modelPart,time);
        }
    }

    private void applyToBone(AnimationData data,FDModelPart part,float time){
        var positionPair = data.getCurrentAndNextPositionKeyFrame(time);
        this.applyPosition(positionPair,data,part,time);

        var rotationPair = data.getCurrentAndNextRotationKeyFrame(time);
        this.applyRotation(rotationPair,data,part,time);

        var scalePair = data.getCurrentAndNextScaleKeyFrame(time);
        this.applyScale(scalePair,data,part,time);

    }


    private void applyPosition(Pair<KeyFrame,KeyFrame> positionPair,AnimationData data,FDModelPart part,float time){
        if (positionPair != null){
            KeyFrame frame1 = positionPair.getFirst();
            KeyFrame frame2 = positionPair.getSecond();
            if (frame2 != null) {
                float time1 = frame1.getTime();
                float time2 = frame2.getTime();

                float localTime = time - time1;
                float percent = localTime / (time2 - time1);

                if (frame1.getLerpMode() == KeyFrame.LerpMode.LINEAR && frame2.getLerpMode() == KeyFrame.LerpMode.LINEAR) {
                    Vec3 pos = FDMathHelper.lerpv3(frame1.getPost(), frame2.getPre(), percent);
                    part.x = (float) pos.x;
                    part.y = (float) pos.y;
                    part.z = (float) pos.z;
                } else {
                    int index = frame1.getIndex();
                    Vec3 pos = data.getPositionSpline().pointBetweenPoints(index, percent);
                    part.x = (float) pos.x;
                    part.y = (float) pos.y;
                    part.z = (float) pos.z;
                }
            }else{
                Vec3 pos = time < frame1.getTime() ? frame1.getPre() : frame1.getPost();
                part.x = (float) pos.x;
                part.y = (float) pos.y;
                part.z = (float) pos.z;
            }
        }
    }
    private void applyRotation(Pair<KeyFrame,KeyFrame> rotationPair,AnimationData data,FDModelPart part,float time){
        if (rotationPair != null){
            KeyFrame frame1 = rotationPair.getFirst();
            KeyFrame frame2 = rotationPair.getSecond();
            if (frame2 != null) {
                float time1 = frame1.getTime();
                float time2 = frame2.getTime();

                float localTime = time - time1;
                float percent = localTime / (time2 - time1);

                if (frame1.getLerpMode() == KeyFrame.LerpMode.LINEAR && frame2.getLerpMode() == KeyFrame.LerpMode.LINEAR) {
                    Vec3 pos = FDMathHelper.lerpv3(frame1.getPost(), frame2.getPre(), percent);
                    part.xRot = (float) pos.x;
                    part.yRot = (float) pos.y;
                    part.zRot = (float) pos.z;
                } else {
                    int index = frame1.getIndex();
                    Vec3 pos = data.getRotationSpline().pointBetweenPoints(index, percent);
                    part.xRot = (float) pos.x;
                    part.yRot = (float) pos.y;
                    part.zRot = (float) pos.z;
                }
            }else{
                Vec3 pos = time < frame1.getTime() ? frame1.getPre() : frame1.getPost();
                part.xRot = (float) pos.x;
                part.yRot = (float) pos.y;
                part.zRot = (float) pos.z;
            }
        }
    }
    private void applyScale(Pair<KeyFrame,KeyFrame> scalePair,AnimationData data,FDModelPart part,float time){
        if (scalePair != null){
            KeyFrame frame1 = scalePair.getFirst();
            KeyFrame frame2 = scalePair.getSecond();
            if (frame2 != null) {
                float time1 = frame1.getTime();
                float time2 = frame2.getTime();

                float localTime = time - time1;
                float percent = localTime / (time2 - time1);

                if (frame1.getLerpMode() == KeyFrame.LerpMode.LINEAR && frame2.getLerpMode() == KeyFrame.LerpMode.LINEAR) {
                    Vec3 pos = FDMathHelper.lerpv3(frame1.getPost(), frame2.getPre(), percent);
                    part.scaleX = (float) pos.x;
                    part.scaleY = (float) pos.y;
                    part.scaleZ = (float) pos.z;
                } else {
                    int index = frame1.getIndex();
                    Vec3 pos = data.getScaleSpline().pointBetweenPoints(index, percent);
                    part.scaleX = (float) pos.x;
                    part.scaleY = (float) pos.y;
                    part.scaleZ = (float) pos.z;
                }
            }else{
                Vec3 pos = time < frame1.getTime() ? frame1.getPre() : frame1.getPost();
                part.scaleX = (float) pos.x;
                part.scaleY = (float) pos.y;
                part.scaleZ = (float) pos.z;
            }
        }
    }


    public int getAnimTimeInTicks(){
        return (int)(this.animTime*20);
    }









    public enum Mode{
        PLAY_ONCE,
        HOLD_ON_LAST_FRAME,
        LOOP
    }





    public static Animation loadAnimation(ResourceLocation location, String animationName){
        JsonObject object = loadJsonFile(location).getAsJsonObject();
        String format = object.get("format_version").getAsString();
        if (!format.equals("1.8.0")){
            throw new IllegalStateException("Animation format " + format + " in animation " + location + " is not supported!");
        }
        JsonObject sanimation = object.getAsJsonObject("animations").getAsJsonObject(animationName);
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
}
