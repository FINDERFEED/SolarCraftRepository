package com.finderfeed.solarcraft.events.misc;

import com.finderfeed.solarcraft.local_library.other.CanTick;

public class ClientTicker implements CanTick {

    private final String id;
    private final int max;
    private int currentValue = 0;
    private boolean shouldBeRemoved = false;


    public ClientTicker(String id,int max){
        this.id = id;
        this.max = max;
    }


    public String getId() {
        return id;
    }

    public int getMax() {
        return max;
    }


    @Override
    public void tick() {
        if (currentValue+1 <= max){
            currentValue++;
        }else{
            shouldBeRemoved = true;
        }
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public boolean shouldBeRemoved() {
        return shouldBeRemoved;
    }
}
