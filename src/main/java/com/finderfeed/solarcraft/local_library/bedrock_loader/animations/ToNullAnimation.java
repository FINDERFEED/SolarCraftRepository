package com.finderfeed.solarcraft.local_library.bedrock_loader.animations;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ToNullAnimation extends Animation{
    protected ToNullAnimation(List<AnimationData> data, Mode mode, float animTime) {
        super(new ResourceLocation(SolarCraft.MOD_ID,"to_null_transition"),data, mode, animTime);
    }
}
