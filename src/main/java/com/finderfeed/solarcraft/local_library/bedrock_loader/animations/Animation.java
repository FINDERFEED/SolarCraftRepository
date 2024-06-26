package com.finderfeed.solarcraft.local_library.bedrock_loader.animations;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.misc.ToNullAnimation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.misc.TransitionAnimation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModel;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModelPart;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class Animation {
    private static Gson GSON = new GsonBuilder().create();

    private ResourceLocation name;
    private Map<String,AnimationData> boneData = new HashMap<>();
    private float animTime;
    private Mode mode;

    public Animation(ResourceLocation name,List<AnimationData> data, Mode mode,float animTime){
        for (AnimationData d : data){
            boneData.put(d.getBoneName(),d);
        }
        this.animTime = animTime;
        this.mode = mode;
        this.name = name;
    }



    public void applyAnimation(FDModel model,int tickTime,float partialTicks){
        float time = FDMathHelper.clamp(0,tickTime + partialTicks,this.tickLength()) / 20f;
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
        Vec3 pos = FDAnimationsHelper.getCurrentPosition(positionPair,data,time);
        part.x += (float) pos.x;
        part.y += (float) pos.y;
        part.z += (float) pos.z;
    }
    private void applyRotation(Pair<KeyFrame,KeyFrame> rotationPair,AnimationData data,FDModelPart part,float time){
        Vec3 pos = FDAnimationsHelper.getCurrentRotation(rotationPair,data,time);
        part.xRot += (float) pos.x;
        part.yRot += (float) pos.y;
        part.zRot += (float) pos.z;
    }
    private void applyScale(Pair<KeyFrame,KeyFrame> scalePair,AnimationData data,FDModelPart part,float time){
        Vec3 pos = FDAnimationsHelper.getCurrentScale(scalePair,data,time);
        part.scaleX += (float) pos.x;
        part.scaleY += (float) pos.y;
        part.scaleZ += (float) pos.z;
    }


    public int tickLength(){
        return (int)(this.animTime*20);
    }

    public Mode getMode() {
        return mode;
    }

    public ResourceLocation getName() {
        return name;
    }

    public static Animation generateToNullAnimation(AnimationTicker  currentAnimation, int currentAnimationTime){
        List<AnimationData> toNullDatas = new ArrayList<>();
        float time = currentAnimationTime / 20f;
        float timeOverride = currentAnimation.getToNullAnimTime() != -1 ? currentAnimation.getToNullAnimTime() / 20f : time;
        for (AnimationData data : currentAnimation.getAnimation().boneData.values()){
            toNullDatas.add(createToZeroAnimationData(data,time,timeOverride));
        }
        return new ToNullAnimation(toNullDatas,Mode.PLAY_ONCE,timeOverride);
    }

    public static Animation generateTransitionAnimation(Animation currentAnimation,Animation target,int currentAnimationTime){
        List<AnimationData> datas = new ArrayList<>();
        fillAnimationDatas(datas,currentAnimation,target,currentAnimationTime);


        return new TransitionAnimation(new ResourceLocation(SolarCraft.MOD_ID,"transition"),datas,target.getMode(),target.animTime,target);
    }

    private static void fillAnimationDatas(List<AnimationData> datas,Animation currentAnimation,Animation target,int currentAnimationTime){
        float time = currentAnimationTime / 20f;
        var missingCurrentAnimDatas = new HashMap<>(target.boneData);
        for (AnimationData data : currentAnimation.boneData.values()){
            var keyframes = FDAnimationsHelper.generateKeyFramesForTime(data, KeyFrame.LerpMode.CATMULLROM, time);
            if (!target.boneData.containsKey(data.getBoneName())){
                AnimationData d = createToZeroAnimationData(data,time,target.animTime);
                datas.add(d);
            } else {
                missingCurrentAnimDatas.remove(data.getBoneName());
                AnimationData targetData = target.boneData.get(data.getBoneName());
                AnimationData newData = targetData.copyWithReplacedFirst(keyframes.left, keyframes.middle, keyframes.right);
                datas.add(newData);
            }
        }
        for (var entry : missingCurrentAnimDatas.entrySet()){
            AnimationData data = entry.getValue();
            var keyframes = FDAnimationsHelper.generateKeyFramesForTime(data, KeyFrame.LerpMode.CATMULLROM, time);
            AnimationData newData = data.copyWithReplacedFirst(keyframes.left, keyframes.middle, keyframes.right);
            datas.add(newData);
        }
    }

    private static AnimationData createToZeroAnimationData(AnimationData data,float time,float fullAnimationTime){
        var keyframes = FDAnimationsHelper.generateKeyFramesForTime(data, KeyFrame.LerpMode.LINEAR, time);
        KeyFrame end = new KeyFrame(Vec3.ZERO,Vec3.ZERO, KeyFrame.LerpMode.LINEAR,fullAnimationTime,1);
        AnimationData d = new AnimationData(data.getBoneName(),
                List.of(keyframes.middle,end),
                List.of(keyframes.left,end),
                List.of(keyframes.right,end)
        );
        return d;
    }

    public enum Mode{
        PLAY_ONCE,
        HOLD_ON_LAST_FRAME,
        LOOP
    }


    public static Animation parseAnimation(ResourceLocation name,JsonObject sanimation){
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
                rotation = KeyFrame.parseKeyFrameList(srotation,-1,-1,1,0,0,0);
            }else{
                rotation = new ArrayList<>();
            }
            if (boneData.has("position")) {
                JsonElement sposition = boneData.get("position");
                position = KeyFrame.parseKeyFrameList(sposition,-1,1,1,0,0,0);
            }else{
                position = new ArrayList<>();
            }
            if (boneData.has("scale")) {
                JsonElement sscale = boneData.get("scale");
                scale = KeyFrame.parseKeyFrameList(sscale,1,1,1,-1,-1,-1);
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
        return new Animation(name,datas,mode,length);
    }


//    private static JsonElement loadJsonFile(ResourceLocation location){
//        ResourceManager manager = Minecraft.getInstance().getResourceManager();
//        Optional<Resource> modelJson = manager.getResource(location);
//        try{
//            if (modelJson.isPresent()){
//
//                BufferedReader stream = modelJson.get().openAsReader();
//                JsonElement element = GSON.fromJson(stream,JsonElement.class);
//                stream.close();
//                return element;
//            }else{
//                throw new RuntimeException("Couldn't find animation: " + location);
//            }
//        }catch (Exception e){
//            throw new RuntimeException("Error loading animation file: " + location,e);
//        }
//    }
}
