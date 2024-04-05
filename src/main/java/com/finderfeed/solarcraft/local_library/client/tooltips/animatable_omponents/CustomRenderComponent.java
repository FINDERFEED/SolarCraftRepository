package com.finderfeed.solarcraft.local_library.client.tooltips.animatable_omponents;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public class CustomRenderComponent extends BaseComponent{

    private AnimatableScreenRenderable renderable;

    public CustomRenderComponent(ContentAlignment alignment, int xSize, int ySize, AnimatableScreenRenderable renderable) {
        super(alignment, xSize, ySize);
        this.renderable = renderable;
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float pTicks, int mouseX, int mouseY, int ticker, int animationLength) {
        int[] xy = this.getAlignment().getCoords(this,x,y);
        xy[0] += getInnerBorder();
        xy[1] += getInnerBorder();
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        this.renderable.render(graphics,xy[0],xy[1],pTicks,mouseX,mouseY,ticker,animationLength);
        matrices.popPose();
    }

}
