package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.misc_things.IScrollable;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
public abstract class ScrollableLexiconScreen extends LexiconScreen implements IScrollable {

    protected int scrollX;
    protected int scrollY;


    @Override
    protected void init() {
        super.init();
        scrollX = 0;
        scrollY = 0;
    }

    @Override
    public void performScroll(int keyCode) {
        int scrollValue = Screen.hasShiftDown() ? this.getScrollValue() * 2 : this.getScrollValue();
        if (keyCode == GLFW_KEY_W || keyCode == GLFW_KEY_UP){
            int delta = FDMathHelper.getUnderflow(-this.getYDownScroll(),scrollY,this.getYUpScroll(),scrollValue);
            scrollY = FDMathHelper.clamp(-this.getYDownScroll(),scrollY+scrollValue,0);
            this.onScrollUp(delta);
        }else if (keyCode == GLFW_KEY_S || keyCode == GLFW_KEY_DOWN){
            int delta = FDMathHelper.getUnderflow(-this.getYDownScroll(),scrollY,this.getYUpScroll(),-scrollValue);
            scrollY = FDMathHelper.clamp(-this.getYDownScroll(),scrollY-scrollValue,0);
            this.onScrollDown(delta);
        }
        if (keyCode == GLFW_KEY_A || keyCode == GLFW_KEY_LEFT){
            int delta = FDMathHelper.getUnderflow(-this.getXRightScroll(),scrollX,this.getXLeftScroll(),scrollValue);
            scrollX = FDMathHelper.clamp(-this.getXRightScroll(),scrollX+scrollValue,0);
            this.onScrollLeft(delta);
        }else if (keyCode == GLFW_KEY_D || keyCode == GLFW_KEY_RIGHT){
            int delta = FDMathHelper.getUnderflow(-this.getXRightScroll(),scrollX,this.getXLeftScroll(),-scrollValue);
            scrollX = FDMathHelper.clamp(-this.getXRightScroll(),scrollX-scrollValue,0);
            this.onScrollRight(delta);
        }
    }


    public abstract int getScrollValue();

    public abstract int getXRightScroll();
    public abstract int getYDownScroll();

    public int getXLeftScroll(){
        return 0;
    }

    public int getYUpScroll(){
        return 0;
    }

    public void onScrollUp(int delta){
        for (AbstractWidget widget : this.getMovableWidgets()){
            widget.y += delta;
        }
    }
    public void onScrollDown(int delta){
        for (AbstractWidget widget : this.getMovableWidgets()){
            widget.y += delta;
        }
    }
    public void onScrollLeft(int delta){
        for (AbstractWidget widget : this.getMovableWidgets()){
            widget.x += delta;
        }
    }
    public void onScrollRight(int delta){
        for (AbstractWidget widget : this.getMovableWidgets()){
            widget.x += delta;
        }
    }

    public abstract List<AbstractWidget> getMovableWidgets();

    @Override
    public int getCurrentScrollX() {
        return scrollX;
    }

    @Override
    public int getCurrentScrollY() {
        return scrollY;
    }
}
