package com.finderfeed.solarcraft.client.screens.ability_screen;

import net.minecraft.util.Mth;

public class InterpolatedValueAbilityScreen {

    private double currentValue = 0;
    private double newValue = 0;
    private double oldValue = 0;
    private int ticker = 0;

    private int time;
    public InterpolatedValueAbilityScreen(int time){
        this.time = time;
    }

    public void tick(){
        float percentage = Mth.clamp(ticker,0,time)/(float)time;
        double progress = -Math.pow(percentage,2) + 1;
        currentValue = Mth.lerp(progress,oldValue,newValue);
        ticker = Mth.clamp(ticker-1,0,time);
    }

    public void setNewValue(int newValue) {
        if (newValue != this.newValue) {
            ticker = time;
            this.oldValue = currentValue;
            this.newValue = newValue;
        }
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public double getCurrentValue(float partialTick) {
        float dir = currentValue >= newValue ? -1 : 1;
        return currentValue + Mth.clamp(Math.max(currentValue,newValue) -
                Math.min(currentValue,newValue),0,1) * partialTick * dir;
    }

    public double getNewValue() {
        return newValue;
    }

}
