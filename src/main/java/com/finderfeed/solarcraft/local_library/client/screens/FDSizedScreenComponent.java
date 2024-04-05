package com.finderfeed.solarcraft.local_library.client.screens;

import net.minecraft.client.gui.screens.Screen;

public abstract class FDSizedScreenComponent extends FDScreenComponent{

    private int width;
    private int height;

    public FDSizedScreenComponent(Screen screen, int x, int y,int width,int height) {
        super(screen, x, y);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
