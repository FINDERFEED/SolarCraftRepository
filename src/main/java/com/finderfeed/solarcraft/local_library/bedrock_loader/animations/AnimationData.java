package com.finderfeed.solarcraft.local_library.bedrock_loader.animations;

import com.finderfeed.solarcraft.local_library.helpers.NumberRange;
import com.finderfeed.solarcraft.local_library.helpers.NumberRangeObjectGetter;
import com.finderfeed.solarcraft.local_library.helpers.mafs.CatmullRomSpline;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class AnimationData {

    private CatmullRomSpline positionSpline;
    private CatmullRomSpline rotationSpline;
    private CatmullRomSpline scaleSpline;

    private NumberRangeObjectGetter<KeyFrame> rotationGetter;

    private NumberRangeObjectGetter<KeyFrame> positionGetter;

    private NumberRangeObjectGetter<KeyFrame> scaleGetter;

    private String boneName;

    public AnimationData(String boneName,List<KeyFrame> rotation,List<KeyFrame> position,List<KeyFrame> scale){
        this.rotationGetter = this.toObjectGetter(rotation);
        this.positionGetter = this.toObjectGetter(position);
        this.scaleGetter = this.toObjectGetter(scale);
        this.boneName = boneName;
        if (position.size() > 1) {
            positionSpline = new CatmullRomSpline(position.stream().map(KeyFrame::getPost).toArray(Vec3[]::new));
        }
        if (rotation.size() > 1) {
            rotationSpline = new CatmullRomSpline(rotation.stream().map(KeyFrame::getPost).toArray(Vec3[]::new));
        }
        if (scale.size() > 1) {
            scaleSpline = new CatmullRomSpline(scale.stream().map(KeyFrame::getPost).toArray(Vec3[]::new));
        }
    }

    public List<KeyFrame> getPosition() {
        return positionGetter.getValues();
    }

    public List<KeyFrame> getRotation() {
        return rotationGetter.getValues();
    }

    public List<KeyFrame> getScale() {
        return scaleGetter.getValues();
    }

    public Pair<KeyFrame,KeyFrame> getCurrentAndNextPositionKeyFrame(float currentTime){
        if (positionGetter == null){
            return null;
        }
        return positionGetter.getObjectAndNext(currentTime);
    }

    public Pair<KeyFrame,KeyFrame> getCurrentAndNextRotationKeyFrame(float currentTime){
        if (rotationGetter == null){
            return null;
        }
        return rotationGetter.getObjectAndNext(currentTime);
    }

    public Pair<KeyFrame,KeyFrame> getCurrentAndNextScaleKeyFrame(float currentTime){
        if (scaleGetter == null){
            return null;
        }
        return scaleGetter.getObjectAndNext(currentTime);
    }

    public String getBoneName() {
        return boneName;
    }

    public CatmullRomSpline getPositionSpline() {
        return positionSpline;
    }

    public CatmullRomSpline getRotationSpline() {
        return rotationSpline;
    }

    public CatmullRomSpline getScaleSpline() {
        return scaleSpline;
    }

    private NumberRangeObjectGetter<KeyFrame> toObjectGetter(List<KeyFrame> frames){
        if (frames.size() > 1) {
            List<NumberRange> ranges = new ArrayList<>();
            for (int i = 0; i < frames.size() - 1; i++) {
                KeyFrame keyFrame = frames.get(i);
                KeyFrame keyFrameNext = frames.get(i + 1);
                float val1 = keyFrame.getTime();
                float val2 = keyFrameNext.getTime();
                NumberRange range = new NumberRange(val1,val2);
                ranges.add(range);
            }
            ranges.add(new NumberRange(frames.get(frames.size()-1).getTime(),Integer.MAX_VALUE));
            return new NumberRangeObjectGetter<>(frames,ranges);
        }else if (frames.size() == 1){
            return new NumberRangeObjectGetter<>(frames,List.of(new NumberRange(0,Integer.MAX_VALUE)));
        }else{
            return null;
        }
    }
}
