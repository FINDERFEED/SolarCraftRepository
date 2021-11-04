package com.finderfeed.solarforge.client.custom_tooltips;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import net.minecraft.resources.ResourceLocation;

public class CustomTooltip{


    private final ResourceLocation CORNER_LOCATION;
    private final ResourceLocation TOP_LOCATION;
    private final ResourceLocation BOTTOM_LOCATION;
    private final int topTextureWidth;
    private final int topTextureHeight;
    private final int bottomTextureWidth;
    private final int bottomTextureHeight;
    private final int cornerDimensions;
    private int yOffsetTop = 0;
    private int yOffsetBottom = 0;

    private final int backgroundColor;
    private final int borderColorStart;
    private final int borderColorEnd;

    public CustomTooltip(String texture,int topTextureWidth,int topTextureHeight,int bottomTextureWidth,int bottomTextureHeight,int cornerDimensions,int colBorderStart,int colBorderEnd, int colBackground){
        CORNER_LOCATION = new ResourceLocation(SolarForge.MOD_ID,"textures/custom_tooltips/" + texture + "_corner.png");
        TOP_LOCATION = new ResourceLocation(SolarForge.MOD_ID,"textures/custom_tooltips/" + texture + "_top.png");
        BOTTOM_LOCATION = new ResourceLocation(SolarForge.MOD_ID,"textures/custom_tooltips/" + texture + "_bottom.png");
        this.topTextureWidth = topTextureWidth;
        this.topTextureHeight = topTextureHeight;
        this.cornerDimensions = cornerDimensions;
        this.backgroundColor = colBackground;
        this.borderColorStart = colBorderStart;
        this.borderColorEnd = colBorderEnd;
        this.bottomTextureHeight = bottomTextureHeight;
        this.bottomTextureWidth = bottomTextureWidth;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getBorderColorEnd() {
        return borderColorEnd;
    }

    public int getBorderColorStart() {
        return borderColorStart;
    }

    public int getBottomTextureHeight() {
        return bottomTextureHeight;
    }

    public int getBottomTextureWidth() {
        return bottomTextureWidth;
    }

    public int getCornerDimensions() {
        return cornerDimensions;
    }

    public int getTopTextureHeight() {
        return topTextureHeight;
    }

    public int getTopTextureWidth() {
        return topTextureWidth;
    }

    public void bindTop(){
        ClientHelpers.bindText(TOP_LOCATION);
    }

    public void bindBottom(){
        ClientHelpers.bindText(BOTTOM_LOCATION);
    }
    public void bindCorners(){
        ClientHelpers.bindText(CORNER_LOCATION);
    }


    public CustomTooltip setyOffsetBottom(int yOffsetBottom) {
        this.yOffsetBottom = yOffsetBottom;
        return this;
    }

    public CustomTooltip setyOffsetTop(int yOffsetTop) {
        this.yOffsetTop = yOffsetTop;
        return this;
    }

    public int getyOffsetBottom() {
        return yOffsetBottom;
    }

    public int getyOffsetTop() {
        return yOffsetTop;
    }
}
