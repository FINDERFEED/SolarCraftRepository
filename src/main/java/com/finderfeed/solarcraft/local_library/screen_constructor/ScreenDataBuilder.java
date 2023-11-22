package com.finderfeed.solarcraft.local_library.screen_constructor;

import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ScreenDataBuilder<T extends BuildableScreen> {
    protected List<Function<T,WidgetInstance>> widgets = new ArrayList<>();
    protected List<RenderableComponentInstance<T>> renderables = new ArrayList<>();
    protected HashMap<String,Object> additionalData = new HashMap<>();
    protected HashMap<Integer,RenderableComponentPair<T>> renderTypes = new HashMap<>();
    protected int screenWidth;
    protected int screenHeight;
    protected Consumer<T> onTick;
    protected Consumer<T> onInit;
    protected OnKeyPress<T> keyPress;
    protected OnMouseClick<T> onMouseClick;

    protected OnMouseScrolled<T> mouseScrolled;
    protected OnMouseDrag<T> onDrag;

    public ScreenDataBuilder(){

    }

    public ScreenDataBuilder<T> onInit(Consumer<T> onInit){
        this.onInit = onInit;
        return this;
    }

    public ScreenDataBuilder<T> keyPressed(OnKeyPress<T> keyPress){
        this.keyPress = keyPress;
        return this;
    }
    public ScreenDataBuilder<T> onMouseScroll(OnMouseScrolled<T> onMouseScrolled){
        this.mouseScrolled = onMouseScrolled;
        return this;
    }
    public ScreenDataBuilder<T> onMouseClick(OnMouseClick<T> mouseClick){
        this.onMouseClick = mouseClick;
        return this;
    }

    public ScreenDataBuilder<T> onMouseDrag(OnMouseDrag<T> drag){
        this.onDrag = drag;
        return this;
    }
    public ScreenDataBuilder<T> onTick(Consumer<T> onTick){
        this.onTick = onTick;
        return this;
    }

    public ScreenDataBuilder<T> addAdditionalData(String dataName, Object object){
        if (this.additionalData.containsKey(dataName)){
            throw new RuntimeException("Duplicate data: " + dataName);
        }
        this.additionalData.put(dataName,object);
        return this;
    }

    public ScreenDataBuilder<T> addRenderable(RenderableComponentInstance<T> renderableComponent){
        this.renderables.add(renderableComponent);
        return this;
    }

    public ScreenDataBuilder<T> addWidget(Function<T,WidgetInstance> widget){
        this.widgets.add(widget);
        return this;
    }
    public ScreenDataBuilder<T> setDimensions(int width,int height){
        this.screenWidth = width;
        this.screenHeight = height;
        return this;
    }

    public ScreenDataBuilder<T> setRenderTypeForRenderGroup(int renderGroup,RenderableComponent<T> before,RenderableComponent<T> after) {
        this.renderTypes.put(renderGroup,new RenderableComponentPair<>(before,after));
        return this;
    }




    protected void hackyRunOnTick(BuildableScreen screen){
        if (onTick == null) return;;
        this.onTick.accept((T)screen);
    }

    protected void hackyRunOnInit(BuildableScreen screen){
        if (onInit == null) return;
        this.onInit.accept((T)screen);
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    protected void applyWidgetsAndRenderablesToScreen(BuildableScreen screen){
        for (Function<T,WidgetInstance> instance : widgets){
            WidgetInstance widgetInstance = instance.apply((T)screen);
            AbstractWidget widget = widgetInstance.widget();
            screen.hackyWidgetAdd(widget);
            screen.renderableInstances.add(new RenderableComponentInstance(widgetInstance.renderIndex(),(scr,graphics,mx,my,pticks)->{
                widget.render(graphics,mx,my,pticks);
            }));
            screen.addAdditionalData(widgetInstance.name(), widget);
        }
        screen.renderableInstances.addAll(this.renderables);
        screen.renderableInstances.sort(Comparator.comparingInt(i->i.renderGroup()));
    }



}
