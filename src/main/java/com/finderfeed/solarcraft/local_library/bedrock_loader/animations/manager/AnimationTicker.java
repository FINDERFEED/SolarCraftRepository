package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager;

import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.registries.animations.AnimationReloadableResourceListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class AnimationTicker {


    private boolean replaceable;
    private int innerTicker;
    private Animation animation;


    private AnimationTicker(boolean replaceable,int ticker,Animation animation){
        this.replaceable = replaceable;
        this.innerTicker = ticker;
        this.animation = animation;
    }

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


    public CompoundTag serialize(){
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("replaceable",this.replaceable);
        tag.putInt("time",this.innerTicker);
        tag.putString("animation_name",this.animation.getName().toString());
        return tag;
    }

    public static AnimationTicker deserialize(CompoundTag tag){
        boolean replaceable = tag.getBoolean("replaceable");
        int time = tag.getInt("time");
        ResourceLocation name = new ResourceLocation(tag.getString("animation_name"));
        Animation animation = AnimationReloadableResourceListener.INSTANCE.getAnimation(name);
        return new AnimationTicker(replaceable,time,animation);
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
