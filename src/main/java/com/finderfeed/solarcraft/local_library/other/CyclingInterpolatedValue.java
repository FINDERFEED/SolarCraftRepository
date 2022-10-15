package com.finderfeed.solarcraft.local_library.other;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import net.minecraft.util.Mth;

public class CyclingInterpolatedValue extends InterpolatedValue{

    public int ticker = 0;
    public double end;
    public boolean reverse = false;
    public int duration = 0;


    public CyclingInterpolatedValue(double start,double end,int duration){
        super(start,end,duration);
        this.end = end;
        this.duration = duration;
    }


    public double getValue(){

        double lerped = Mth.lerp((float)ticker/duration, start,end);

        return FDMathHelper.clamp(0,
                lerped,
                end);
    }


    public void setDuration(int a){
        this.duration = a;
    }

    public void setEnd(double end) {
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
            ticker = duration;
            reverse = true;
        }else if (this.ticker <= 0){
            ticker = 0;
            reverse = false;
        }
    }
}
