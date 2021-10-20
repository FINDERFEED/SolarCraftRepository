package com.finderfeed.solarforge.for_future_library.other;

import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import net.minecraft.util.Mth;

public class EaseOut implements CanTick{

    private int ticker = 0;
    private double duration;
    private double start = 0;
    private double end;
    private final double modifier;
    public EaseOut(double end, double duration,double modifier){
        this.duration = duration;
        this.end = end;
        this.modifier = modifier;
    }


    public double getValue(){
        double time = FinderfeedMathHelper.clamp(start,ticker,duration);
        return Mth.lerp(FinderfeedMathHelper.FLIP.apply(Math.pow(FinderfeedMathHelper.FLIP.apply(time/duration),modifier)),start,end);
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    @Override
    public void tick(){
        this.ticker+=1;
    }

    public void reset(){
        this.ticker = 0;
    }
}
