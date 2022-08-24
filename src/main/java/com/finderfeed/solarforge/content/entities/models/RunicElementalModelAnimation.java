package com.finderfeed.solarforge.content.entities.models;


import com.finderfeed.solarforge.content.entities.RunicElementalBoss;

@FunctionalInterface
public interface RunicElementalModelAnimation {
    void animate(RunicElementalBoss entity, RunicElementalModel model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, int ticker);
}
