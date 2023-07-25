package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager;

import java.util.HashMap;
import java.util.Map;

public class ServerAnimationManager implements AnimationManager{

    public Map<String,AnimationTicker> tickers = new HashMap<>();

    public ServerAnimationManager(){

    }

    @Override
    public void tickAnimations() {

    }

    @Override
    public void setAnimation(String tickerName, AnimationTicker animation) {

    }

    @Override
    public void stopAnimation(String tickerName) {

    }
}
