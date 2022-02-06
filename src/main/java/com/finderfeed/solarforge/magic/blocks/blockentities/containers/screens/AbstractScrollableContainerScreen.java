package com.finderfeed.solarforge.magic.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.misc_things.IScrollable;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScrollableContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> implements IScrollable {

    public int relX;
    public int relY;
    public int prevscrollX = 0;
    public int prevscrollY = 0;
    public int scrollX = 0;
    public int scrollY = 0;
    private List<AbstractWidget> staticWidgets = new ArrayList<>();

    public AbstractScrollableContainerScreen(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }


    @Override
    protected void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 30;
        this.relY = (height - 218*scale)/2/scale;
    }

    @Override
    public void performScroll(int keyCode) {
        if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_LEFT) || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_A))
                && !(scrollX +getScrollValue() > getMaxXLeftScrollValue())){
            scrollX+=getScrollValue();
        } else if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_UP)
                || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_W)) && !(scrollY +getScrollValue() > getMaxYUpScrollValue())){
            scrollY+=getScrollValue();
        }else if((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_DOWN)
                || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_S)) && !(scrollY -getScrollValue() < -getMaxYDownScrollValue())){
            scrollY-=getScrollValue();
        }else if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_RIGHT)
                || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_D)) && !(scrollX -getScrollValue() < -getMaxXRightScrollValue())){
            scrollX-=getScrollValue();
        }
        if (this.prevscrollX != scrollX){
            List<AbstractWidget> list = ClientHelpers.getScreenButtons(this);
            list.removeAll(staticWidgets);
            for (AbstractWidget a : list) {
                if (prevscrollX < scrollX) {
                    a.x += getScrollValue();
                } else {
                    a.x -= getScrollValue();
                }

            }
            this.prevscrollX = scrollX;
        }
        if (this.prevscrollY != scrollY){
            List<AbstractWidget> list = ClientHelpers.getScreenButtons(this);
            list.removeAll(staticWidgets);
            for (AbstractWidget a : list) {
                if (prevscrollY < scrollY) {

                    a.y += getScrollValue();
                } else {

                    a.y -= getScrollValue();
                }


            }
            this.prevscrollY = scrollY;
        }
    }

    protected void setAsStaticWidget(AbstractWidget widget){
        if (!staticWidgets.contains(widget)){
            staticWidgets.add(widget);
        }
    }

    public List<AbstractWidget> getStaticWidgets() {
        return staticWidgets;
    }

    public int getRelX() {
        return relX;
    }

    public int getRelY() {
        return relY;
    }

    @Override
    public int getCurrentScrollX() {
        return scrollX;
    }

    @Override
    public int getCurrentScrollY() {
        return scrollY;
    }

    protected abstract int getScrollValue();
    protected abstract int getMaxYDownScrollValue();
    protected abstract int getMaxXRightScrollValue();
    protected abstract int getMaxYUpScrollValue();
    protected abstract int getMaxXLeftScrollValue();



}
