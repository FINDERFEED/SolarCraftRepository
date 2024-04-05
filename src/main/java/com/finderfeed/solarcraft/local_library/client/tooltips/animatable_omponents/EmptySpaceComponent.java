package com.finderfeed.solarcraft.local_library.client.tooltips.animatable_omponents;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

//don't ask me
public class EmptySpaceComponent extends BaseComponent{
    public EmptySpaceComponent(int xSize,int ySize) {
        super(ContentAlignment.NO_ALIGNMENT, xSize, ySize);
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float pTicks, int mouseX, int mouseY, int ticker, int animationLength) {

    }
}
