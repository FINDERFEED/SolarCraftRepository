package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager;

import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.misc.DummyAnimation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.misc.ToNullAnimation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class ServerAnimationManager implements AnimationManager{

    public Map<String,AnimationTicker> tickers = new HashMap<>();

    public ServerAnimationManager(){

    }

    @Override
    public void tickAnimations() {
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

    private void handleAnimationModes(Iterator<Map.Entry<String,AnimationTicker>> iterator,AnimationTicker ticker){
        Animation animation = ticker.getAnimation();
        if (animation.getMode() == Animation.Mode.LOOP){
            ticker.reset();
        }else if (animation.getMode() == Animation.Mode.PLAY_ONCE){
            if (animation instanceof DummyAnimation){
                iterator.remove();
            } else {
                ticker.reset();
                DummyAnimation dummyAnimation = new DummyAnimation(ticker.getCurrentTime() / 20f);
                ticker.setAnimation(dummyAnimation);
                ticker.setReplaceable(true);
            }
        }
    }



    @Override
    public void setAnimation(String tickerName, AnimationTicker animation) {
        AnimationTicker current = tickers.get(tickerName);
        if (current == null){
            this.tickers.put(tickerName,animation);
            this.sendAnimationStartPacket(tickerName,animation);
        }else{
            if (current.isReplaceable() || (current.getAnimation() != animation.getAnimation())){
                this.tickers.put(tickerName,animation);
                this.sendAnimationStartPacket(tickerName,animation);
            }
        }
    }

    @Override
    public void stopAnimation(String tickerName) {
        AnimationTicker ticker = tickers.get(tickerName);
        if (ticker != null && !(ticker.getAnimation() instanceof DummyAnimation)){
            DummyAnimation dummyAnimation = new DummyAnimation(ticker.getCurrentTime() / 20f);
            this.tickers.put(tickerName,new AnimationTicker(true,0,dummyAnimation,-1));
            this.sendAnimationStopPacket(tickerName);
        }
    }

    @Override
    public AnimationTicker getTicker(String name) {
        return this.tickers.get(name);
    }

    public abstract void sendAnimationStartPacket(String tickerName,AnimationTicker animationTicker);
    public abstract void sendAnimationStopPacket(String tickerName);

}
