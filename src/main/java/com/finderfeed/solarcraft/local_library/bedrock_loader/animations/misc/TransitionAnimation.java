package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.misc;

import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.AnimationData;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class TransitionAnimation extends Animation {

    private Animation originalAnimation;
    public TransitionAnimation(ResourceLocation name, List<AnimationData> data, Mode mode, float animTime,Animation original) {
        super(name, data, mode, animTime);
        this.originalAnimation = original;
    }

    public Animation getOriginalAnimation() {
        return originalAnimation;
    }


}
