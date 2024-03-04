package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle.client;

import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class SunRayButton extends FDButton {
    private int id;
    public SunRayButton(int posX, int posY, int width, int height,int id, OnPress press) {
        super(posX, posY, width, height,Component.empty(), press);
        this.id = id;
    }


    @Override
    public void renderWidget(GuiGraphics graphics, int mx, int my, float pticks) {
        super.renderWidget(graphics, mx, my, pticks);
    }

    public int getId() {
        return id;
    }
}
