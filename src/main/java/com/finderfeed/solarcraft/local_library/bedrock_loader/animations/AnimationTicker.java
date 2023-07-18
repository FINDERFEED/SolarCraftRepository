package com.finderfeed.solarcraft.local_library.bedrock_loader.animations;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;

public class AnimationTicker {


    private boolean replaceable;
    private int innerTicker;
    private Animation animation;


    public AnimationTicker(Animation animation){
        this.animation = animation;
        this.innerTicker = 0;
        this.replaceable = false;
    }
    public AnimationTicker(Builder builder){
        this.animation = builder.animation;
        this.innerTicker = builder.startFrom;
        this.replaceable = builder.replaceable;
    }

    public AnimationTicker(AnimationTicker ticker,Animation animation){
        this.replaceable = ticker.replaceable;
        this.innerTicker = ticker.innerTicker;
        this.animation = animation;
    }


    public void tick(){
        int maxtime = animation.getAnimTimeInTicks();
        innerTicker = FDMathHelper.clamp(0,innerTicker + 1,maxtime);
    }


    public void reset(){
        innerTicker = 0;
    }

    public boolean ended(){
        return this.innerTicker == animation.getAnimTimeInTicks();
    }

    public boolean isReplaceable() {
        return replaceable;
    }

    public int getCurrentTime() {
        return innerTicker;
    }

    public Animation getAnimation() {
        return animation;
    }

    public static class Builder{

        private boolean replaceable = false;
        private Animation animation;
        private int startFrom = 0;

        public Builder(Animation animation){
            this.animation = animation;
        }

        public Builder replaceable(boolean replaceable){
            this.replaceable = replaceable;
            return this;
        }

        public Builder startFrom(int tick){
            this.startFrom = tick;
            return this;
        }

        public AnimationTicker build(){
            return new AnimationTicker(this);
        }
    }
}
