package com.finderfeed.solarforge.misc_things;

import net.minecraft.util.Mth;

public class Flash {

    private int inTime;
    private int outTime;
    private int stayTime;

    private int ticker = 0;

    public Flash(int inTime,int stayTime,int outTime){
        this.inTime = inTime;
        this.stayTime =stayTime;
        this.outTime = outTime;
    }

    public void tick(){
        this.ticker = Mth.clamp(this.ticker + 1,0,getAllTime());
    }

    public int getInTime() {
        return inTime;
    }

    public int getOutTime() {
        return outTime;
    }

    public int getStayTime() {
        return stayTime;
    }

    public int getTicker() {
        return ticker;
    }

    public boolean isFinished(){
        return this.ticker >= getAllTime();
    }

    public int getAllTime(){
        return stayTime + inTime + outTime;
    }
}
