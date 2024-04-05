package com.finderfeed.solarcraft.client.screens.components;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.client.screens.FDScreenComponent;
import com.finderfeed.solarcraft.local_library.client.screens.IRenderable;
import com.finderfeed.solarcraft.local_library.client.tooltips.animatable_omponents.FDTextComponent;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class TooltipBoxComponent extends FDScreenComponent {

    private int sizeX;
    private int sizeY;
    private IRenderable tooltip;

    public TooltipBoxComponent(Screen screen, int x, int y, int sizeX, int sizeY, IRenderable renderable) {
        super(screen, x, y);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.tooltip = renderable;
    }


    @Override
    public void init() {

    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float pticks) {
        if (RenderingTools.isMouseInBorders(mousex,mousey,0,0,sizeX,sizeY)){
            tooltip.render(graphics,x + mousex,y + mousey);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void mouseClicked(double x, double y, int action) {

    }

    @Override
    public void mouseReleased(double x, double y, int action) {

    }

    @Override
    public void mouseDragged(double xPos, double yPos, int button, double dragLeftRight, double dragUpDown) {

    }

    @Override
    public void keyPressed(int key, int scanCode, int modifiers) {

    }

    @Override
    public void mouseScrolled(double mousePosX, double mousePosY, double delta) {

    }
}
