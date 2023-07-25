package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager;

public interface AnimationManager {

    void tickAnimations();

    void setAnimation(String tickerName, AnimationTicker animation);

    void stopAnimation(String tickerName);

}
