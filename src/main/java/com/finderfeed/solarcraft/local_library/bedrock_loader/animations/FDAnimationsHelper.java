package com.finderfeed.solarcraft.local_library.bedrock_loader.animations;

import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModelPart;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.ImmutableTriple;

public class FDAnimationsHelper {


    public static Vec3 getCurrentPosition(Pair<KeyFrame,KeyFrame> positionPair, AnimationData data, float time){
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
                    return pos;
                } else {
                    int index = frame1.getIndex();
                    Vec3 pos = data.getPositionSpline().pointBetweenPoints(index, percent);
                    return pos;
                }
            }else{
                Vec3 pos = time < frame1.getTime() ? frame1.getPre() : frame1.getPost();
                return pos;
            }
        }else{
            return Vec3.ZERO;
        }
    }

    public static Vec3 getCurrentScale(Pair<KeyFrame,KeyFrame> scalePair, AnimationData data, float time){
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
                    return pos;
                } else {
                    int index = frame1.getIndex();
                    Vec3 pos = data.getScaleSpline().pointBetweenPoints(index, percent);
                    return pos;
                }
            }else{
                Vec3 pos = time < frame1.getTime() ? frame1.getPre() : frame1.getPost();
                return pos;
            }
        }else{
            return Vec3.ZERO;
        }
    }

    public static Vec3 getCurrentRotation(Pair<KeyFrame,KeyFrame> rotationPair, AnimationData data, float time){
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
                    return pos;
                } else {
                    int index = frame1.getIndex();
                    Vec3 pos = data.getRotationSpline().pointBetweenPoints(index, percent);
                    return pos;
                }
            }else{
                Vec3 pos = time < frame1.getTime() ? frame1.getPre() : frame1.getPost();
                return pos;
            }
        }else{
            return Vec3.ZERO;
        }
    }

    public static ImmutableTriple<KeyFrame,KeyFrame,KeyFrame> generateKeyFramesForTime(AnimationData data, KeyFrame.LerpMode lerpMode,float time){
        Pair<KeyFrame, KeyFrame> currentPosKeyFrames = data.getCurrentAndNextPositionKeyFrame(time);
        Pair<KeyFrame, KeyFrame> currentRotKeyFrames = data.getCurrentAndNextRotationKeyFrame(time);
        Pair<KeyFrame, KeyFrame> currentScaleKeyFrames = data.getCurrentAndNextScaleKeyFrame(time);
        Vec3 currentPos = FDAnimationsHelper.getCurrentPosition(currentPosKeyFrames,data,time);
        Vec3 currentRot = FDAnimationsHelper.getCurrentRotation(currentRotKeyFrames,data,time);
        Vec3 currentScale = FDAnimationsHelper.getCurrentScale(currentScaleKeyFrames,data,time);
        KeyFrame keyFramePos = new KeyFrame(currentPos,currentPos, lerpMode,0,0);
        KeyFrame keyFrameRot = new KeyFrame(currentRot,currentRot, lerpMode,0,0);
        KeyFrame keyFrameScale = new KeyFrame(currentScale,currentScale, lerpMode,0,0);
        return ImmutableTriple.of(keyFramePos,keyFrameRot,keyFrameScale);
    }

}
