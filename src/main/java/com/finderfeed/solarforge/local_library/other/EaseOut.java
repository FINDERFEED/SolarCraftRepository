package com.finderfeed.solarforge.local_library.other;

import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import net.minecraft.util.Mth;

public class EaseOut extends InterpolatedValue implements CanTick{


    private final double modifier;
    public EaseOut(double start,double end, double duration,double modifier){
        super(start,end,modifier);
        this.duration = duration;
        this.end = end;
        this.modifier = modifier;
    }



    public double getValue(){
        double time = FinderfeedMathHelper.clamp(0,ticker,duration);
        return Mth.lerp(FinderfeedMathHelper.FLIP.apply(Math.pow(FinderfeedMathHelper.FLIP.apply(time/duration),modifier)),start,end);
    }


    @Override
    public void tick(){
        this.ticker+=1;
    }

}
