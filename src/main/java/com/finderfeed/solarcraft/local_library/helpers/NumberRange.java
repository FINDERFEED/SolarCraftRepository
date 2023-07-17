package com.finderfeed.solarcraft.local_library.helpers;

public class NumberRange {

    private double begin;
    private double end;

    public NumberRange(double begin,double end) {
        if (begin > end){
            throw new IllegalStateException("Begin could not be larger than end!");
        }
        this.begin = begin;
        this.end = end;
    }

    public boolean isInRange(double value){
        return value >= begin && value <= end;
    }

    public boolean isInRangeEndExclusive(double value){
        return value >= begin && value < end;
    }

    public boolean isBetweenRange(double value){
        return value > begin && value < end;
    }

    public boolean isAfterEnd(double value){
        return value > end;
    }

    public boolean isBeforeBegin(double value){
        return value < begin;
    }

    public boolean isAfterEndInclusive(double value){
        return value >= end;
    }

    public boolean isBeforeBeginInclusive(double value){
        return value <= begin;
    }

    public double getBegin() {
        return begin;
    }

    public double getEnd() {
        return end;
    }
}
