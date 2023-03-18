package com.finderfeed.solarcraft.local_library.client.screens;

import com.finderfeed.solarcraft.client.screens.SolarCraftScreen;
import com.mojang.blaze3d.vertex.PoseStack;

public abstract class FDScreenComponent {

    private boolean isFocused = false;
    protected int x;
    protected int y;
    protected DefaultScreen screen;

    public FDScreenComponent(DefaultScreen screen, int x, int y){
        this.x = x;
        this.y = y;
        this.screen = screen;
    }

    public abstract void init();

    public abstract void render(PoseStack matrices,int mousex,int mousey,float pticks);

    public abstract void tick();

    //all mouse actions are relative to this component's x and y
    public abstract void mouseClicked(double x, double y, int action);

    public abstract void mouseReleased(double x, double y, int action);

    public abstract void mouseDragged(double xPos, double yPos, int button, double dragLeftRight, double dragUpDown);

    public abstract void keyPressed(int key, int scanCode, int modifiers);

    public abstract void mouseScrolled(double mousePosX, double mousePosY, double delta);

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public boolean isMouseInBounds(double x,double y,double sizeX,double sizeY){
        return x >= 0 && y >= 0 && x <= sizeX && y <= sizeY;
    }
}
