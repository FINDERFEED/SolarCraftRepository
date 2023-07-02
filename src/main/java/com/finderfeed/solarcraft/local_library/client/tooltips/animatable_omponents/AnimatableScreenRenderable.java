package com.finderfeed.solarcraft.local_library.client.tooltips.animatable_omponents;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

@FunctionalInterface
public interface AnimatableScreenRenderable {

    void render(GuiGraphics graphics, int x, int y, float pTicks, int mouseX, int mouseY, int ticker, int animationLength);

}
