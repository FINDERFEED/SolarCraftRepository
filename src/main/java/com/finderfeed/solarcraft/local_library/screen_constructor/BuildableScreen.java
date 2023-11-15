package com.finderfeed.solarcraft.local_library.screen_constructor;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.HashMap;

public abstract class BuildableScreen extends Screen {

    private ScreenDataBuilder<? extends BuildableScreen> data;

    private HashMap<String,Object> additionalData;

    public BuildableScreen(ScreenDataBuilder<? extends BuildableScreen> data) {
        super(Component.literal(""));
        this.additionalData = new HashMap<>();
        this.data = data;
    }



    @Override
    protected void init() {
        super.init();
        this.additionalData = new HashMap<>(data.additionalData);
        for (AbstractWidget widget : data.widgets){
            this.addWidget(widget);
        }
        data.hackyRunOnInit(this);
    }


    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pticks) {
        for (ScreenDataBuilder.RenderableComponentInstance instance : data.renderables){
            instance.component().hackyRender(this,graphics,mx,my,pticks);
        }
    }


    @Override
    public void tick() {
        super.tick();
        if (this.data.onTick != null){
            this.data.hackyRunOnTick(this);
        }
    }

    @Override
    public boolean keyPressed(int key, int scancode, int modifiers) {

        if (data.keyPress != null){
            data.keyPress.hackyPress(this,key,scancode,modifiers);
        }

        return super.keyPressed(key, scancode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (this.data.onMouseClick != null){
            this.data.onMouseClick.hackyClick(this,mx,my,button);
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseDragged(double xpos, double ypos, int button, double leftright, double updown) {
        if (this.data.onDrag != null){
            this.data.onDrag.hackyDrag(this,xpos,ypos,button,leftright,updown);
        }
        return super.mouseDragged(xpos, ypos, button, leftright, updown);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double delta) {
        if (this.data.mouseScrolled != null){
            this.data.mouseScrolled.hackyScroll(this,mx,my,delta);
        }
        return super.mouseScrolled(mx, my, delta);
    }


    public <U> U getAdditionalData(String name, Class<U> clazz){
        return (U)this.additionalData.get(name);
    }
}
