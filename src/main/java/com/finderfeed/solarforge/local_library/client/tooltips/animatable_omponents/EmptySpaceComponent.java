package com.finderfeed.solarforge.local_library.client.tooltips.animatable_omponents;

import com.mojang.blaze3d.vertex.PoseStack;

//don't ask me
public class EmptySpaceComponent extends BaseComponent{
    public EmptySpaceComponent(int xSize,int ySize) {
        super(ContentAlignment.NO_ALIGNMENT, xSize, ySize);
    }

    @Override
    public void render(PoseStack matrices, int x, int y, float pTicks, int mouseX, int mouseY,int ticker,int animationLength) {

    }
}
