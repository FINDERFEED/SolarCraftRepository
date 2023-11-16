package com.finderfeed.solarcraft.local_library.screen_constructor;

import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BuildableScreen extends DefaultScreen {
    private ScreenDataBuilder<? extends BuildableScreen> data;
    private HashMap<String,Object> additionalData;

    protected List<RenderableComponentInstance> renderableInstances = new ArrayList<>();
    public BuildableScreen(ScreenDataBuilder<? extends BuildableScreen> data) {
        super();
        this.additionalData = new HashMap<>();
        this.data = data;
    }

    @Override
    protected void init() {
        super.init();
        this.renderableInstances.clear();
        this.additionalData = new HashMap<>(data.additionalData);
        data.applyWidgetsAndRenderablesToScreen(this);
        data.hackyRunOnInit(this);
    }

    public void hackyWidgetAdd(AbstractWidget widget){
        this.addWidget(widget);
    }

    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pticks) {
        PoseStack matrix = graphics.pose();
        matrix.pushPose();
        for (RenderableComponentInstance instance : this.renderableInstances){
            instance.component().hackyRender(this,graphics,mx,my,pticks);
            matrix.translate(0,0,1);
        }
        matrix.popPose();
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


    public <U> U getAdditionalData(String name, Class<U> clazz) {
        return (U)this.additionalData.get(name);
    }

    public void addAdditionalData(String name,Object data){
        this.additionalData.put(name,data);
    }

    @Override
    public int getScreenWidth() {
        return data.screenWidth;
    }

    @Override
    public int getScreenHeight() {
        return data.screenHeight;
    }
}
