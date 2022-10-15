package com.finderfeed.solarcraft.misc_things;

public interface IEnergyUser {
    int giveEnergy(int a);
    int getMaxEnergy();
    boolean requriesEnergy();
    int getRadius();
}
