package com.finderfeed.solarcraft.local_library.client.screens;

import com.finderfeed.solarcraft.local_library.client.particles.ScreenParticlesRenderHandler;
import com.finderfeed.solarcraft.local_library.client.screens.FDScreenComponent;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    public int relX;
    public int relY;
    private Map<String, FDScreenComponent> components = new HashMap<>();

    public DefaultContainerScreen(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void init() {
        this.components.clear();
        super.init();
        Window window = Minecraft.getInstance().getWindow();
        int scaledWidth = window.getGuiScaledWidth();
        int scaledHeight = window.getGuiScaledHeight();
        this.relX = scaledWidth / 2 - getScreenWidth() / 2;
        this.relY = scaledHeight / 2 - getScreenHeight() / 2;
    }

    @Override
    public void containerTick() {
        super.containerTick();
        for (FDScreenComponent component : components.values()) {
            component.tick();
        }
    }


    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        for (FDScreenComponent component : components.values()) {
            component.keyPressed(key, scanCode, modifiers);
        }
        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public boolean mouseReleased(double x, double y, int action) {
        for (FDScreenComponent component : components.values()) {
            component.mouseReleased(x - component.x, y - component.y, action);
        }
        return super.mouseReleased(x, y, action);
    }

    @Override
    public boolean mouseDragged(double xPos, double yPos, int button, double dragLeftRight, double dragUpDown) {
        for (FDScreenComponent component : components.values()) {
            component.mouseDragged(xPos - component.x, yPos - component.y, button, dragLeftRight, dragUpDown);
        }
        return super.mouseDragged(xPos, yPos, button, dragLeftRight, dragUpDown);
    }

    @Override
    public boolean mouseClicked(double x, double y, int action) {
        for (FDScreenComponent component : components.values()) {
            component.mouseClicked(x - component.x, y - component.y, action);
        }
        return super.mouseClicked(x, y, action);
    }

    @Override
    public boolean mouseScrolled(double mousePosX, double mousePosY, double delta,double what) {
        for (FDScreenComponent component : components.values()) {
            component.mouseScrolled(mousePosX - component.x, mousePosY - component.y, delta);
        }
        return super.mouseScrolled(mousePosX, mousePosY, delta,what);
    }

    public void renderComponents(GuiGraphics graphics, int mx, int my, float pticks, String... ids) {
        for (String id : ids) {
            FDScreenComponent component = components.get(id);
            component.render(graphics, mx - component.x, my - component.y, pticks);
        }
    }

    public void renderAllComponents(GuiGraphics graphics, int mx, int my, float pticks) {
        for (FDScreenComponent component : components.values()) {
            component.render(graphics, mx - component.x, my - component.y, pticks);
        }
    }

    public void addFDComponent(String id, FDScreenComponent scScreenComponent) {
        this.components.put(id, scScreenComponent);
    }


    @Override
    public void onClose() {
        super.onClose();
        ScreenParticlesRenderHandler.clearAllParticles();
    }

    public abstract int getScreenWidth();

    public abstract int getScreenHeight();
}
