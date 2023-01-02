package com.finderfeed.solarcraft.local_library.client.screens;

import com.mojang.blaze3d.vertex.PoseStack;

@FunctionalInterface
public interface IRenderable {

    void render(PoseStack matrices,int x,int y);

}
