package com.finderfeed.solarforge.for_future_library.other;

import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import net.minecraft.util.Mth;

public class EaseInOut implements CanTick{
    private int ticker = 0;
    private double duration;
    private double start = 0;
    private double end;
    private final double modifier;
    public EaseInOut(double start,double end, double duration,double modifier){
        this.start = start;
        this.duration = duration;
        this.end = end;
        this.modifier = modifier;
    }


    public double getValue(){
        double time = FinderfeedMathHelper.clamp(start,ticker,duration);
        double easein= FinderfeedMathHelper.SQUARE.apply(time/duration);
        double easeout = FinderfeedMathHelper.FLIP.apply(Math.pow(FinderfeedMathHelper.FLIP.apply(time/duration),modifier));
        return Mth.lerp(Mth.lerp(time/duration,easein,easeout),start,end);
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    @Override
    public void tick(){
        if (this.ticker + 1 <= duration) {
            this.ticker += 1;
        }
    }

    public void tickBackwards(){
        if (this.ticker -1 >= 0) {
            this.ticker -= 1;
        }
    }

    public void reset(){
        this.ticker = 0;
    }
}
