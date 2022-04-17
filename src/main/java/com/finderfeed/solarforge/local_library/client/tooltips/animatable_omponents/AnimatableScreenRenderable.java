package com.finderfeed.solarforge.local_library.client.tooltips.animatable_omponents;


import com.mojang.blaze3d.vertex.PoseStack;

@FunctionalInterface
public interface AnimatableScreenRenderable {

    void render(PoseStack matrices,int x,int y,float pTicks,int mouseX,int mouseY,int ticker,int animationLength);

}
