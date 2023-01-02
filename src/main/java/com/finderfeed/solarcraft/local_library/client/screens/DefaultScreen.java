package com.finderfeed.solarcraft.local_library.client.screens;

import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.PostRenderTooltips;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public abstract class DefaultScreen extends Screen {

    public int relX;
    public int relY;


    public DefaultScreen() {
        super(Component.literal(""));
    }

    @Override
    protected void init() {
        super.init();
        Window window = Minecraft.getInstance().getWindow();
        int scaledWidth = window.getGuiScaledWidth();
        int scaledHeight = window.getGuiScaledHeight();
        this.relX = scaledWidth / 2 - getScreenWidth() / 2;
        this.relY = scaledHeight / 2 - getScreenHeight() / 2;
    }

    public abstract int getScreenWidth();
    public abstract int getScreenHeight();

}
