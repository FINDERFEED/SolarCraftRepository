package com.finderfeed.solarforge.local_library.other;

public abstract class InterpolatedValue implements CanTick{

    protected int ticker = 0;
    protected double duration;
    protected double start = 0;
    protected double end;

    public InterpolatedValue(double start,double end, double duration){
        this.start = start;
        this.duration = duration;
        this.end = end;
    }

    public abstract double getValue();

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

    public void setEnd(double end) {
        this.end = end;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean canBeDeleted(){
        return ticker == duration;
    }
}
