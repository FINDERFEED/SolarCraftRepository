package com.finderfeed.solarforge.client.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.local_library.other.CanTick;
import com.finderfeed.solarforge.misc_things.IScrollable;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public abstract class ScrollableScreen extends Screen implements IScrollable {

    public int relX;
    public int relY;
    public int prevscrollX = 0;
    public int prevscrollY = 0;
    public int scrollX = 0;
    public int scrollY = 0;
    private List<AbstractWidget> staticWidgets = new ArrayList<>();
    private List<Runnable> postRenderEntries = new ArrayList<>();
    private List<CanTick> tickables = new ArrayList<>();

    public ScrollableScreen() {
        super(new TextComponent(""));
    }


    @Override
    protected void init() {
        super.init();
        this.tickables.clear();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 30;
        this.relY = (height - 218*scale)/2/scale;
    }

    @Override
    public void tick() {
        super.tick();
        tickables.forEach(CanTick::tick);
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
    protected void runPostEntries(){
        if (!postRenderEntries.isEmpty()){
            postRenderEntries.forEach(Runnable::run);
            postRenderEntries.clear();
        }
    }

    protected void addPostRenderEntry(Runnable post){
        this.postRenderEntries.add(post);
    }
}
