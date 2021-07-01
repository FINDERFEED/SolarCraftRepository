package com.finderfeed.solarforge.capability_mana;

public class SolarManaCapability implements SolarForgeMana{
    public double mana;

    @Override
    public void setMana(double mana) {
        this.mana = mana;

    }

    @Override
    public double getMana() {
        return mana;
    }
}
