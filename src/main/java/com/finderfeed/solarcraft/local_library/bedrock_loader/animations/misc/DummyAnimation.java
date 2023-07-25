package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.misc;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.AnimationData;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class DummyAnimation extends Animation {

    private static final ResourceLocation DUMMY = new ResourceLocation(SolarCraft.MOD_ID,"dummy_null");

    public DummyAnimation(float animTime) {
        super(DUMMY, List.of(), Mode.PLAY_ONCE, animTime);
    }


}
