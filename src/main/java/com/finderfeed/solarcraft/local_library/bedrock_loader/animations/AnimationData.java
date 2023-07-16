package com.finderfeed.solarcraft.local_library.bedrock_loader.animations;

import java.util.List;

public class AnimationData {

    private List<KeyFrame> rotation;

    private List<KeyFrame> position;

    private List<KeyFrame> scale;

    private String boneName;

    public AnimationData(String boneName,List<KeyFrame> rotation,List<KeyFrame> position,List<KeyFrame> scale){
        this.rotation = rotation;
        this.position = position;
        this.scale = scale;
        this.boneName = boneName;
    }

    public List<KeyFrame> getPosition() {
        return position;
    }

    public List<KeyFrame> getRotation() {
        return rotation;
    }

    public List<KeyFrame> getScale() {
        return scale;
    }

    public String getBoneName() {
        return boneName;
    }
}
