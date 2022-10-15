package com.finderfeed.solarcraft.local_library.other;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import net.minecraft.util.Mth;

public class EaseIn extends InterpolatedValue{

    public EaseIn(double start,double end, double duration){
        super(start,end,duration);
        this.start = start;
        this.duration = duration;
        this.end = end;
    }



    public double getValue(){
        double time = FDMathHelper.clamp(0,ticker,duration);
        return Mth.lerp(FDMathHelper.SQUARE.apply(time/duration),start,end);
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    @Override
    public void tick(){
        if (ticker+1 <= duration){
            ticker++;
        }
    }
    public void tickBackwards(){
        if (ticker-1 >= 0){
            ticker--;
        }
    }

    public void reset(){
        this.ticker = 0;
    }

}
