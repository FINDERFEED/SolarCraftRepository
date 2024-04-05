package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.misc;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.AnimationData;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ToNullAnimation extends Animation {
    public ToNullAnimation(List<AnimationData> data, Mode mode, float animTime) {
        super(new ResourceLocation(SolarCraft.MOD_ID,"to_null_transition"),data, mode, animTime);
    }
}
