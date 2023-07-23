package com.finderfeed.solarcraft.local_library.bedrock_loader.animations;

import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModel;

import java.util.HashMap;
import java.util.Map;

public class ClientAnimationManager {

    public Map<String,AnimationTicker> tickers = new HashMap<>();

    public void tickAnimations(){
        var iterator = tickers.entrySet().iterator();
        while (iterator.hasNext()){
            var entry = iterator.next();
            AnimationTicker ticker = entry.getValue();
            if (!ticker.ended()){
                ticker.tick();
            }else{
                if (this.handleAnimationModes(ticker)){
                    iterator.remove();
                }
            }
        }
    }

    private boolean handleAnimationModes(AnimationTicker ticker){
        switch (ticker.getAnimation().getMode()){
            case LOOP -> {
                ticker.reset();
                return false;
            }
            case HOLD_ON_LAST_FRAME -> {
                return false;
            }
            default -> {
                return true;
            }
        }
    }

    public void removeAnimation(String tickerName){
        AnimationTicker ticker = this.tickers.get(tickerName);
        if (ticker == null) return;
        if (ticker.getAnimation() instanceof ToNullAnimation) return;

        Animation animation = Animation.generateToNullAnimation(ticker.getAnimation(),ticker.getCurrentTime());
        this.tickers.put(tickerName,new AnimationTicker.Builder(animation)
                        .replaceable(true)
                .build());
    }

    public void setAnimation(String tickerName,AnimationTicker animation){
        AnimationTicker c = this.tickers.get(tickerName);
        if (c == null){
            this.tickers.put(tickerName,animation);
        }else{
            if (c.isReplaceable()){
                Animation transition = Animation.generateTransitionAnimation(c.getAnimation(),animation.getAnimation(),c.getCurrentTime());
                AnimationTicker ticker = new AnimationTicker(animation,transition);
                this.tickers.put(tickerName,ticker);
            }
        }
    }

    public void applyAnimations(FDModel model,float pticks){
        model.resetTransformations();
        for (AnimationTicker ticker : tickers.values()){
            Animation animation = ticker.getAnimation();
            animation.applyAnimation(model,ticker.getCurrentTime(),pticks);
        }
    }
}