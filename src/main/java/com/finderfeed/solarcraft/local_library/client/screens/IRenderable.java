package com.finderfeed.solarcraft.local_library.client.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

@FunctionalInterface
public interface IRenderable {

    void render(GuiGraphics graphics, float x, float y);

}
