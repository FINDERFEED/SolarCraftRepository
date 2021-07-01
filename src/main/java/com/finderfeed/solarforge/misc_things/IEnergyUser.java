package com.finderfeed.solarforge.misc_things;

public interface IEnergyUser {
    void giveEnergy(int a);
    int getMaxEnergy();
    boolean requriesEnergy();
    int getRadius();
}
