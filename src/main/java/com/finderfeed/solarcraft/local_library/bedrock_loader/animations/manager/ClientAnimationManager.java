package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager;

import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.misc.DummyAnimation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.misc.ToNullAnimation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.misc.TransitionAnimation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClientAnimationManager implements AnimationManager{

    public Map<String, AnimationTicker> tickers = new HashMap<>();

    @Override
    public void tickAnimations(){
        var iterator = tickers.entrySet().iterator();
        while (iterator.hasNext()){
            var entry = iterator.next();
            AnimationTicker ticker = entry.getValue();
            if (!ticker.ended()){
                ticker.tick();
            }else{
                this.handleAnimationModes(iterator,ticker);
            }
        }
    }

    private void handleAnimationModes(Iterator<Map.Entry<String,AnimationTicker>> iterator, AnimationTicker ticker){
        Animation animation = ticker.getAnimation();
        if (animation.getMode() == Animation.Mode.LOOP){
            if (animation instanceof TransitionAnimation transition) {
                ticker.setAnimation(transition.getOriginalAnimation());
            }
            ticker.reset();
        }else if (animation.getMode() == Animation.Mode.PLAY_ONCE){
            if (animation instanceof ToNullAnimation){
                iterator.remove();
            } else {
                Animation newanim = Animation.generateToNullAnimation(ticker,ticker.getCurrentTime());
                ticker.reset();
                ticker.setAnimation(newanim);
                ticker.setReplaceable(true);
                ticker.setToNullAnimTime(newanim.tickLength());
            }
        }
    }



    @Override
    public void stopAnimation(String tickerName){
        AnimationTicker ticker = this.tickers.get(tickerName);
        if (ticker == null) return;
        if (ticker.getAnimation() instanceof ToNullAnimation) return;

        Animation animation = Animation.generateToNullAnimation(ticker,ticker.getCurrentTime());
        this.tickers.put(tickerName,new AnimationTicker.Builder(animation)
                        .replaceable(true)
                .build());
    }

    @Override
    public AnimationTicker getTicker(String name) {
        return this.tickers.get(name);
    }

    @Override
    public void setAnimation(String tickerName,AnimationTicker animation){
        AnimationTicker c = this.tickers.get(tickerName);
        if (c == null){
            this.tickers.put(tickerName,animation);
        }else{
            if (c.isReplaceable() || (c.getAnimation() != animation.getAnimation())){
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
