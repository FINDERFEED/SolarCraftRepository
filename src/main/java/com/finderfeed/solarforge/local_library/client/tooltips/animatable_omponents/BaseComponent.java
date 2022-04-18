package com.finderfeed.solarforge.local_library.client.tooltips.animatable_omponents;

import com.mojang.blaze3d.vertex.PoseStack;

public abstract class BaseComponent {

    private int xSize;
    private int ySize;
    private ContentAlignment alignment;
    private int innerBorder = 0;

    public BaseComponent(ContentAlignment alignment,int xSize,int ySize){
        this.xSize = xSize;
        this.ySize = ySize;
        this.alignment = alignment;
    }


    public abstract void render(PoseStack matrices,int x,int y,float pTicks,int mouseX,int mouseY,int ticker,int animationLength);

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    public void setYSize(int ySize) {
        this.ySize = ySize;
    }

    public ContentAlignment getAlignment() {
        return alignment;
    }

    public BaseComponent setInnerBorder(int innerBorder) {
        this.innerBorder = innerBorder;
        this.ySize += innerBorder*2;
        this.xSize += innerBorder*2;
        return this;
    }

    public int getInnerBorder() {
        return innerBorder;
    }

    public BaseComponent forceXSize(int size){
        this.xSize = size;
        return this;
    }

    public BaseComponent forceYSize(int size){
        this.ySize = size;
        return this;
    }
}
