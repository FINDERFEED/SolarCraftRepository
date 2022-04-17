package com.finderfeed.solarforge.local_library.client.tooltips;

import com.finderfeed.solarforge.local_library.client.tooltips.animatable_omponents.ComponentSequence;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;

public abstract class AnimatedTooltip {
    protected int ticker = 0;
    protected int borderX1;
    protected int borderY1;
    protected int borderX2;
    protected int borderY2;
    protected int animLength;
    protected int borderWidth;
    protected boolean xFixed = false;
    protected boolean yFixed = false;
    protected int maxSizeX;
    protected int maxSizeY;
    protected AbstractWidget tiedWidget;
    private ComponentSequence sequence;

    public AnimatedTooltip(int borderX1,int borderY1,int borderX2,int borderY2,int animationLength,int borderWidth){
        this.borderX1 = borderX1;
        this.borderX2 = borderX2;
        this.borderY1 = borderY1;
        this.borderY2 = borderY2;
        this.animLength = animationLength;
        this.borderWidth = borderWidth;
    }

    public abstract void render(PoseStack matrices, int x, int y, float pTicks, int mousex, int mousey);

    public int calculateXRenderPos(int x){
        if (xFixed) return x;
        if (borderX2 - borderX1 <= maxSizeX) return borderX1;
        if (x + maxSizeX > borderX2) return borderX2 - maxSizeX;
        return Math.max(x, borderX1);
    }
    public int calculateYRenderPos(int y){
        if (yFixed) return y;
        if (borderY2 - borderY1 <= maxSizeY) return borderY1;
        if (y + maxSizeY > borderY2) return borderY2 - maxSizeY;
        return Math.max(y,borderY1);
    }

    public void tick(boolean forward){
        if (forward){
            if (ticker < animLength) ticker++;
        }else{
            if (ticker > 0) ticker--;
        }
    }

    public AnimatedTooltip setXFixed(boolean xFixed) {
        this.xFixed = xFixed;
        return this;
    }

    public AnimatedTooltip setYFixed(boolean yFixed) {
        this.yFixed = yFixed;
        return this;
    }

    public AnimatedTooltip addComponents(ComponentSequence sequence){
        this.sequence = sequence;
        this.maxSizeX = sequence.getXSize() + borderWidth*2;
        this.maxSizeY = sequence.getYSize() + borderWidth*2;
        return this;
    }

    public ComponentSequence getComponents() {
        return sequence;
    }

    public AnimatedTooltip tieToWidget(AbstractWidget widget){
        this.tiedWidget = widget;
        return this;
    }

    public AbstractWidget getTiedWidget() {
        return tiedWidget;
    }
}
