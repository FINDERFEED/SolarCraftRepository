package com.finderfeed.solarforge.for_future_library.other;

import net.minecraft.util.Mth;

public class CyclingInterpolatedValue implements CanTick{

    public int ticker = 0;
    public double end;
    public boolean reverse = false;
    public double duration = 0;


    public CyclingInterpolatedValue(int end,int duration){
        this.end = end;
        this.duration = duration;
    }


    public double getValue(){
        return Mth.clamp(0,Mth.lerp(ticker/duration,0,end),end);
    }


    public void setDuration(int a){
        this.duration = a;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public void tick() {
        if (reverse) {
            this.ticker--;
        }else{
            this.ticker++;
        }
        if (this.ticker >= duration){
            ticker = (int)Math.round(duration);
            reverse = true;
        }else if (this.ticker <= 0){
            ticker = 0;
            reverse = false;
        }
    }
}
