package com.finderfeed.solarcraft.content.entities.models;


import com.finderfeed.solarcraft.content.entities.RunicElementalBoss;

@FunctionalInterface
public interface RunicElementalModelAnimation {
    void animate(RunicElementalBoss entity, RunicElementalModel model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, int ticker);
}
