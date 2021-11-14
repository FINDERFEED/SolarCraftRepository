package com.finderfeed.solarforge.events.misc;

import com.finderfeed.solarforge.events.other_events.event_handler.ClientEventsHandler;
import com.finderfeed.solarforge.for_future_library.other.CanTick;

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
        if (currentValue++ <= max){
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
