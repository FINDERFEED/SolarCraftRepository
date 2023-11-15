package com.finderfeed.solarcraft.local_library.screen_constructor;

import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ScreenDataBuilder<T extends BuildableScreen> {

    protected List<AbstractWidget> widgets = new ArrayList<>();
    protected List<RenderableComponentInstance> renderables = new ArrayList<>();
    protected HashMap<String,Object> additionalData = new HashMap<>();

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

    public ScreenDataBuilder<T> addRenderable(int renderIndex,RenderableComponent<T> renderableComponent){
        this.renderables.add(new RenderableComponentInstance(renderIndex,renderableComponent));
        return this;
    }

    public ScreenDataBuilder<T> addWidget(int renderIndex,String name,AbstractWidget widget){
        if (this.additionalData.containsKey(name)){
            throw new RuntimeException("Duplicate data: " + name);
        }
        this.widgets.add(widget);
        this.additionalData.put(name,widget);
        this.renderables.add(new RenderableComponentInstance(renderIndex,(screen,graphics,mx,my,pticks)->{
            widget.render(graphics,mx,my,pticks);
        }));
        return this;
    }


    public ScreenDataBuilder<T> build(){
        this.renderables.sort(Comparator.comparingInt(i->i.renderIndex));
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

    public record RenderableComponentInstance(int renderIndex,RenderableComponent<?> component){

    }
}
